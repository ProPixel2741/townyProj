package gui;

import mc.obliviate.inventory.Gui;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import utils.EmptyEnchant;

import java.util.ArrayList;
import java.util.List;

public class VaultRemaining extends Gui {
    List<ItemStack> remainingItems = new ArrayList<>();

    public VaultRemaining(Player player, List<ItemStack> requirementItems, List<ItemStack> currentItems) {
        super(player,
                "VaultRemaining",
                ChatColor.GRAY + "VAULT REMAINING",
                6);
        calculateRemainingItems(requirementItems, currentItems);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        populateGUI(this.remainingItems);
    }

    public void populateGUI(List<ItemStack> remainingItems) {
        for (int i = 0; i < remainingItems.size(); i++) {
            ItemStack remainingItemStack = remainingItems.get(i);
            if (remainingItemStack.getAmount() > 64) {
                ItemMeta itemMeta = remainingItemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GOLD + "" + remainingItemStack.getAmount() + " remaining");
                itemMeta.setDisplayName(ChatColor.BOLD + itemMeta.getDisplayName());
                remainingItemStack.setItemMeta(itemMeta);
                EmptyEnchant emptyEnchant = new EmptyEnchant(NamespacedKey.fromString("key"));
                remainingItemStack.addUnsafeEnchantment(emptyEnchant, 1); //anything
            }
            addItem(i, remainingItemStack);
        }
    }

    private void calculateRemainingItems(List<ItemStack> requirementItems, List<ItemStack> currentItems) {
        for (ItemStack requirements : requirementItems) {
            for (ItemStack current : currentItems) {
                if (requirements.getType() == current.getType()) {
                    int currentAmount = current.getAmount();
                    remainingItems(currentAmount, requirements);
                    return;
                }
            }
            int currentAmount = 0;
            remainingItems(currentAmount, requirements);
            return;
        }
    }

    private void remainingItems(int currentAmount, ItemStack requirements) {
        int remaining = requirements.getAmount() - currentAmount;
        if (remaining <= 0) {
            return;
        }
        ItemStack itemStack = new ItemStack(requirements.getType(), remaining);
        remainingItems.add(itemStack);
    }
}
