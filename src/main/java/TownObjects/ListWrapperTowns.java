package TownObjects;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ListWrapperTowns {
    private List<WrapperTown> wrapperTownList;

    public ListWrapperTowns() {
        wrapperTownList = new ArrayList<>();
    }

    public void addWrapperTown(WrapperTown wrapperTown) {
        wrapperTownList.add(wrapperTown);
    }

    public void removeWrapperTown(WrapperTown wrapperTown) {
        try {
            wrapperTownList.remove(wrapperTown);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeWrapperTownByName(String townName) {
        Bukkit.getServer().getConsoleSender().sendMessage("TOWNNAME" + townName);
        for (WrapperTown wrapperTown : wrapperTownList) {
            Bukkit.getServer().getConsoleSender().sendMessage("TOMATCH: " + wrapperTown.getTown().getName());
            if (wrapperTown.getTown().getName().equals(townName)) {
                Bukkit.getServer().getConsoleSender().sendMessage("MATCH!!!!");
                try {
                    wrapperTownList.remove(wrapperTown);
                    Bukkit.getServer().getConsoleSender().sendMessage("Town: " + townName + ", removed as TownWrapper.");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public WrapperTown getWrapperTownByTown(Town town) {
        for (WrapperTown wrapperTown : wrapperTownList) {
            if (wrapperTown.getTown() == town) {
                return wrapperTown;
            }
        }
        return null;
    }

    public WrapperTown getWrapperTownByPlayerName(String playerName) {
        Town town;
        try {
            Resident resident = TownyUniverse.getInstance().getResident(playerName);
            if (!resident.hasTown()) {
                return null;
            }
            town = resident.getTown();
        } catch (NotRegisteredException e) {
            throw new RuntimeException(e);
        }
        return getWrapperTownByTown(town);
    }

    public WrapperTown getWrapperTownByPlayer(Player player) {
        return getWrapperTownByPlayerName(player.getName());
    }

    public WrapperTown getWrapperTownByHumanEntity(HumanEntity humanEntity) {
        return getWrapperTownByPlayerName(humanEntity.getName());
    }

    public void levelUp(WrapperTown wrapperTown) {
        //get excess
        ExcessItemStacksForLevelUp excess = new ExcessItemStacksForLevelUp(wrapperTown);

        wrapperTownList.remove(wrapperTown);
        wrapperTown.getTown().setManualTownLevel(wrapperTown.getTown().getManualTownLevel() + 1);
        wrapperTown.getTown().save();
        WrapperTown leveledUpTownWrapper = new WrapperTown(wrapperTown.getTown());
        wrapperTownList.add(leveledUpTownWrapper);

        for (ItemStack excessItemStack : excess.getItemStackList()) {
            Bukkit.getServer().getConsoleSender().sendMessage("Type: " + excessItemStack.getType() + ", Amount: " + excessItemStack.getAmount());

            try {
                BundleItemStack matchingBundleItemStack = leveledUpTownWrapper.getBundleItemStack(excessItemStack);
                matchingBundleItemStack.getCurItems().addAmount(matchingBundleItemStack, excessItemStack.getAmount());
            } catch (Exception e) {
                Bukkit.getServer().getConsoleSender().sendMessage("No BundleItemStack Found!");
            }
        }
    }
}
