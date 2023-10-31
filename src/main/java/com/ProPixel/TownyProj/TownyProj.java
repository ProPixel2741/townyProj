package com.ProPixel.TownyProj;

import WrappedTownObjects.ListOfTownWrappers;
import WrappedTownObjects.TownWrapper;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TownyProj extends JavaPlugin {

    public static ListOfTownWrappers listOfTownWrappers;
    @Override
    public void onEnable() {
        initializeTowns();
    }

    @Override
    public void onDisable() {

    }

    private void initializeTowns() {
        for (Town town : TownyUniverse.getInstance().getTowns()) {
            initializeSingleTown(town);
        }
    }

    private void initializeSingleTown(Town town) {
        initializeAllUnleveledTowns(town);
        initializeWrappedTownObjects(town);
    }

    private void initializeAllUnleveledTowns(Town town) {
        try {
            town.getManualTownLevel();
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Invalid town level for town: " + town.getName() + ", " + e);
        }
        if (town.getManualTownLevel() < 1) {
            town.setManualTownLevel(1);
        }
    }

    private void initializeWrappedTownObjects(Town town) {
        TownWrapper townWrapper = new TownWrapper(town);
        listOfTownWrappers.addTownWrapper(townWrapper);
    }

}
