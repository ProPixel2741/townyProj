package WrappedTownObjects.Current;

import WrappedTownObjects.TownWrapper;
import com.palmergames.bukkit.towny.object.Town;

public class CurrentWrapper {
    private CurrentItemStacks currentItemStacks;
    private CurrentMoney currentMoney;

    public CurrentWrapper(TownWrapper townWrapper) {
        currentItemStacks = new CurrentItemStacks();
        currentMoney = new CurrentMoney();
    }

    public CurrentItemStacks getCurrentItemStacks() {
        return currentItemStacks;
    }

    public CurrentMoney getCurrentMoney() {
        return currentMoney;
    }
}
