package Cmds;

import TownObjects.WrapperTown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.ProPixel.TownyProj.TownyProj.listWrapperTowns;

public class LevelUpSubCmd implements CommandExecutor, Listener {
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

        boolean goodToLevelUp = true;
        //test for if remaining is 0 for everything
        for (ItemStack itemStack : wrapperTown.getRemainingItemStackList()) {
            Bukkit.getServer().getConsoleSender().sendMessage("ItemStack: " + itemStack.getType() + ", Amount: " + itemStack.getAmount());
            if (itemStack.getAmount() > 0) {
                goodToLevelUp = false;
                player.sendMessage("§l§c[-] Town still needs: " + itemStack.getAmount() + " more " + itemStack.getType() + " to levelup.");
            }
        }

        if (wrapperTown.getTown().getAccount().getHoldingBalance() < wrapperTown.getMoneyRequired()) {
            goodToLevelUp = false;
            double remainder = wrapperTown.getMoneyRequired() - wrapperTown.getTown().getAccount().getHoldingBalance();
            player.sendMessage("§l§c[-] Town still needs: $" + remainder);
        }

        if (goodToLevelUp) {
            listWrapperTowns.levelUp(wrapperTown);
            player.sendMessage("§l§a[+] Town successfully leveled up to level-" + wrapperTown.getTown().getManualTownLevel() + ".");
            return false;
        }

        return false;
    }
}