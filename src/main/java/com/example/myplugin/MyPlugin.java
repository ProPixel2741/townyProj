package com.example.myplugin;

import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Town;
import commands.Vault;
import commands.rankup;
import config.ConfigValues;
import events.newTownCreation;
import listener.DepositListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import requirements.ListOfTownsRequirements;
import requirements.TownRequirements;

import static events.newTownCreation.townTransition;

public class MyPlugin extends JavaPlugin implements Listener {
    public static ConfigValues configValues;

    private static MyPlugin instance;

    public static ListOfTownsRequirements listOfTownsRequirements;
    @Override
    public void onEnable() {
        instance = this;
        configValues = new ConfigValues(instance);
        Bukkit.getServer().getConsoleSender().sendMessage("Overflow Config: " + configValues.getConfigOverflowRequirements());
        listOfTownsRequirements = new ListOfTownsRequirements();
        setupEconomy();
        for (Town town : TownyUniverse.getInstance().getTowns()) {
            try {
                town.getManualTownLevel();
                Bukkit.getServer().getConsoleSender().sendMessage("Town: " + town.getName() + ", Manual Level: " + town.getManualTownLevel());
                if (town.getManualTownLevel() < 1) {
                    townTransition(town);
                    Bukkit.getServer().getConsoleSender().sendMessage("Town: " + town.getName() + ", Manual Level: " + town.getManualTownLevel());

                }
            } catch (Exception e) {
                Bukkit.getServer().getConsoleSender().sendMessage("Not a valid town level for: " + town.getName());
            }

            listOfTownsRequirements.addTownToList(new TownRequirements(town, town.getManualTownLevel()));

        }
        getServer().getPluginManager().registerEvents(new DepositListener(), this);
        getServer().getPluginManager().registerEvents(new newTownCreation(), this);
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.TOWN, "rankup", new rankup());
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.TOWN, "vault", new Vault());
    }

    @Override
    public void onDisable() {

    }

    public static Economy econ = null;
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
