package Listeners;

import com.ProPixel.TownyProj.TownyProj;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NewTownCreation implements Listener {
    @EventHandler
    public void onTownCreate(NewTownEvent event) {
        event.getTown().setManualTownLevel(1);
        event.getTown().save();
        Bukkit.getServer().getConsoleSender().sendMessage("Town: " + event.getTown().getName() + ", Manual Level: " + event.getTown().getManualTownLevel());
        TownyProj.initializeSingleTown(event.getTown());
    }
}
