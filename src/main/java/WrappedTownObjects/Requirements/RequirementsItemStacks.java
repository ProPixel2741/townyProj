package WrappedTownObjects.Requirements;

import WrappedTownObjects.TownWrapper;
import WrappedTownObjects.WrapperSuperClasses.WrapperItemStacks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RequirementsItemStacks extends WrapperItemStacks {
    private Integer townLevel;
    public RequirementsItemStacks(TownWrapper townWrapper) {
        townLevel = townWrapper.getTown().getManualTownLevel();
        initializeRequirementItemStacks();

    }

    private void initializeRequirementItemStacks() {
        if (townLevel >= 1 ) {
            addItemStack(new ItemStack(Material.IRON_INGOT, 10*townLevel));
        }
        if (townLevel >= 3) {
            addItemStack(new ItemStack(Material.DIAMOND, Math.floorDiv(townLevel, 3)));
        }
        if (townLevel >= 10) {
            addItemStack(new ItemStack(Material.NETHERITE_INGOT, Math.floorDiv(townLevel, 5)));
        }
    }
}
