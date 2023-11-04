package Cmds;

import InventoryGuis.ViewGenericItemStackGUI;
import TownObjects.WrapperTown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.ProPixel.TownyProj.TownyProj.listWrapperTowns;

public class VaultSubCmd implements CommandExecutor, Listener {

    private final Inventory depositInventory = Bukkit.createInventory(null, 54, ("§7§lVAULT DEPOSIT"));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be player to run command");
            return false;
        }
        Player player = (Player) sender;
        WrapperTown wrapperTown = listWrapperTowns.getWrapperTownByPlayer(player);
        if (wrapperTown == null) {
            sender.sendMessage("Must be in a town to run command");
            return false;
        }
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("deposit") || args[0].equalsIgnoreCase("dep")) {
                player.openInventory(depositInventory);
                return false;
            }
            if (args[0].equalsIgnoreCase("getoverflow") || args[0].equalsIgnoreCase("gofl")) {
                for (ItemStack overflowItemStack : wrapperTown.getOverflowItemStacks().getOverflowItemStackList()) {

                    if (canFullyAddItem(player, overflowItemStack)) {
                        wrapperTown.getOverflowItemStacks().subtractOverflowItemStackList(overflowItemStack);
                    }

                }
                return false;
            }
            if (args[0].equalsIgnoreCase("view")) {
                if (args.length == 1) {
                    player.sendMessage("view");
                    ViewGenericItemStackGUI genericBundleView = new ViewGenericItemStackGUI(player, "VAULTVIEW", "§7§lVAULT VIEW", 6, wrapperTown.getDisplayBundleItemStackList(), "");
                    genericBundleView.open();
                    return false;
                }
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("deposits") || args[1].equalsIgnoreCase("dep")) {
                        player.sendMessage("view deposits");
                        ViewGenericItemStackGUI depositView = new ViewGenericItemStackGUI(player, "VAULTDEPOSITVIEW", "§7§lVAULT DEPOSIT VIEW. §6§lUse /t vault deposit", 6, wrapperTown.getCurrentItemStackList(), "§7§lDEPOSITED");
                        depositView.open();
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("remaining") || args[1].equalsIgnoreCase("rem")) {
                        player.sendMessage("view remaining");
                        ViewGenericItemStackGUI remainingView = new ViewGenericItemStackGUI(player, "VAULTREMAININGVIEW", "§7§lVAULT REMAINING VIEW", 6, wrapperTown.getRemainingItemStackList(), "§7§lREMAINING");
                        remainingView.open();
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("requirements") || args[1].equalsIgnoreCase("req")) {
                        player.sendMessage("view requirements");
                        ViewGenericItemStackGUI requirementsView = new ViewGenericItemStackGUI(player, "VAULTREQUIREMENTSVIEW", "§7§lVAULT REQUIREMENTS VIEW", 6, wrapperTown.getRequirementsItemStackList(), "§7§lREQUIREMENTS");
                        requirementsView.open();
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("overflow") || args[1].equalsIgnoreCase("ofl")) {
                        ViewGenericItemStackGUI overflowView = new ViewGenericItemStackGUI(player, "VAULTOVERFLOW", "§7§lVAULT OVERFLOW. §6§lUse /t vault getoverflow", 6, wrapperTown.getOverflowItemStacks().getOverflowItemStackList(), "§7§lOVERFLOW");
                        overflowView.open();
                        return false;
                    }
                    return false;
                }
                return false;
            }
        }
        return false;
    }

    public boolean canFullyAddItem(Player player, ItemStack item) {
        Inventory inventory = player.getInventory();
        HashMap<Integer, ItemStack> leftover = inventory.addItem(item);
        if (leftover.isEmpty()) {
            return true; // The item was fully added
        } else {
            // The item couldn't be fully added, remove the partially added items
            inventory.removeItem(item);
            return false;
        }
    }
}
