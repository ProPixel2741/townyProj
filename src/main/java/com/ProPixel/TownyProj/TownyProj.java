package com.ProPixel.TownyProj;

import Cmds.LevelUpSubCmd;
import Cmds.VaultSubCmd;
import Listeners.NewTownCreation;
import Listeners.TownDisband;
import NonCommandListeners.VaultDeposit;
import TownObjects.ListWrapperTowns;
import TownObjects.WrapperTown;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Town;
import mc.obliviate.inventory.InventoryAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class TownyProj extends JavaPlugin {

    public static ListWrapperTowns listWrapperTowns;
    public static TownyProj instance;
    public static Economy econ = null;
    @Override
    public void onEnable() {
        instance = this;
        setupEconomy();
        new InventoryAPI(this).init();
        listWrapperTowns = new ListWrapperTowns();
        initializeTowns();
        getServer().getPluginManager().registerEvents(new VaultDeposit(), this);
        getServer().getPluginManager().registerEvents(new NewTownCreation(), this);
        getServer().getPluginManager().registerEvents(new TownDisband(), this);
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.TOWN, "vault", new VaultSubCmd());
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.TOWN, "levelup", new LevelUpSubCmd());
    }

    @Override
    public void onDisable() {

    }

    private void initializeTowns() {
        for (Town town : TownyUniverse.getInstance().getTowns()) {
            initializeSingleTown(town);
        }
    }

    public static void initializeSingleTown(Town town) {
        initializeManualLevelOfTown(town);
        initializeWrappedTownObject(town);
    }

    private static void initializeManualLevelOfTown(Town town) {
        try {
            town.getManualTownLevel();
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Invalid town level for town: " + town.getName() + ", " + e);
        }
        if (town.getManualTownLevel() < 1) {
            town.setManualTownLevel(1);
        }
    }

    private static void initializeWrappedTownObject(Town town) {
        WrapperTown wrapperTown = new WrapperTown(town);
        listWrapperTowns.addWrapperTown(wrapperTown);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
