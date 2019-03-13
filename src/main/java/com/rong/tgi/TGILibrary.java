package com.rong.tgi;

import java.util.logging.Logger;

import com.rong.tgi.ClientProxy;
import com.rong.tgi.CommonProxy;
import com.rong.tgi.entities.EntityManaPearl;
import com.rong.tgi.forestry.CharcoalShit;
import com.rong.tgi.gt.GTRecipes;
import com.rong.tgi.reskillable.traits.TraitPassiveDefense;
import com.rong.tgi.reskillable.traits.TraitPassiveMining;
import com.rong.tgi.temperature.handling.ITemperature;
import com.rong.tgi.temperature.handling.Temperature;
import com.rong.tgi.temperature.handling.TemperatureStorage;
import com.rong.tgi.thaumcraft.ThaumcraftAddon;

import codersafterdark.reskillable.api.ReskillableRegistries;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTControlledRegistry;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;

@Mod(modid = TGILibrary.MODID, name = TGILibrary.MODNAME, version = TGILibrary.VERSION, useMetadata = true, dependencies = "after:*;required-after:gregtech")

public class TGILibrary {
	
	@SidedProxy(clientSide = "com.rong.tgi.ClientProxy", serverSide = "com.rong.tgi.ServerProxy")
	
	public static ClientProxy clientProxy;
	public static CommonProxy commonProxy;
	
	@Mod.Instance
    public static TGILibrary instance;
	public static Logger logger;
	
	public static final String MODID = "tgilibrary";
	public static final String MODNAME = "TGI Library";
	public static final String VERSION = "1.6";

	@Mod.EventHandler
	public void construction(FMLConstructionEvent event) {
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CapabilityManager.INSTANCE.register(ITemperature.class, new TemperatureStorage(), Temperature.class);	
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		clientProxy.initModels();
		
		ThaumcraftAddon.initAspects();
		ThaumcraftAddon.initScannables();
		ThaumcraftAddon.initCategories();
		ThaumcraftAddon.initRecipes();
		ThaumcraftAddon.initResearches();
		ThaumcraftAddon.initCards();
		ThaumcraftAddon.initTheoryCraft();
		
		ReskillableRegistries.UNLOCKABLES.registerAll(
                new TraitPassiveMining(),
                new TraitPassiveDefense()
		);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {	  
		CharcoalShit.init();
    }
}

