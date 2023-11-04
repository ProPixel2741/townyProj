package TownObjects;

import TownObjects.Requirements.ReqMoney;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WrapperTown {

    private Town town;
    private Integer townLevel;
    private List<BundleItemStack> bundleItemStackList;

    private OverflowItemStacks overflowItemStacks;

    private ReqMoney reqMoney;

    public WrapperTown(Town town) {
        this.town = town;
        this.townLevel = town.getManualTownLevel();
        bundleItemStackList = new ArrayList<>();
        overflowItemStacks = new OverflowItemStacks();
        initializeItemStackBundles();
        initializeMoneyBundle();
    }

    private void initializeMoneyBundle() {
        if (townLevel >= 1) {
            reqMoney = new ReqMoney(townLevel * 100);
        }
    }

    private void initializeItemStackBundles() {
        if (townLevel >= 1) {
            addItemStackBundle(new ItemStack(Material.IRON_INGOT, townLevel*10));
        }
        if (townLevel >= 1) {
            addItemStackBundle(new ItemStack(Material.DIAMOND, Math.floorDiv(townLevel, 5)));
        }
        if (townLevel >= 10) {
            addItemStackBundle(new ItemStack(Material.NETHERITE_INGOT, Math.floorDiv(townLevel, 10)));
        }
        if (townLevel == 8) {
            addItemStackBundle(new ItemStack(Material.NETHER_STAR, 1));
        }
    }

    private void addItemStackBundle(ItemStack itemStack) {
        bundleItemStackList.add(new BundleItemStack(itemStack, itemStack.getAmount()));
    }

    public Town getTown() {
        return town;
    }

    public Integer getTownLevel() {
        return townLevel;
    }

    public List<BundleItemStack> getBundleItemStackList() {
        return bundleItemStackList;
    }

    public BundleItemStack getBundleItemStack(ItemStack itemStack) {
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            if (bundleItemStack.getItemStack().getType() == itemStack.getType()) {
                return bundleItemStack;
            }
        }
        return null;
    }

    public List<ItemStack> getDisplayBundleItemStackList() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            ItemStack displayItemStack = new ItemStack(bundleItemStack.getItemStack().getType(), 1);

            ItemMeta displayMeta = displayItemStack.getItemMeta();
            List<String> displayList = new ArrayList<>();
            displayList.add("§c Required Amount: " + bundleItemStack.getReqItems().getAmount());
            displayList.add("§6 Remaining Amount: " + bundleItemStack.getRemItems().getAmount());
            displayList.add("§a Deposited Amount: " + bundleItemStack.getCurItems().getAmount());
            displayMeta.setLore(displayList);
            displayItemStack.setItemMeta(displayMeta);

            itemStackList.add(displayItemStack);
        }
        return itemStackList;
    }

    public List<ItemStack> getRequirementsItemStackList() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            ItemStack itemStack = bundleItemStack.getItemStack();
            itemStack.setAmount(bundleItemStack.getReqItems().getAmount());
            itemStackList.add(itemStack);
        }
        return itemStackList;
    }

    public List<ItemStack> getRemainingItemStackList() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            ItemStack itemStack = bundleItemStack.getItemStack();
            itemStack.setAmount(bundleItemStack.getRemItems().getAmount());
            itemStackList.add(itemStack);
        }
        return itemStackList;
    }

    public List<ItemStack> getCurrentItemStackList() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            ItemStack itemStack = bundleItemStack.getItemStack();
            itemStack.setAmount(bundleItemStack.getCurItems().getAmount());
            itemStackList.add(itemStack);
        }
        return itemStackList;
    }

    public Integer getMoneyRequired() {
        return reqMoney.getMoney();
    }

    public OverflowItemStacks getOverflowItemStacks() {
        try {
            return overflowItemStacks;
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("No existing overflow.");
            overflowItemStacks = new OverflowItemStacks();
            return overflowItemStacks;
        }
    }
}
