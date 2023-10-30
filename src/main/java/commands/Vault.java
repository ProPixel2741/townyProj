package commands;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import gui.VaultRemaining;
import gui.VaultView;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import requirements.TownRequirements;
import utils.EmptyEnchant;

import java.util.List;

import static com.example.myplugin.MyPlugin.listOfTownsRequirements;

public class Vault implements CommandExecutor, Listener {

    private Player p;
    private Town playerTown;
    private final Inventory depositInventory = Bukkit.createInventory(null, 54, (ChatColor.GRAY + "VAULT DEPOSIT"));

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command !");
            return false;
        }
        p = (Player) sender;
        try {
            Resident resident = TownyUniverse.getInstance().getResident(p.getName());
            if (!resident.hasTown()) {
                sender.sendMessage("Only players in towns can run this command !");
                return false;
            }
            playerTown = resident.getTown();
        } catch (NotRegisteredException e){
            e.printStackTrace();
            return false;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("deposit")) {

                p.openInventory(depositInventory);

                return false;
            }


                if (args[0].equalsIgnoreCase("view")) {
                    p.sendMessage("1");
                    for (TownRequirements townRequirements : listOfTownsRequirements.getTownRequirementsList()) {
                        if (townRequirements.getTown() == playerTown) {
                            List<ItemStack> currentItems = townRequirements.getCurrentItemSets();
                            VaultView vaultView = new VaultView(p, currentItems);
                            vaultView.open();
                            return false;
                        }
                    }
                    p.sendMessage("You must be in a town to have a town vault.");
                    return false;
                }

                if (args[0].equalsIgnoreCase("remaining")) {
                    for (TownRequirements townRequirements : listOfTownsRequirements.getTownRequirementsList()) {
                        if (townRequirements.getTown() == playerTown) {
                            List<ItemStack> currentItems = townRequirements.getCurrentItemSets();
                            List<ItemStack> requirementItems = townRequirements.getRequirementItemSets();
                            VaultRemaining vaultRemaining = new VaultRemaining(p, requirementItems, currentItems);
                            vaultRemaining.open();
                            return false;
                        }
                    }
                }


        }

        return false;
    }
}
