package WrappedTownObjects;

import WrappedTownObjects.Current.CurrentWrapper;
import WrappedTownObjects.Remaining.RemainingWrapper;
import WrappedTownObjects.Requirements.RequirementsWrapper;
import com.palmergames.bukkit.towny.object.Town;

public class TownWrapper {
    private RequirementsWrapper requirementsWrapper;
    private CurrentWrapper currentWrapper;
    private RemainingWrapper remainingWrapper;
    private Town town;

    public TownWrapper(Town town) {
        this.town = town;
        requirementsWrapper = new RequirementsWrapper(this);
        //currentWrapper = new CurrentWrapper(this);
        //remainingWrapper = new RemainingWrapper(this);
    }

    public RequirementsWrapper getRequirementsWrapper() {
        return requirementsWrapper;
    }

    public CurrentWrapper getCurrentWrapper() {
        return currentWrapper;
    }

    public RemainingWrapper getRemainingWrapper() {
        return remainingWrapper;
    }

    public Town getTown() {
        return town;
    }
}
