package WrappedTownObjects.Requirements;

import WrappedTownObjects.TownWrapper;
import com.palmergames.bukkit.towny.object.Town;

public class RequirementsWrapper {
    private RequirementsItemStacks requirementsItemStacks;
    private RequirementsMoney requirementsMoney;

    public RequirementsWrapper(TownWrapper townWrapper) {
        requirementsItemStacks = new RequirementsItemStacks(townWrapper);
        requirementsMoney = new RequirementsMoney();
    }

    public RequirementsItemStacks getRequirementsItemStacks() {
        return requirementsItemStacks;
    }

    public RequirementsMoney getRequirementsMoney() {
        return requirementsMoney;
    }
}
