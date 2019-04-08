package com.rong.tgi.thaumcraft;

import org.apache.commons.lang3.StringUtils;

import com.rong.tgi.TGILibrary;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.lib.Constellations;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.api.research.theorycraft.TheorycraftManager;
import thaumcraft.common.config.ConfigAspects;
import thaumcraft.common.lib.crafting.DustTriggerOre;
import thaumcraft.common.lib.crafting.DustTriggerSimple;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.utils.Utils;
import zmaster587.advancedRocketry.api.AdvancedRocketryBlocks;

public class ThaumcraftAddon {
	
	public static Aspect SPACE = new Aspect("Cosmos", 8388736, new Aspect[] {Aspect.FLIGHT, Aspect.DARKNESS}, new ResourceLocation(TGILibrary.MODID, "textures/aspects/cosmos.png"), 1);
	
	public static void initAspects() {
		
		ThaumcraftApi.registerObjectTag("turfMoon", new AspectList().add(SPACE, 10));
		appendAspects(new ItemStack(Blocks.END_STONE), new AspectList().add(SPACE, 5));
		appendAspects(new ItemStack(Blocks.END_BRICKS), new AspectList().add(SPACE, 3));
		appendAspects(new ItemStack(Blocks.END_ROD), new AspectList().add(SPACE, 2));
		appendAspects(new ItemStack(Blocks.END_BRICKS), new AspectList().add(SPACE, 3));
	}
	
	public static void initScannables() {
		ScanningManager.addScannableThing(new ScanAstralSky());
	}
	
	public static void initCards() {
		TheorycraftManager.registerCard(CardCosmos.class);
	}
	
	public static void initTheoryCraft() {
	    TheorycraftManager.registerAid(new AidBasicCelestial());
	}
	
	public static void initCategories() {
		ResourceLocation BACK_OVER = new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png");
		ResearchCategories.registerCategory("CELESTIAL", (String)null, new AspectList(), new ResourceLocation("astralsorcery", "textures/items/looking_glass.png"), new ResourceLocation(TGILibrary.MODID, "textures/gui/celestial_back.png"), BACK_OVER);
	}
	
	public static void initResearches() {
		ThaumcraftApi.registerResearchLocation(new ResourceLocation(TGILibrary.MODID, "research/astral"));
		ThaumcraftApi.registerResearchLocation(new ResourceLocation(TGILibrary.MODID, "research/general"));
	}
	
	public static void initRecipes() {
		IDustTrigger.registerDustTrigger(new DustTriggerOre("MANAORE", "oreIron", new ItemStack(Item.getByNameOrId("thermalfoundation:ore"), 1, 8)));
		IDustTrigger.registerDustTrigger(new DustTriggerOre("MANAORE", "oreSandIron", new ItemStack(Item.getByNameOrId("thermalfoundation:ore"), 1, 8)));
		IDustTrigger.registerDustTrigger(new DustTriggerOre("MANAORE", "oreNetherrackIron", new ItemStack(Item.getByNameOrId("thermalfoundation:ore"), 1, 8)));
		IDustTrigger.registerDustTrigger(new DustTriggerOre("MANAORE", "oreEndstoneIron", new ItemStack(Item.getByNameOrId("thermalfoundation:ore"), 1, 8)));
		IDustTrigger.registerDustTrigger(new DustTriggerOre("MANAORE", "oreGravelIron", new ItemStack(Item.getByNameOrId("thermalfoundation:ore"), 1, 8)));
		
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_0"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreIron", new AspectList().add(Aspect.ALCHEMY, 2)));
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_1"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreBedrockIron", new AspectList().add(Aspect.ALCHEMY, 2)));
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_2"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreSandIron", new AspectList().add(Aspect.ALCHEMY, 2)));
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_3"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreSandstoneIron", new AspectList().add(Aspect.ALCHEMY, 2)));
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_4"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreRedSandstoneIron", new AspectList().add(Aspect.ALCHEMY, 2)));
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_5"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreNetherrackIron", new AspectList().add(Aspect.ALCHEMY, 2)));
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_6"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreEndstoneIron", new AspectList().add(Aspect.ALCHEMY, 2)));
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_7"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreGravelIron", new AspectList().add(Aspect.ALCHEMY, 2)));
		
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "imbued_marble"), new CrucibleRecipe("IMBUEDMARBLE", new ItemStack(Item.getByNameOrId("astralsorcery:blockmarble"), 2), "stoneMarble", new AspectList().add(Aspect.MAGIC, 2)));
		//ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(TGILibrary.MODID + Constellations.discidia.getUnlocalizedName() + "ConstellationPaper"), new InfusionRecipe("DISCIDIAPAPER", null, 0, null, null, null));
	}
	
	private static void appendAspects(ItemStack stack, AspectList toAdd) {
		toAdd = toAdd.copy(); {
			AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
			if(al != null)
				toAdd = toAdd.add(al);
		}
		CommonInternals.objectTags.put(CommonInternals.generateUniqueItemstackId(stack), toAdd);
	}
}