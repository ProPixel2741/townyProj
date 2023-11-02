package Listeners;

import com.ProPixel.TownyProj.TownyProj;
import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownDisband implements Listener {
    @EventHandler
    public void onTownDisband(DeleteTownEvent event) {
        String townName = event.getTownName();
        TownyProj.listWrapperTowns.removeWrapperTownByName(townName);
        Bukkit.getServer().getConsoleSender().sendMessage("DeleteTownEvent Acknowledged.");
    }
}
