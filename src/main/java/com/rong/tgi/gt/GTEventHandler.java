package com.rong.tgi.gt;

import com.rong.tgi.temperature.recipes.TemperatureRecipes;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class GTEventHandler {
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void recipesLow(Register<IRecipe> event) {  
		GTRecipes.advancedRocketryPort();
		GTRecipes.immersiveEngineeringAddon();
		GTRecipes.fuckGA();
		GTRecipes.dyes();
		TemperatureRecipes.equipPads();
	}

}
