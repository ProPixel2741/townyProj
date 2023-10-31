package TownObjects;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WrapperTown {

    private Town town;
    private Integer townLevel;
    private List<BundleItemStack> bundleItemStackList;

    public WrapperTown(Town town) {
        this.town = town;
        this.townLevel = town.getManualTownLevel();
        bundleItemStackList = new ArrayList<>();
        initializeItemStackBundles();
    }

    private void initializeItemStackBundles() {
        if (townLevel >= 1) {
            addItemStackBundle(new ItemStack(Material.IRON_INGOT, townLevel*10));
        }
        if (townLevel >= 1) {
            addItemStackBundle(new ItemStack(Material.DIAMOND, Math.floorDiv(townLevel, 5)));
        }
        if (townLevel >= 10) {
            addItemStackBundle(new ItemStack(Material.NETHERITE_INGOT, Math.floorDiv(townLevel, 10)));
        }
    }

    private void addItemStackBundle(ItemStack itemStack) {
        bundleItemStackList.add(new BundleItemStack(itemStack, itemStack.getAmount()));
    }

    public Town getTown() {
        return town;
    }

    public Integer getTownLevel() {
        return townLevel;
    }

    public BundleItemStack getBundleItemStack(ItemStack itemStack) {
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            if (bundleItemStack.getItemStack().getType() == itemStack.getType()) {
                return bundleItemStack;
            }
        }
        return null;
    }

    public List<ItemStack> getRequirementsItemStackList() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            ItemStack itemStack = bundleItemStack.getItemStack();
            itemStack.setAmount(bundleItemStack.getReqItems().getAmount());
            itemStackList.add(itemStack);
        }
        return itemStackList;
    }

    public List<ItemStack> getRemainingItemStackList() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            ItemStack itemStack = bundleItemStack.getItemStack();
            itemStack.setAmount(bundleItemStack.getRemItems().getAmount());
            itemStackList.add(itemStack);
        }
        return itemStackList;
    }

    public List<ItemStack> getCurrentItemStackList() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (BundleItemStack bundleItemStack : bundleItemStackList) {
            ItemStack itemStack = bundleItemStack.getItemStack();
            itemStack.setAmount(bundleItemStack.getCurItems().getAmount());
            itemStackList.add(itemStack);
        }
        return itemStackList;
    }
}
