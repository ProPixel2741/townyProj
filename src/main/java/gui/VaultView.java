package gui;

import mc.obliviate.inventory.Gui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import utils.EmptyEnchant;

import java.util.List;

public class VaultView extends Gui {
    List<ItemStack> currentItems;
    public VaultView(Player player, List<ItemStack> currentItems) {
        super(player,
                "VaultView",
                ChatColor.GRAY + "VAULT VIEW",
                6);
        this.currentItems = currentItems;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        populateGUI(this.currentItems);
    }

    public void populateGUI(List<ItemStack> currentItems) {
        for (int i = 0; i < currentItems.size(); i++) {
            ItemStack currentItemStack = currentItems.get(i);
            if (currentItemStack.getAmount() > 64) {
                ItemMeta itemMeta = currentItemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GOLD + "" + currentItems.get(i).getAmount() + " deposited");
                itemMeta.setDisplayName(ChatColor.BOLD + itemMeta.getDisplayName());
                currentItemStack.setItemMeta(itemMeta);
                EmptyEnchant emptyEnchant = new EmptyEnchant(NamespacedKey.fromString("key"));
                currentItemStack.addUnsafeEnchantment(emptyEnchant, 1); //anything
            }
            addItem(i, currentItems.get(i));
        }
    }
}
