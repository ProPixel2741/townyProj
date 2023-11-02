package MiscUtils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemCounterForDepositOrReturnMessage {

    private List<ItemStack> itemStackList;
    public ItemCounterForDepositOrReturnMessage() {
        itemStackList = new ArrayList<>();
    }

    public void addItemStackToList(ItemStack itemStack) {
        for (ItemStack itemStackInList : itemStackList) {
            if (itemStackInList.getType() == itemStack.getType()) {
                int newAmount = itemStackInList.getAmount() + itemStack.getAmount();
                itemStackInList.setAmount(newAmount);
                return;
            }
        }
        itemStackList.add(itemStack);
    }

    public List<ItemStack> getItemStackList() {
        return itemStackList;
    }
}
