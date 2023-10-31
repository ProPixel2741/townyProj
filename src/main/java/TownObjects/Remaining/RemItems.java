package TownObjects.Remaining;

import TownObjects.Current.CurItems;
import TownObjects.Requirements.ReqItems;

public class RemItems {
    private Integer amount;

    public RemItems(ReqItems reqItems, CurItems curItems) {
        int differenceAmount = reqItems.getAmount() - curItems.getAmount();
        if (differenceAmount < 0) {
            differenceAmount = 0;
        }
        amount = differenceAmount;
    }

    public void setNewRemItemsAmount(Integer newAmount) {
        this.amount = newAmount;
    }

    public Integer getAmount() {
        return amount;
    }
}
