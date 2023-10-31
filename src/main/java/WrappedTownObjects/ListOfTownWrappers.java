package WrappedTownObjects;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class ListOfTownWrappers {
    private List<TownWrapper> townWrapperList = new ArrayList<>();

    public ListOfTownWrappers() {

    }

    public void addTownWrapper(TownWrapper townWrapper) {
        townWrapperList.add(townWrapper);
    }

    public TownWrapper getSpecificTownWrapper(Town town) {
        for (TownWrapper townWrapper : townWrapperList) {
            if (townWrapper.getTown() == town) {
                return townWrapper;
            }
        }
        Bukkit.getServer().getConsoleSender().sendMessage("Town: " + town + ", is not initialized properly.");
        return null;
    }
}
