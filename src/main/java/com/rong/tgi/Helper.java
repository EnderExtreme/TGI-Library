package com.rong.tgi;

import com.rong.tgi.temperature.items.ItemCoolingPad;
import com.rong.tgi.temperature.items.ItemHeatingPad;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.minecraftforge.fml.common.registry.GameRegistry;
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
import net.minecraftforge.fml.common.Loader;

public class Helper {

    public static EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    static String[] enderIOMachines = { "enderio:block_simple_furnace", "enderio:block_simple_alloy_smelter", "enderio:block_simple_sag_mill", "enderio:block_powered_spawner", "enderio:block_farm_station", "enderio:block_soul_binder", "enderio:block_attractor_obelisk", "enderio:block_aversion_obelisk", "enderio:block_inhibitor_obelisk", "enderio:block_experience_obelisk", "enderio:block_weather_obelisk", "enderio:block_slice_and_splice", "enderio:block_power_monitor", "enderio:block_sag_mill", "enderio:block_wired_charger", "enderio:block_normal_wireless_charger", "enderio:block_alloy_smelter", "enderio:block_vat", "enderio:block_painter", "enderio:block_buffer", "enderio:block_impulse_hopper", "enderio:block_crafter" };

    private static final ConcurrentMap<String, Boolean> isModLoadedCache = new ConcurrentHashMap<>();

    public static ItemStack makeStack(String itemName, int meta, int stackSize) {
        return GameRegistry.makeItemStack(itemName, meta, stackSize, "");
    }

    public static ItemStack makeStack(String itemName, int stackSize) {
        return GameRegistry.makeItemStack(itemName, 0, stackSize, "");
    }

    public static boolean isModLoaded(String modid) {
        if (isModLoadedCache.containsKey(modid)) {
            return isModLoadedCache.get(modid);
        }
        boolean isLoaded = Loader.instance().getIndexedModList().containsKey(modid);
        isModLoadedCache.put(modid, isLoaded);
        return isLoaded;
    }

    public static void setPad(ItemStack armorStack, ItemStack padStack) {
        NBTTagCompound nbt = getStackNBTSafe(armorStack);
        if (padStack.getItem() instanceof ItemHeatingPad) {
            nbt.setBoolean("coolingPad", false);
            nbt.setBoolean("heatingPad", true);
        }
        if (padStack.getItem() instanceof ItemCoolingPad) {
            nbt.setBoolean("heatingPad", false);
            nbt.setBoolean("coolingPad", true);
        }
    }

    public static NBTTagCompound getStackNBTSafe(ItemStack stack) {
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }

    public static boolean checkIfNBTNotNull(ItemStack stack, String key) {
        return getStackNBTSafe(stack).hasKey(key);
    }

}
