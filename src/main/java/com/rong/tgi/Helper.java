package com.rong.tgi;

import com.rong.tgi.temperature.items.ItemCoolingPad;
import com.rong.tgi.temperature.items.ItemHeatingPad;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.ModHandler;

import java.util.Arrays;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

    public static boolean checkIfNBTNotNull(ItemStack stack, String key) {
        return getStackNBTSafe(stack).hasKey(key);
    }

	public static boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
	
	public static String resource(String name) {
		return String.format("%s:%s", TGILibrary.MODID, name.toLowerCase(Locale.US));
	}
	public static String prefix(String name) {
		return String.format("%s.%s", TGILibrary.MODID, name.toLowerCase(Locale.US));
	}

	public static ResourceLocation getResource(String res) {
		return new ResourceLocation(TGILibrary.MODID, res);
	}

}
