package com.rong.tgi.temperature.recipes;

import com.rong.tgi.Helper;
import com.rong.tgi.temperature.items.ItemCoolingPad;
import com.rong.tgi.temperature.items.ItemHeatingPad;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipesHeatingPad extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting craftingInv, World worldIn) {
        ItemStack armorPieces = ItemStack.EMPTY;
        ItemStack heatingPad = ItemStack.EMPTY;
        for (int i = 0; i < craftingInv.getSizeInventory(); ++i) {
            ItemStack stack = craftingInv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof ItemHeatingPad) {
                    if(!heatingPad.isEmpty()) {
                        return false;
                    }
                    heatingPad = stack;
                } else {
                    if(!(stack.getItem() instanceof ItemArmor)) {
                        return false;
                    } else {
                        if(!armorPieces.isEmpty()) {
                            return false;
                        }
                        armorPieces = stack;
                    }
                }
            }
        }
        return (!armorPieces.isEmpty() && !heatingPad.isEmpty()) && (armorPieces != heatingPad);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting craftingInv) {
        ItemStack armorPieces = ItemStack.EMPTY;
        ItemStack heatingPad = ItemStack.EMPTY;
        for(int i = 0; i < craftingInv.getSizeInventory(); ++i) {
            ItemStack stack = craftingInv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof ItemHeatingPad) {
                    heatingPad = stack;
                }
                else {
                    if(stack.getItem() instanceof ItemArmor) {
                        armorPieces = stack;
                    }
                }
            }
        }
        if(!armorPieces.isEmpty() && !heatingPad.isEmpty()) {
            ItemStack result = armorPieces.copy();
            Helper.setPad(result, heatingPad);
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < remaining.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            remaining.set(i, ForgeHooks.getContainerItem(itemstack));
        }

        return remaining;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
