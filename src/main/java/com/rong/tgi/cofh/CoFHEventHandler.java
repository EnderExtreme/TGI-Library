package com.rong.tgi.cofh;

import com.rong.tgi.cofh.thermalexpansion.FactorizerRecipes;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CoFHEventHandler {
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void recipesLowest(Register<IRecipe> event) {  
		FactorizerRecipes.init(event.getRegistry().iterator());
	}

}
