package NonCommandListeners;

import MiscUtils.ItemCounterForDepositOrReturnMessage;
import TownObjects.BundleItemStack;
import TownObjects.Current.CurItems;
import TownObjects.WrapperTown;
import com.ProPixel.TownyProj.TownyProj;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import static com.ProPixel.TownyProj.TownyProj.listWrapperTowns;

public class VaultDeposit implements Listener {

    @EventHandler
    public void onDepositClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals("§7§lVAULT DEPOSIT")) {
            return;
        }
        WrapperTown wrapperTown = listWrapperTowns.getWrapperTownByHumanEntity(event.getPlayer());
        if (wrapperTown == null) {
            return;
        }
        ItemCounterForDepositOrReturnMessage deposited = new ItemCounterForDepositOrReturnMessage();
        ItemCounterForDepositOrReturnMessage returned = new ItemCounterForDepositOrReturnMessage();
        for (ItemStack itemStack : event.getInventory().getContents()) {
            if (itemStack != null) {
                BundleItemStack bundleItemStack = wrapperTown.getBundleItemStack(itemStack);
                if (bundleItemStack != null) {
                    //add amount to current
                    bundleItemStack.getCurItems().addAmount(bundleItemStack, itemStack.getAmount());
                    deposited.addItemStackToList(itemStack);
                } else {
                    //return itemstack to player inventory
                    returned.addItemStackToList(itemStack);
                    event.getPlayer().getInventory().addItem(itemStack);
                }
            }
        }
        event.getInventory().clear();
        for (ItemStack itemStack : deposited.getItemStackList()) {
            event.getPlayer().sendMessage("§l§a[+] " + itemStack.getAmount() + " " + itemStack.getType() + ", successfully deposited");
        }
        for (ItemStack itemStack : returned.getItemStackList()) {
            event.getPlayer().sendMessage("§l§c[-] " + itemStack.getAmount() + " " + itemStack.getType() + ", rejected and returned to you");
        }
    }
}
