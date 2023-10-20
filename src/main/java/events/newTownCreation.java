package events;

import com.example.myplugin.MyPlugin;
import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import requirements.TownRequirements;

import static com.example.myplugin.MyPlugin.listOfTownsRequirements;

public class newTownCreation implements Listener {
    @EventHandler
    public void onTownCreate(NewTownEvent event) {
        event.getTown().setManualTownLevel(1);
        event.getTown().save();
        Bukkit.getServer().getConsoleSender().sendMessage("Town: " + event.getTown().getName() + ", Manual Level: " + event.getTown().getManualTownLevel());
        listOfTownsRequirements.addTownToList(new TownRequirements(event.getTown(), event.getTown().getManualTownLevel()));
        for (TownRequirements townRequirements : listOfTownsRequirements.getTownRequirementsList()) {
            Bukkit.getServer().getConsoleSender().sendMessage("" + townRequirements.getTown() + ", " + townRequirements.getLevel());
        }
    }

    @EventHandler
    public void onTownDelete(DeleteTownEvent event) {
        for (TownRequirements townRequirements : listOfTownsRequirements.getTownRequirementsList()) {
            if (townRequirements.getTown().getName() == event.getTownName()) {
                listOfTownsRequirements.removeTownFromList(townRequirements);
            }
        }
    }

    public static void townTransition(Town town) {
        town.setManualTownLevel(1);
        town.save();
    }

}
