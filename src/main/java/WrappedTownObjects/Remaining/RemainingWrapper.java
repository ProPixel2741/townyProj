package WrappedTownObjects.Remaining;

import WrappedTownObjects.TownWrapper;
import com.palmergames.bukkit.towny.object.Town;

public class RemainingWrapper {
    private RemainingItemStacks remainingItemStacks;
    private RemainingMoney remainingMoney;

    public RemainingWrapper(TownWrapper townWrapper) {
        remainingItemStacks = new RemainingItemStacks();
        remainingMoney = new RemainingMoney();
    }

    public RemainingItemStacks getRemainingItemStacks() {
        return remainingItemStacks;
    }

    public RemainingMoney getRemainingMoney() {
        return remainingMoney;
    }
}
