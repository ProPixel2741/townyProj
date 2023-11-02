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
import org.jetbrains.annotations.NotNull;

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
            if (args[0].equalsIgnoreCase("deposit")) {
                player.openInventory(depositInventory);
                return false;
            }
            if (args[0].equalsIgnoreCase("view")) {
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("deposits") || args[1].equalsIgnoreCase("dep")) {
                        ViewGenericItemStackGUI depositView = new ViewGenericItemStackGUI(player, "VAULTDEPOSITVIEW", "§7§lVAULT DEPOSIT VIEW", 6, wrapperTown.getCurrentItemStackList());
                        depositView.open();
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("remaining") || args[1].equalsIgnoreCase("rem")) {
                        ViewGenericItemStackGUI remainingView = new ViewGenericItemStackGUI(player, "VAULTREMAININGVIEW", "§7§lVAULT REMAINING VIEW", 6, wrapperTown.getRemainingItemStackList());
                        remainingView.open();
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("requirements") || args[1].equalsIgnoreCase("req")) {
                        ViewGenericItemStackGUI requirementsView = new ViewGenericItemStackGUI(player, "VAULTREQUIREMENTSVIEW", "§7§lVAULT REQUIREMENTS VIEW", 6, wrapperTown.getRequirementsItemStackList());
                        requirementsView.open();
                        return false;
                    }
                    return false;
                }
                return false;
            }
        }
        return false;
    }
}
