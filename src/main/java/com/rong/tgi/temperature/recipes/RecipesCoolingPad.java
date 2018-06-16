package com.rong.tgi.temperature.recipes;

import com.rong.tgi.temperature.items.ItemCoolingPad;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipesCoolingPad extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting craftingInv, World worldIn) {
        ItemStack armorPieces = ItemStack.EMPTY;
        ItemStack coolingPad = ItemStack.EMPTY;
        for (int i = 0; i < craftingInv.getSizeInventory(); ++i) {
            ItemStack stack = craftingInv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof ItemCoolingPad) {
                    if(!coolingPad.isEmpty()) {
                        return false;
                    }
                    coolingPad = stack;
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
        return (!armorPieces.isEmpty() && !coolingPad.isEmpty()) && (armorPieces != coolingPad);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting craftingInv) {
        ItemStack armorPieces = ItemStack.EMPTY;
        ItemStack coolingPad = ItemStack.EMPTY;
        for(int i = 0; i < craftingInv.getSizeInventory(); ++i) {
            ItemStack stack = craftingInv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof ItemCoolingPad) {
                    coolingPad = stack;
                }
                else {
                    if(stack.getItem() instanceof ItemArmor) {
                        armorPieces = stack;
                    }
                }
            }
        }
        if(!armorPieces.isEmpty() && !coolingPad.isEmpty()) {
            ItemStack result = armorPieces.copy();
            /*CircleGemType appliedGem = ((ItemGem)gem.getItem()).type;
            CircleGemType toolGem = CircleGemHelper.getGem(tool);
            int gemRelation = appliedGem.getRelation(toolGem);
            if(gemRelation == -1) {
                CircleGemHelper.setGem(result, CircleGemType.NONE);*/
            RecipeHelper.setCoolingPad(result);
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
