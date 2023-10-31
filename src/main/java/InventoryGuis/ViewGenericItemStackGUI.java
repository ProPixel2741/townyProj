package InventoryGuis;

import TownObjects.WrapperTown;
import MiscUtils.EmptyEnchant;
import mc.obliviate.inventory.Gui;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewGenericItemStackGUI extends Gui {
    List<ItemStack> itemStackListToView;
    public ViewGenericItemStackGUI(@NotNull Player player, @NotNull String id, String title, int rows, List<ItemStack> itemStackListToView) {
        super(player, id, title, rows);
        this.itemStackListToView = itemStackListToView;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        populateGUI();
    }

    private void populateGUI() {
        for (int i = 0; i < itemStackListToView.size(); i++) {
            ItemStack itemStack = itemStackListToView.get(i);
            if (itemStack.getAmount() > 64) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GOLD + "" + itemStackListToView.get(i).getAmount() + " deposited");
                itemMeta.setDisplayName(ChatColor.BOLD + itemMeta.getDisplayName());
                itemStack.setItemMeta(itemMeta);
                EmptyEnchant emptyEnchant = new EmptyEnchant(NamespacedKey.fromString("key"));
                itemStack.addUnsafeEnchantment(emptyEnchant, 1); //anything
            }
            addItem(i, itemStackListToView.get(i));
        }
    }

}
