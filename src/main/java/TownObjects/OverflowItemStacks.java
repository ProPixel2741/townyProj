package TownObjects;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OverflowItemStacks {

    private List<ItemStack> overflowItemStackList;

    public OverflowItemStacks() {
        overflowItemStackList = new ArrayList<>();
    }

    public void addOverflowItemStack(ItemStack overflowItemStack) {
        try {
            overflowItemStackList.add(overflowItemStack);
            Bukkit.getServer().getConsoleSender().sendMessage("Added overflow: " + overflowItemStack.getType() + ": " + overflowItemStack.getAmount());
        } catch (Exception e) {
            overflowItemStackList = new ArrayList<>();
            addOverflowItemStack(overflowItemStack);
        }
    }

    public List<ItemStack> getOverflowItemStackList() {
        try {
            return overflowItemStackList;
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Nothing to view");
            overflowItemStackList = new ArrayList<>();
            return overflowItemStackList;
        }
    }

    public void subtractOverflowItemStackList(ItemStack removeItemStack) {
        try {
            for (ItemStack overflowItemStack : overflowItemStackList) {
                if (overflowItemStack.getType() == removeItemStack.getType()) {
                    overflowItemStackList.remove(overflowItemStack);
                    int newAmount = overflowItemStack.getAmount() - removeItemStack.getAmount();
                    overflowItemStack.setAmount(newAmount);
                    overflowItemStackList.add(overflowItemStack);
                    Bukkit.getServer().getConsoleSender().sendMessage("New Overflow amount for: " + overflowItemStack.getType() + ", " + overflowItemStack.getAmount());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
