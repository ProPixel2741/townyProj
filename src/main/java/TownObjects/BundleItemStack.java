package TownObjects;

import TownObjects.Current.CurItems;
import TownObjects.Remaining.RemItems;
import TownObjects.Requirements.ReqItems;
import org.bukkit.inventory.ItemStack;

public class BundleItemStack {
    private ReqItems reqItems;
    private CurItems curItems;
    private RemItems remItems;

    private ItemStack itemStack;
    public BundleItemStack(ItemStack itemStack, Integer reqAmount) {
        this.itemStack = itemStack;
        reqItems = new ReqItems(reqAmount);
        curItems = new CurItems();
        remItems = new RemItems(reqItems, curItems);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ReqItems getReqItems() {
        return reqItems;
    }

    public RemItems getRemItems() {
        return remItems;
    }

    public CurItems getCurItems() {
        return curItems;
    }

}
