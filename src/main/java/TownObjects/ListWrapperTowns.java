package TownObjects;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

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
}
