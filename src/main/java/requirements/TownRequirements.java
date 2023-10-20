package requirements;

import com.example.myplugin.MyPlugin;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.example.myplugin.MyPlugin.configValues;
import static com.example.myplugin.MyPlugin.listOfTownsRequirements;

public class TownRequirements {
    private List<ItemStack> requirementItemSets = new ArrayList<ItemStack>();

    private List<ItemStack> currentItemSets;

    private int currentMoney;
    private int requirementMoney;
    private int level;
    private Town town;

    //for TotalRequirements
    private List<ItemStack> itemRequirements(int level) {
        List<ItemStack> requirements = new ArrayList<ItemStack>();
        //requirements.add(new ItemStack(Material.DIAMOND, 320));
//        requirements.add(new ItemStack(Material.APPLE, 1));
//        requirements.add(new ItemStack(Material.OAK_LOG, 64));
        //requirements.add(new ItemStack(Material.COBBLESTONE, 1000));
        int apples = level * 10;
        requirements.add(new ItemStack(Material.APPLE, apples));
        int golden_apples = level * 1;
        requirements.add(new ItemStack(Material.GOLDEN_APPLE, golden_apples));

        return requirements;
    }

    private int moneyRequirements() {
        return 1000;
    }

    public TownRequirements(Town town, int level) {
        this.town = town;
        this.level = level;
        this.currentItemSets = new ArrayList<ItemStack>();
        this.currentMoney = 0;
        this.requirementItemSets = itemRequirements(level);
        this.requirementMoney = moneyRequirements();
    }

    public Town getTown() {
        return town;
    }

    public int getLevel() {
        return level;
    }

    public List<ItemStack> getRequirementItemSets() {
        return requirementItemSets;
    }

    public List<ItemStack> getCurrentItemSets() {
        return currentItemSets;
    }

    public void setCurrentItemSets(List<ItemStack> currentItemSets) {
        this.currentItemSets = currentItemSets;
    }

    public void addCurrentItemSets(ItemStack addItemSets, HumanEntity player) {
        for (ItemStack required : requirementItemSets) {
            if (addItemSets.getType() == required.getType()) {
                int totalAmount;
                for (ItemStack current : currentItemSets) {
                    if (addItemSets.getType() == current.getType()) {
                        totalAmount = current.getAmount() + addItemSets.getAmount();
                        addWithExistingAmount(totalAmount, addItemSets, current, required, player);
                        return;
                    }
                }
                //does not currently exist yet
                totalAmount = addItemSets.getAmount();
                addWithoutExistingAmount(totalAmount, addItemSets, required, player);
                return;
            }
        }
    }

    private void addWithExistingAmount(int totalAmount, ItemStack addItemSets, ItemStack current, ItemStack required, HumanEntity player) {
        if (configValues.getConfigOverflowRequirements()) {
            current.setAmount(totalAmount);
            return;
        }
        if (totalAmount > required.getAmount()) {
            current.setAmount(required.getAmount());
            int remainder = totalAmount - required.getAmount();
            ItemStack remainderItemStack = new ItemStack(addItemSets.getType(), remainder);
            player.getInventory().addItem(remainderItemStack);
            return;
        }
        current.setAmount(totalAmount);
    }

    private void addWithoutExistingAmount(int totalAmount, ItemStack addItemSets, ItemStack required, HumanEntity player) {
        if (configValues.getConfigOverflowRequirements()) {
            currentItemSets.add(addItemSets);
            return;
        }
        if (totalAmount > required.getAmount()) {
            ItemStack totalWithoutRemainder = new ItemStack(addItemSets.getType(), required.getAmount());
            currentItemSets.add(totalWithoutRemainder);
            int remainder = totalAmount - required.getAmount();
            ItemStack remainderItemStack = new ItemStack(addItemSets.getType(), remainder);
            player.getInventory().addItem(remainderItemStack);
            return;
        }
        currentItemSets.add(addItemSets);
    }

    public boolean goodToRankupNoRequirementsRemaining() {
        if (currentItemSets.isEmpty()) {
            return false;
        }
        for (ItemStack required : requirementItemSets) {
            for (ItemStack current : currentItemSets) {
                if (current.getType() == required.getType()) {
                    if (current.getAmount() < required.getAmount()) {
                        return false;
                    }
                }
            }
        }
        if (!(town.getAccount().getHoldingBalance() >= requirementMoney)) {
            return false;
        }
        return true;
    }

    public void rankupByTakingRequiredItemsAndMoneyAway() {
        for (ItemStack required : requirementItemSets) {
            for (ItemStack current : currentItemSets) {
                if (current.getType() == required.getType()) {
                    current.setAmount(current.getAmount() - required.getAmount());
                }
            }
        }
        double newBalance = town.getAccount().getHoldingBalance() - requirementMoney;
        town.getAccount().setBalance(newBalance, "For ranking up");


        listOfTownsRequirements.removeTownFromList(this);
        listOfTownsRequirements.addTownToList(new TownRequirements(town, town.getManualTownLevel()));
    }
}
