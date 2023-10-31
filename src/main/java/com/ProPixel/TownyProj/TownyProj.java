package com.ProPixel.TownyProj;

import Cmds.VaultSubCmd;
import NonCommandListeners.VaultDeposit;
import TownObjects.ListWrapperTowns;
import TownObjects.WrapperTown;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Town;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TownyProj extends JavaPlugin {

    public static ListWrapperTowns listWrapperTowns;
    public static TownyProj instance;
    @Override
    public void onEnable() {
        instance = this;
        new InventoryAPI(this).init();
        listWrapperTowns = new ListWrapperTowns();
        initializeTowns();
        getServer().getPluginManager().registerEvents(new VaultDeposit(), this);
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.TOWN, "vault", new VaultSubCmd());
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
        WrapperTown wrapperTown = new WrapperTown(town);
        listWrapperTowns.addWrapperTown(wrapperTown);
    }
}
