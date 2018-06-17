package com.rong.tgi;

import com.rong.tgi.temperature.items.ItemCoolingPad;
import com.rong.tgi.temperature.items.ItemHeatingPad;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Helper {

    public static void setPad(ItemStack armorStack, ItemStack padStack) {
        NBTTagCompound nbt = getStackNBTSafe(armorStack);
        if(padStack.getItem() instanceof ItemHeatingPad) {
            nbt.setBoolean("coolingPad", false);
            nbt.setBoolean("heatingPad", true);
        }
        if(padStack.getItem() instanceof ItemCoolingPad) {
            nbt.setBoolean("heatingPad", false);
            nbt.setBoolean("coolingPad", true);
        }
    }

    public static NBTTagCompound getStackNBTSafe(ItemStack stack) {
        if(stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }
}
