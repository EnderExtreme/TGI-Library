package com.rong.tgi.temperature.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public class RecipeHelper {

    /*public static getCoolingPad(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt != null && nbt.hasKey("coolingPad", Constants.NBT.TAG_INT)) {
            return nbt.getBoolean("coolingPad");
        }
        return false;
    }*/

    public static void setCoolingPad(ItemStack stack) {
        NBTTagCompound nbt = getStackNBTSafe(stack);
        nbt.setBoolean("coolingPad", true);
        //stack.set
    }

    public static NBTTagCompound getStackNBTSafe(ItemStack stack) {
        if(stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }
}
