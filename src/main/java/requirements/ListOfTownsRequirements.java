package requirements;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class ListOfTownsRequirements {
    private List<TownRequirements> townRequirementsList;
    public ListOfTownsRequirements() {
        this.townRequirementsList = new ArrayList<>();
    }

    public List<TownRequirements> getTownRequirementsList() {
        for (TownRequirements townRequirements : townRequirementsList)
        Bukkit.getServer().getConsoleSender().sendMessage("got: " + townRequirements.getTown());
        return this.townRequirementsList;
    }

    public synchronized void addTownToList(TownRequirements townRequirements) {
        this.townRequirementsList.add(townRequirements);
        Bukkit.getServer().getConsoleSender().sendMessage("added: " + townRequirements.getTown());
    }

    public synchronized void removeTownFromList(TownRequirements townRequirements) {
        this.townRequirementsList.remove(townRequirements);
        Bukkit.getServer().getConsoleSender().sendMessage("deleted: " + townRequirements.getTown());
    }
}
