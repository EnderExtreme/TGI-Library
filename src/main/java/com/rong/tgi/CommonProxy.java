package com.rong.tgi;

import java.util.Locale;

import com.rong.tgi.enderio.SAGMillRecipeAutogenerator;
import com.rong.tgi.entities.EntityManaPearl;
import com.rong.tgi.gt.GTRecipes;
import com.rong.tgi.gt.tools.TiCToolPlunger;
import com.rong.tgi.gt.tools.TiCToolSaw;
import com.rong.tgi.temperature.recipes.TemperatureRecipes;

import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	public static ToolPart plungerHead;
	public static ToolCore plunger;
	
	public static ToolPart sawHead;
	public static ToolCore saw;
	
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
		
		plungerHead = registerItem(r, new ToolPart(Material.VALUE_Ingot * 2), "plunger_head");
		sawHead = registerItem(r, new ToolPart(Material.VALUE_Ingot * 2), "saw_head");
		
		plunger = registerItem(r, new TiCToolPlunger(), "plunger");
		saw = registerItem(r, new TiCToolSaw(), "saw");
		
		TinkerRegistry.registerToolCrafting(plunger);
		TinkerRegistry.registerToolCrafting(saw);
		
		registerStencil(plungerHead);
		registerStencil(sawHead);
		
		OreDictionary.registerOre("craftingToolPlunger", new ItemStack(plunger, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("craftingToolSaw", new ItemStack(saw, 1, OreDictionary.WILDCARD_VALUE));
		
		ModelRegisterUtil.registerPartModel(plungerHead);
		ModelRegisterUtil.registerToolModel(plunger);
		ModelRegisterUtil.registerPartModel(sawHead);
		ModelRegisterUtil.registerToolModel(saw);
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
	
	protected static void registerStencil(ToolPart part) {
		TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), part));
	}
	
	protected static <T extends Item> T registerItem(IForgeRegistry<Item> registry, T item, String name) { 
		if(!name.equals(name.toLowerCase(Locale.US))) {
			throw new IllegalArgumentException(
					String.format("Unlocalized names need to be all lowercase! Item: %s", name));
		}

		item.setUnlocalizedName(Helper.prefix(name));
		item.setRegistryName(Helper.getResource(name));
		registry.register(item);
		return item;
	}
}