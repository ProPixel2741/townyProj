package WrappedTownObjects.WrapperSuperClasses;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WrapperItemStacks {
    private List<ItemStack> itemStackList = new ArrayList<>();

    public List<ItemStack> getItemStackList() {
        return itemStackList;
    }

    public void addItemStack(ItemStack addedItemStack) {
        //TODO: add override of this method in remaining to test if config allows oversupply of item beyond requirements
        ItemStack itemStackAlreadyInList = getItemStack(addedItemStack.getType());
        if (itemStackAlreadyInList == null) {
            itemStackList.add(addedItemStack);
            return;
        }
        int newAmount = itemStackAlreadyInList.getAmount() + addedItemStack.getAmount();
        itemStackAlreadyInList.setAmount(newAmount);
    }

    public void subtractItemStack(ItemStack subtractItemStack) {
        ItemStack itemStackAlreadyInList = getItemStack(subtractItemStack.getType());
        if (itemStackAlreadyInList == null) {return;}
        int newAmount = itemStackAlreadyInList.getAmount() - subtractItemStack.getAmount();
        if (newAmount < 0) {
            newAmount = 0;
        }
        itemStackAlreadyInList.setAmount(newAmount);
    }

    public ItemStack getItemStack(Material material) {
        for (ItemStack itemStackAlreadyInList : itemStackList) {
            if (itemStackAlreadyInList.getType() == material) {
                return itemStackAlreadyInList;
            }
        }
        return null;
    }

    public void removeItemStackFromList(Material material) {
        ItemStack itemStackToRemove = getItemStack(material);
        if (itemStackToRemove == null) {return;}
        itemStackList.remove(itemStackToRemove);
    }
}
