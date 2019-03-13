package com.rong.tgi;

import com.rong.tgi.enderio.SAGMillRecipeAutogenerator;
import com.rong.tgi.entities.EntityManaPearl;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void recipesLow(Register<IRecipe> event) {  
		SAGMillRecipeAutogenerator.addRecipe();
	}
	
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event) {
	}
	
	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		IForgeRegistry<Item> r = event.getRegistry();
	}
	
	@SubscribeEvent
	public static void registerEntities(Register<EntityEntry> event) {
		 EntityEntry entry = EntityEntryBuilder.create()
				 .entity(EntityManaPearl.class)
				 .id(new ResourceLocation(TGILibrary.MODID), 1)
				 .name("manapearl")
				 .tracker(64, 4, true)
				 .build();
		 event.getRegistry().register(entry);
	}
}