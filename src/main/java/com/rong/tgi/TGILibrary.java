package com.rong.tgi;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.apache.logging.log4j.Logger;

import com.rong.tgi.nc.RadiationEventHandler;
import com.rong.tgi.reskillable.traits.TraitDiggersLuck;
import com.rong.tgi.reskillable.traits.TraitPassiveDefense;
import com.rong.tgi.reskillable.traits.TraitPassiveMining;
import com.rong.tgi.temperature.handling.ITemperature;
import com.rong.tgi.temperature.handling.Temperature;
import com.rong.tgi.temperature.handling.TemperatureStorage;
import com.rong.tgi.thaumcraft.ThaumcraftAddon;

import codersafterdark.reskillable.api.ReskillableRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = TGILibrary.MODID, name = TGILibrary.MODNAME, version = TGILibrary.VERSION, useMetadata = true, dependencies = "after:*")

public class TGILibrary {

    @SidedProxy(clientSide = "com.rong.tgi.ClientProxy", serverSide = "com.rong.tgi.ServerProxy")

    public static ClientProxy clientProxy;
    public static CommonProxy commonProxy;

    @Mod.Instance
    public static TGILibrary instance;
    public static Logger logger;

    public static final String MODID = "tgi";
    public static final String MODNAME = "TGI Library";
    public static final String VERSION = "2.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(ITemperature.class, new TemperatureStorage(), Temperature.class);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        clientProxy.initModels();

        if (Helper.isModLoaded("thaumcraft")) {
            ThaumcraftAddon.initAspects();
            ThaumcraftAddon.initCategories();
            ThaumcraftAddon.initResearches();
            ThaumcraftAddon.initCards();
            ThaumcraftAddon.initTheoryCraft();
        }

        //if (Helper.isModLoaded("reskillable")) {
            //ReskillableRegistries.UNLOCKABLES.registerAll(new TraitPassiveMining(), new TraitPassiveDefense(), new TraitDiggersLuck());
        //}
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        //if (Helper.isModLoaded("nuclearcraft")) { //TODO: use new API
            //RadiationEventHandler.addRadSources();
        //}
    }
}