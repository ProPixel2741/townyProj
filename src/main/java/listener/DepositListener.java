package listener;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import requirements.TownRequirements;

import static com.example.myplugin.MyPlugin.listOfTownsRequirements;

public class DepositListener implements Listener {

    TownRequirements playerTownRequirements = null;

    @EventHandler
    public void onDepositVaultClose(InventoryCloseEvent event) throws NotRegisteredException {
        Resident resident = TownyUniverse.getInstance().getResident(event.getPlayer().getName());
        if (!resident.hasTown()) {
            return;
        }
        for (TownRequirements tr : listOfTownsRequirements.getTownRequirementsList()) {
            if (tr.getTown() == resident.getTown()) {
                playerTownRequirements = tr;
            }
        }

        if (playerTownRequirements == null) {
            return;
        }

        if (event.getView().getTitle().equals(ChatColor.GRAY + "VAULT DEPOSIT")) {
            for (ItemStack itemStack : event.getInventory().getContents()) {
                if (itemStack != null) {
                    boolean ifItemExistsInRequirements = false;
                    for (ItemStack reqItems : playerTownRequirements.getRequirementItemSets()) {
                        if (itemStack.getType() == reqItems.getType()) {
                            ifItemExistsInRequirements = true;
                            playerTownRequirements.addCurrentItemSets(itemStack, event.getPlayer());
                        }
                    }
                    if (!ifItemExistsInRequirements) {
                        event.getPlayer().getInventory().addItem(itemStack);
                    }
                }
            }
            event.getInventory().clear();
        }
    }
}
