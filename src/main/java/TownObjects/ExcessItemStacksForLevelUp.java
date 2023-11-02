package TownObjects;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExcessItemStacksForLevelUp {

    List<ItemStack> itemStackList;
    public ExcessItemStacksForLevelUp(WrapperTown wrapperTown) {
        itemStackList = new ArrayList<>();
        for (BundleItemStack bundleItemStack : wrapperTown.getBundleItemStackList()) {
            int excess = bundleItemStack.getCurItems().getAmount() - bundleItemStack.getReqItems().getAmount();
            if (excess > 0) {
                itemStackList.add(new ItemStack(bundleItemStack.getItemStack().getType(), excess));
            }
        }
    }

    public List<ItemStack> getItemStackList() {
        return itemStackList;
    }
}
