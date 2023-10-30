package listener;

import gui.VaultView;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VaultPreventClickEvent implements Listener {

    @EventHandler
    public void preventClickEvent(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getClickedInventory() != null && inventoryClickEvent.getClickedInventory().getHolder() instanceof VaultView) {
            inventoryClickEvent.setCancelled(true);
        }
    }
}
