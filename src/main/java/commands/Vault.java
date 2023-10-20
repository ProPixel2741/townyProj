package commands;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import requirements.TownRequirements;
import utils.EmptyEnchant;

import java.util.List;
import java.util.Objects;

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
                            p.sendMessage("2");
                            List<ItemStack> currentItems = townRequirements.getCurrentItemSets();
                            final Inventory vaultViewInventory = Bukkit.createInventory(null, 54, (ChatColor.GRAY + "VAULT VIEW"));
                            for (int i = 0; i < currentItems.size(); i++) {
                                p.sendMessage("Amount: " + currentItems.get(i).getAmount());
                                ItemStack currentItemStack = currentItems.get(i);
                                p.sendMessage("Amount: " + currentItems.get(i).getAmount());
                                if (currentItemStack.getAmount() > 64) {
                                    ItemMeta itemMeta = currentItemStack.getItemMeta();
                                    p.sendMessage("Amount: " + currentItems.get(i).getAmount());
                                    itemMeta.setDisplayName(ChatColor.GOLD + "" + currentItems.get(i).getAmount() + " deposited");
                                    p.sendMessage("Amount: " + currentItems.get(i).getAmount());
                                    itemMeta.setDisplayName(ChatColor.BOLD + itemMeta.getDisplayName());
                                    currentItemStack.setItemMeta(itemMeta);
                                    EmptyEnchant emptyEnchant = new EmptyEnchant(NamespacedKey.fromString("key"));
                                    currentItemStack.addUnsafeEnchantment(emptyEnchant, 1); //anything
                                }
                                vaultViewInventory.setItem(i, currentItems.get(i));
                            }
                            p.openInventory(vaultViewInventory);
                            return false;
                        }
                    }
                    p.sendMessage("You must be in a town to have a town vault.");
                    return false;
                }


        }

        return false;
    }
}
