package commands;

import com.example.myplugin.MyPlugin;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import requirements.TownRequirements;

import java.util.List;
import java.util.Objects;

import static com.example.myplugin.MyPlugin.listOfTownsRequirements;

public class rankup implements CommandExecutor {
    private Town playerTown;
    private Resident resident;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command !");
            return false;
        }
        Player p = (Player) sender;
        try {
            resident = TownyUniverse.getInstance().getResident(p.getName());
            if (!resident.hasTown()) {
                sender.sendMessage("Only players in towns can run this command !");
                return false;
            }
            playerTown = resident.getTown();
        } catch (NotRegisteredException e){
            e.printStackTrace();
            return false;
        }
        if(args.length == 0) { // if no args or help
            //sender.sendMessage("You are in a town!");
//            int totalDiamonds = 0;
//            ItemStack[] inv = p.getInventory().getContents();
//            ItemStack stackWithDiamonds = null;
//            for (ItemStack i : inv) {
//                try {
//                    if (i != null && i.getType() != null && i.getType() == Material.DIAMOND) {
//                        totalDiamonds += i.getAmount();
//                        stackWithDiamonds = i;
//                    }
//                } catch (NullPointerException npe) {
//                    Bukkit.getServer().getConsoleSender().sendMessage("i: " + i + ", is null");
//                }
//            }
//            int requirementCount = 3;
//            if (totalDiamonds < 1) {
//                sender.sendMessage("You need at least 1 diamond to rankup your town");
//                requirementCount -= 1;
//            }
//            if (playerTown.getAccount().getHoldingBalance() < 500) {
//                sender.sendMessage("Town needs at least $500 to rankup");
//                requirementCount -= 1;
//            }
//            if (econ.getBalance(p) < 500) {
//                sender.sendMessage("You as the player need at least $500 to rankup the town");
//                requirementCount -= 1;
//            }
//            if (requirementCount != 3) {
//                sender.sendMessage("Only (" + requirementCount + "/3) requirements fulfilled.");
//                return false;
//            }

            //econ.withdrawPlayer(p, 500);
            //playerTown.getAccount().setBalance(playerTown.getAccount().getHoldingBalance() - 500, "For ranking up");
            //stackWithDiamonds.setAmount(stackWithDiamonds.getAmount() - 1);
            boolean townIsInRequirementsList = false;
            p.sendMessage("PlayerTown: " + playerTown);
            for (TownRequirements townRequirements : listOfTownsRequirements.getTownRequirementsList()) {
                p.sendMessage("Town: " + townRequirements.getTown());
                if (townRequirements.getTown() == playerTown) {
                    townIsInRequirementsList = true;
                    if (!townRequirements.goodToRankupNoRequirementsRemaining()) {
                        p.sendMessage("Incomplete requirements needed to rankup town");
                        return false;
                    }
                    townRequirements.rankupByTakingRequiredItemsAndMoneyAway();
                }
            }

            if (!townIsInRequirementsList) {
                p.sendMessage("Your town is not registered for some reason!");
                return false;
            }

            try {
                sender.sendMessage("Level before: " + playerTown.getManualTownLevel());
                playerTown.setManualTownLevel(playerTown.getManualTownLevel() + 1);
                playerTown.save();
                sender.sendMessage("Rankup successful: now level: " + playerTown.getManualTownLevel());

            } catch (Exception e) {
                sender.sendMessage("Rankup failed");
            }
            return false;
        }
        return false;
    }
}
