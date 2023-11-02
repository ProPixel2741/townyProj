package TownObjects.Current;

import TownObjects.BundleItemStack;
import TownObjects.Remaining.RemItems;
import TownObjects.Requirements.ReqItems;

public class CurItems {
    private Integer amount;

    public CurItems() {
        amount = 0;
    }

    public CurItems(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void addAmount(BundleItemStack bundleItemStack, Integer addAmount) {
        //TODO: add check for config setting for overflow allowed
        amount += addAmount;
        RemItems newRemItems = new RemItems(bundleItemStack.getReqItems(), this);
        //update remaining amount
        bundleItemStack.getRemItems().setNewRemItemsAmount(newRemItems.getAmount());
    }
}
