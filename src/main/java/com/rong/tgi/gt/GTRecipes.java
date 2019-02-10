package com.rong.tgi.gt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Streams;
import com.rong.tgi.Helper;
import com.rong.tgi.gt.GTMTEs.MTERecipes;

import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.crafting.AlloyRecipe;
import blusunrize.immersiveengineering.api.crafting.CokeOvenRecipe;
import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.api.crafting.FermenterRecipe;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.IERecipes;
import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials.Tier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItem2;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlazedTerracotta;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.items.ItemsTC;
import zmaster587.advancedRocketry.api.AdvancedRocketryAPI;
import zmaster587.advancedRocketry.api.AdvancedRocketryItems;
import zmaster587.advancedRocketry.armor.ItemSpaceArmor;

public class GTRecipes {
	
	public static void advancedRocketryPort() {
		
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().inputs(new ItemStack(Items.BONE)).fluidInputs(Materials.Nitrogen.getFluid(50)).outputs(new ItemStack(Items.DYE, 10, 15)).EUt(32).duration(300).buildAndRegister();

		for(ResourceLocation key : Item.REGISTRY.getKeys()) {
			Item item = Item.REGISTRY.getObject(key);
			if(item instanceof ItemArmor && !(item instanceof ItemSpaceArmor)) {
				ItemStack enchanted = new ItemStack(item);
				enchanted.addEnchantment(AdvancedRocketryAPI.enchantmentSpaceProtection, 1);
				if(((ItemArmor) item).armorType == EntityEquipmentSlot.CHEST) {
					RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().inputs(new ItemStack(AdvancedRocketryItems.itemPressureTank, 1, 3), new ItemStack(item)).fluidInputs(Materials.Ice.getFluid(2592)).outputs(enchanted).EUt(286).duration(1200).buildAndRegister();
				}
				else {
					RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().inputs(new ItemStack(item)).fluidInputs(Materials.Ice.getFluid(2592)).outputs(enchanted).EUt(286).duration(600).buildAndRegister();
				}
			}
		}
	}
	
	/*
	 * TODO:
	 * 1. AE2 CABLES
	 * 2. IF CONVEYOR BELTS
	 * 3. FUSED QUARTZ
	 * 4. THAUMCRAFT NITOR
	 * 5. THERMAL FOUNDATION ROCKWOOL
	 * 6. ITEMFRAMES
	 * 7. CANDLES
	 * 8. STAINED PLANKS
	 * 9. ENDERIO SHIT
	 * 10. OC FLOPPY DISKS
	 * 11. QUARK ITEMS
	 * 
	 * FIX DYE COLOURS YET... AGAIN...
	 */
	
	public static void dyes() {
		
		List<String> itemDyes = Arrays.asList("dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite");
		List<String> waterDyes = Arrays.asList("water_dye_black", "water_dye_red", "water_dye_green", "water_dye_brown", "water_dye_blue", "water_dye_purple", "water_dye_cyan", "water_dye_lightgray", "water_dye_gray", "water_dye_pink", "water_dye_lime", "water_dye_yellow", "water_dye_lightblue", "water_dye_magenta", "water_dye_orange", "water_dye_white");
		List<String> chemicalDyes = Arrays.asList("chemical_dye_black", "chemical_dye_red", "chemical_dye_green", "chemical_dye_brown", "chemical_dye_blue", "chemical_dye_purple", "chemical_dye_cyan", "chemical_dye_lightgray", "chemical_dye_gray", "chemical_dye_pink", "chemical_dye_lime", "chemical_dye_yellow", "chemical_dye_lightblue", "chemical_dye_magenta", "chemical_dye_orange", "chemical_dye_white");
		
		ArrayList<Integer> dyeMetas = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));		
		ArrayList<Integer> woolMetas = new ArrayList<Integer>(Arrays.asList(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
		Iterator<Integer> dyeIterator = dyeMetas.iterator();
		Iterator<Integer> woolIterator = woolMetas.iterator();
				
		while(dyeIterator.hasNext() && woolIterator.hasNext()) {
			int dye = dyeIterator.next();
			int meta = woolIterator.next();
			if(dye != 15 && meta != 0) {
				RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(18).input(itemDyes.get(dye), 4).inputs(new ItemStack(Blocks.WOOL, 1, 0)).outputs(new ItemStack(Blocks.WOOL, 1, meta)).buildAndRegister();
			
				RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(240).EUt(8).fluidInputs(ModHandler.getWater(500)).input(itemDyes.get(dye), 1).fluidOutputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).buildAndRegister();
				RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(30).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500), Materials.SulfuricAcid.getFluid(500)).input(OrePrefix.dust, Materials.Salt).fluidOutputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 1000)).buildAndRegister();
						
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(120).EUt(2).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.WOOL, 1, 0)).outputs(new ItemStack(Blocks.WOOL, 1, meta)).buildAndRegister();
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(120).EUt(2).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.HARDENED_CLAY)).outputs(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, meta)).buildAndRegister();			
				
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(120).EUt(2).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.WOOL, 1, 0)).outputs(new ItemStack(Blocks.WOOL, 1, meta)).buildAndRegister();
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(120).EUt(2).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.HARDENED_CLAY)).outputs(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, meta)).buildAndRegister();			
						
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.CARPET, 1, 0)).outputs(new ItemStack(Blocks.CARPET, 1, meta)).buildAndRegister();
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.CARPET, 1, 0)).outputs(new ItemStack(Blocks.CARPET, 1, meta)).buildAndRegister();		
			}
			
			if(dye != 7) {
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, 7)).outputs(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, dye)).buildAndRegister();
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, 7)).outputs(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, dye)).buildAndRegister();			
			}
			
			RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(80).EUt(12).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 5000)).inputs(MetaItems.SPRAY_EMPTY.getStackForm()).outputs(MetaItems.SPRAY_CAN_DYES[meta].getStackForm()).buildAndRegister();
			
			RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.GLASS, 1, 0)).outputs(new ItemStack(Blocks.STAINED_GLASS, 1, meta)).buildAndRegister();
			RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.GLASS, 1, 0)).outputs(new ItemStack(Blocks.STAINED_GLASS, 1, meta)).buildAndRegister();
		
			RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.GLASS_PANE, 1, 0)).outputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, meta)).buildAndRegister();
			RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.GLASS_PANE, 1, 0)).outputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, meta)).buildAndRegister();		

			ModHandler.removeRecipes(new ItemStack(Blocks.WOOL, 1, dye));
			ModHandler.removeRecipes(new ItemStack(Blocks.STAINED_GLASS, 8, dye));
			ModHandler.removeRecipes(new ItemStack(Blocks.STAINED_GLASS_PANE, 16, dye));
			ModHandler.removeRecipes(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 8, dye));
			ModHandler.removeRecipes(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, dye));
			
		}
		
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(96).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(2), 4000)).inputs(CountableIngredient.from(OrePrefix.dustPure, Materials.NetherQuartz, 12), CountableIngredient.from(OrePrefix.dustPure, Materials.Olivine, 2)).outputs(new ItemStack(Item.getByNameOrId("enderio:item_material"), 1, 51)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(128).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(3), 4000)).inputs(CountableIngredient.from("dustSoularium", 3), CountableIngredient.from(OrePrefix.dustPure, Materials.Tanzanite, 8)).outputs(new ItemStack(Item.getByNameOrId("enderio:item_material"), 1, 52)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(512).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(0), 10000)).inputs(CountableIngredient.from("itemPulsatingPowder", 4), CountableIngredient.from(OrePrefix.dustPure, Materials.Quartzite, 4)).outputs(new ItemStack(Item.getByNameOrId("enderio:item_material"), 1, 67)).buildAndRegister();
	}		
	
	public static void immersiveEngineeringAddon() {
		
		//Removes all Crusher Recipes
		List<CrusherRecipe> crusherRecipes = new ArrayList();
		Iterator<CrusherRecipe> crusherIterator = CrusherRecipe.recipeList.iterator();
		while(crusherIterator.hasNext()) {
			CrusherRecipe crusher = crusherIterator.next();
			crusherRecipes.add(crusher);
			crusherIterator.remove();
		}
		
		List<MetalPressRecipe> metalPressRecipes = new ArrayList();
		Set<ComparableItemStack> keySet = new HashSet<ComparableItemStack>(MetalPressRecipe.recipeList.keySet());
		for(ComparableItemStack mold : keySet) {
			Iterator<MetalPressRecipe> metalPressIterator = MetalPressRecipe.recipeList.get(mold).iterator();
			while(metalPressIterator.hasNext()) {
				MetalPressRecipe metalPress = metalPressIterator.next();
				metalPressRecipes.add(metalPress);
				metalPressIterator.remove();
			}
		}
		
		List<AlloyRecipe> alloyRecipes = new ArrayList();
		Iterator<AlloyRecipe> alloyIterator = AlloyRecipe.recipeList.iterator();
		while(alloyIterator.hasNext()) {
			AlloyRecipe crusher = alloyIterator.next();
			alloyRecipes.add(crusher);
			alloyIterator.remove();
		}
		
		List<CokeOvenRecipe> cokeRecipes = new ArrayList();
		Iterator<CokeOvenRecipe> cokeIterator = CokeOvenRecipe.recipeList.iterator();
		while(cokeIterator.hasNext()) {
			CokeOvenRecipe crusher = cokeIterator.next();
			cokeRecipes.add(crusher);
			cokeIterator.remove();
		}
		
		List<MixerRecipe> mixerRecipe = new ArrayList();
		Iterator<MixerRecipe> mixerIterator = MixerRecipe.recipeList.iterator();
		while(mixerIterator.hasNext()) {
			MixerRecipe crusher = mixerIterator.next();
			mixerRecipe.add(crusher);
			mixerIterator.remove();
		}
		
		AlloyRecipe.addRecipe(new ItemStack(Blocks.HARDENED_CLAY), new ItemStack(Blocks.CLAY), ItemStack.EMPTY, 200);
		AlloyRecipe.addRecipe(new ItemStack(Item.getByNameOrId("betterwithmods:aesthetic"), 1, 7), new ItemStack(Blocks.END_STONE), ItemStack.EMPTY, 100);
		AlloyRecipe.addRecipe(new ItemStack(Item.getByNameOrId("betterwithmods:aesthetic"), 2, 7), new ItemStack(Blocks.END_STONE), new ItemStack(Blocks.END_STONE), 140);
		
		//Re-implement useful recipes
		IERecipes.addCrusherRecipe(new ItemStack(Blocks.GRAVEL), "cobblestone", 1600);
		IERecipes.addCrusherRecipe(new ItemStack(Blocks.SAND), Blocks.GRAVEL, 1600);
		IERecipes.addCrusherRecipe(new ItemStack(Blocks.SAND), "itemSlag", 1600);
		IERecipes.addCrusherRecipe(new ItemStack(Blocks.SAND), "blockGlass", 3200);
		IERecipes.addCrusherRecipe(new ItemStack(Blocks.SAND, 2), "sandstone", 1600, new ItemStack(IEContent.itemMaterial, 1, 24), .5f);
		IERecipes.addCrusherRecipe(new ItemStack(Items.QUARTZ, 4), "blockQuartz", 3200);
		IERecipes.addCrusherRecipe(new ItemStack(Items.GLOWSTONE_DUST, 4), "glowstone", 3200);
		IERecipes.addCrusherRecipe(new ItemStack(Items.BLAZE_POWDER, 4), "rodBlaze", 3200, new ItemStack(IEContent.itemMaterial, 1, 25), .5f);
		IERecipes.addCrusherRecipe(new ItemStack(Items.DYE, 6, 15), Items.BONE, 3200);
		IERecipes.addCrusherRecipe(new ItemStack(IEContent.itemMaterial, 1, 17), "fuelCoke", 2400);
		IERecipes.addCrusherRecipe(new ItemStack(IEContent.itemMaterial, 9, 17), "blockFuelCoke", 4800);
		
		for(Material m : Material.MATERIAL_REGISTRY) {
			boolean gemFormsIgnored = OrePrefix.gemChipped.isIgnored(m);
			if(m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
				DustMaterial material = (DustMaterial)m;
				if(!(m instanceof GemMaterial) || gemFormsIgnored) {
					DustMaterial byproductMaterial = GTUtility.selectItemInList(0, material, material.oreByProducts, DustMaterial.class);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.ore, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Stone), 0.75F);			
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreSandstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreRedSandstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreGravel, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Flint), 0.75F);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreSand, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);		
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreNetherrack, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Netherrack), 0.75F);		
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreEndstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Endstone), 0.75F);					
				}
				else {
					DustMaterial byproductMaterial = GTUtility.selectItemInList(0, material, material.oreByProducts, DustMaterial.class);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, m), OreDictUnifier.get(OrePrefix.ore, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Stone), 0.75F);			
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, m), OreDictUnifier.get(OrePrefix.oreSandstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, m), OreDictUnifier.get(OrePrefix.oreRedSandstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, m), OreDictUnifier.get(OrePrefix.oreGravel, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Flint), 0.75F);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, m), OreDictUnifier.get(OrePrefix.oreSand, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);		
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, m), OreDictUnifier.get(OrePrefix.oreNetherrack, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Netherrack), 0.75F);		
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, m), OreDictUnifier.get(OrePrefix.oreEndstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Endstone), 0.75F);					
				}
			}
		}
		
	}

	public static void fuckGA() {
		
        for (Material material : Material.MATERIAL_REGISTRY) {
            if (!OreDictUnifier.get(OrePrefix.gem, material).isEmpty() && !OreDictUnifier.get(OrePrefix.toolHeadHammer, material).isEmpty() && material != Materials.Flint) {               
            	SolidMaterial toolMaterial = (SolidMaterial) material;
                ModHandler.addMirroredShapedRecipe(String.format("hammer_%s", material.toString()),
                        (MetaItems.HARD_HAMMER).getStackForm(toolMaterial, Materials.Wood),
                        "GG ", "GGS", "GG ",
                        'G', new UnificationEntry(OrePrefix.gem, toolMaterial), 'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));

            	ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadAxe, material));
                ModHandler.addShapedRecipe("axe_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadAxe, material), "GG", "Gf", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadFile, material));
                ModHandler.addShapedRecipe("file_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadFile, material), "G", "G", "f", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadHammer, material));
                ModHandler.addShapedRecipe("hammer_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadHammer, material), "GG ", "GGf", "GG ", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadHoe, material));
                ModHandler.addShapedRecipe("hoe_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadHoe, material), "GGf", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, material));
                ModHandler.addShapedRecipe("pickaxe_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadPickaxe, material), "GGG", "f  ", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadPlow, material));
                ModHandler.addShapedRecipe("flow_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadPlow, material), "GG", "GG", " f", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadSaw, material));
                ModHandler.addShapedRecipe("saw_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadSaw, material), "GG", "f ", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadSense, material));
                ModHandler.addShapedRecipe("sense_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadSense, material), "GGG", " f ", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadShovel, material));
                ModHandler.addShapedRecipe("shovel_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadShovel, material), "fG", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadSword, material));
                ModHandler.addShapedRecipe("sword_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadSword, material), " G", "fG", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, material));
                ModHandler.addShapedRecipe("universal_spade_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, material), "GGG", "GfG", " G ", 'G', new UnificationEntry(OrePrefix.gem, material));
            }
        }

        //Misc Recipe Patches
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).input(OrePrefix.dust, Materials.NetherQuartz).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.NetherQuartz)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).input(OrePrefix.dust, Materials.CertusQuartz).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.CertusQuartz)).buildAndRegister();

        //Dust Uncrafting Fixes
        for (Material m : DustMaterial.MATERIAL_REGISTRY) {
            if (!OreDictUnifier.get(OrePrefix.dustSmall, m).isEmpty()) {
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.dustSmall, m));
                ModHandler.addShapedRecipe("dust_small_" + m.toString(), OreDictUnifier.get(OrePrefix.dustSmall, m, 4), " D", "  ", 'D', new UnificationEntry(OrePrefix.dust, m));
            }
        }
		
		RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(180).EUt(8).input(OrePrefix.dust, Materials.Trona).fluidInputs(Materials.CarbonDioxide.getFluid(500), ModHandler.getWater(1000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.SodiumBicarbonate)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(80).EUt(16).input(OrePrefix.dust, Materials.Quicklime).fluidInputs(Materials.AceticAcid.getFluid(2000)).fluidOutputs(Materials.CalciumAcetate.getFluid(2000)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(80).EUt(16).input(OrePrefix.dust, Materials.Calcium).fluidInputs(Materials.AceticAcid.getFluid(2000)).fluidOutputs(Materials.CalciumAcetate.getFluid(2000)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(240).EUt(16).input(OrePrefix.dust, Materials.Calcite).fluidInputs(Materials.AceticAcid.getFluid(2000)).fluidOutputs(Materials.CalciumAcetate.getFluid(2000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(2).fluidInputs(Materials.Hydrogen.getFluid(1000), Materials.CarbonDioxide.getFluid(1000)).fluidOutputs(FluidRegistry.getFluidStack("carbon_monoxide", 1000), ModHandler.getWater(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Air.getFluid(1000), Materials.Ethylene.getFluid(144)).fluidOutputs(Materials.Plastic.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Oxygen.getFluid(1000), Materials.Ethylene.getFluid(144)).fluidOutputs(Materials.Plastic.getFluid(216)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Air.getFluid(7500), Materials.Ethylene.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.Plastic.getFluid(3240)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Oxygen.getFluid(7500), Materials.Ethylene.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.Plastic.getFluid(4320)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(18).inputs(OreDictUnifier.get(OrePrefix.dust, Materials.SodiumCyanate, 2)).input("dustThermite", 2).fluidOutputs(Materials.SodiumCyanide.getFluid(250)).buildAndRegister(); 
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Oxygen.getFluid(10000), Materials.Ammonia.getFluid(4000)).fluidOutputs(Materials.NitricOxide.getFluid(4000), Materials.Water.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(120).fluidInputs(Materials.Ammonia.getFluid(1000), Materials.Methanol.getFluid(2000)).fluidOutputs(Materials.Water.getFluid(2000), Materials.Dimethylamine.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.HypochlorousAcid.getFluid(1000), Materials.Ammonia.getFluid(1000)).fluidOutputs(Materials.Water.getFluid(1000), Materials.Chloramine.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Materials.Oxygen.getFluid(4000), Materials.Ammonia.getFluid(1000)).fluidOutputs(Materials.NitricAcid.getFluid(1000), Materials.Water.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(30).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Materials.Oxygen.getFluid(7000), Materials.Ammonia.getFluid(2000)).fluidOutputs(Materials.NitrogenTetroxide.getFluid(1000), Materials.Water.getFluid(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(30).inputs(OreDictUnifier.get(OrePrefix.dust, Materials.SodiumHydroxide)).fluidInputs(Materials.Epichlorhydrin.getFluid(1000), Materials.BisphenolA.getFluid(1000)).fluidOutputs(Materials.Epoxid.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(8).input(OrePrefix.dust, Materials.Sodium).fluidInputs(Materials.Water.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.SodiumHydroxide)).fluidOutputs(Materials.Hydrogen.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(30).input(OrePrefix.dust, Materials.SodiumHydroxide).fluidInputs(Materials.AllylChloride.getFluid(1000), Materials.HypochlorousAcid.getFluid(1000)).fluidOutputs(Materials.Epichlorhydrin.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Propene.getFluid(1000), Materials.Chlorine.getFluid(2000)).fluidOutputs(Materials.HydrochloricAcid.getFluid(1000), Materials.AllylChloride.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(110).fluidInputs(Materials.Ammonia.getFluid(1000), Materials.CarbonDioxide.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Urea, 3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(300).EUt(30).fluidInputs(Materials.SodiumCyanide.getFluid(50), Materials.Acetone.getFluid(50)).fluidOutputs(Materials.AcetoneCyanohydrin.getFluid(100)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(20).EUt(24).fluidInputs(Materials.AcetoneCyanohydrin.getFluid(100), Materials.Methanol.getFluid(150)).fluidOutputs(Materials.MethylMethacrylate.getFluid(250)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(120).fluidInputs(Materials.MethylMethacrylate.getFluid(250), Materials.Fluorine.getFluid(1000)).fluidOutputs(Materials.Acrylic.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(110).inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Urea, 6), OreDictUnifier.get(OrePrefix.dust, Materials.SodiumHydroxide, 3)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.SodiumCyanate, 2)).buildAndRegister();
        
        RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(16).EUt(30).circuitMeta(1).fluidInputs(Materials.Acetone.getFluid(100)).fluidOutputs(Materials.Ethenone.getFluid(100)).buildAndRegister();
        RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(16).EUt(30).circuitMeta(1).fluidInputs(Materials.CalciumAcetate.getFluid(200)).fluidOutputs(Materials.Acetone.getFluid(200)).buildAndRegister();
        
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(300).EUt(5).inputs(MetaItems.RUBBER_DROP.getStackForm()).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 3)).chancedOutput(MetaItems.PLANT_BALL.getStackForm(), 1000).fluidOutputs(Materials.Glue.getFluid(100)).buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.input(OrePrefix.log, Materials.Wood, 16)
        	.circuitMeta(0)
        	.outputs(new ItemStack(Items.COAL, 20, 1))
        	.fluidOutputs(Materials.Creosote.getFluid(4000))
        	.duration(440)
        	.EUt(64)
        	.buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.input(OrePrefix.log, Materials.Wood, 16)
        	.circuitMeta(1)
        	.fluidInputs(Materials.Nitrogen.getFluid(400))
        	.outputs(new ItemStack(Items.COAL, 20, 1))
        	.fluidOutputs(Materials.Creosote.getFluid(4000))
        	.duration(200)
        	.EUt(96)
        	.buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.input(OrePrefix.log, Materials.Wood, 16)
        	.circuitMeta(2)
        	.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 4))
        	.fluidOutputs(Materials.OilHeavy.getFluid(200))
        	.duration(280)
        	.EUt(192)
        	.buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.input(OrePrefix.log, Materials.Wood, 16)
        	.circuitMeta(3)
        	.outputs(new ItemStack(Items.COAL, 20, 1))
        	.fluidOutputs(Materials.WoodVinegar.getFluid(3000))
        	.duration(640)
        	.EUt(64)
        	.buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.input(OrePrefix.log, Materials.Wood, 16)
        	.circuitMeta(4)
        	.fluidInputs(Materials.Nitrogen.getFluid(400))
        	.outputs(new ItemStack(Items.COAL, 20, 1))
        	.fluidOutputs(Materials.WoodVinegar.getFluid(3000))
        	.duration(320)
        	.EUt(96)
        	.buildAndRegister();
        	

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.input(OrePrefix.log, Materials.Wood, 16)
        	.circuitMeta(7)
        	.outputs(new ItemStack(Items.COAL, 20, 1))
        	.fluidOutputs(Materials.WoodTar.getFluid(1500))
        	.duration(640)
        	.EUt(64)
        	.buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.input(OrePrefix.log, Materials.Wood, 16)
        	.circuitMeta(8)
        	.fluidInputs(Materials.Nitrogen.getFluid(400))
        	.outputs(new ItemStack(Items.COAL, 20, 1))
        	.fluidOutputs(Materials.WoodTar.getFluid(1500))
        	.duration(320)
        	.EUt(96)
        	.buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.inputs(new ItemStack(Items.SUGAR, 23))
        	.circuitMeta(1)
        	.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Charcoal, 12))
        	.fluidOutputs(Materials.Water.getFluid(1500))
        	.duration(640)
        	.EUt(64)
        	.buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.inputs(new ItemStack(Items.SUGAR, 23))
        	.circuitMeta(2)
        	.fluidInputs(Materials.Nitrogen.getFluid(400))
        	.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Charcoal, 12))
        	.fluidOutputs(Materials.Water.getFluid(1500))
        	.duration(320)
        	.EUt(96)
        	.buildAndRegister();
        
        MaterialStack[] ironOres = {
                new MaterialStack(Materials.Pyrite, 1),
                new MaterialStack(Materials.Magnetite, 1),
                new MaterialStack(Materials.Iron, 1),
                new MaterialStack(Materials.Siderite, 1),
                new MaterialStack(Materials.Hematite, 1),
                new MaterialStack(Materials.Geothite, 1),
                new MaterialStack(Materials.Taconite, 1),
        };
        
        for(MaterialStack ore : ironOres) {
            Material targetMaterial = ore.material;
            if(targetMaterial == Materials.BandedIron) {
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreRedSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreRedSandstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();       
            }
            if((targetMaterial == Materials.Magnetite) || (targetMaterial == Materials.Taconite)) {
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreRedSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreRedSandstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();       
            }
            if(targetMaterial == Materials.Geothite) {
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreRedSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreRedSandstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();       
            }
            else {
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreRedSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreRedSandstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();       
            }
        }
        
        //Electromagnetic Separator Recipes
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Nickel).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Nickel)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Pentlandite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Pentlandite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.BandedIron).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BandedIron)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Ilmenite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ilmenite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Pyrite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Pyrite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Tin).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Tin)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Chromite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Chromite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Bastnasite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Bastnasite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Neodymium), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.VanadiumMagnetite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.VanadiumMagnetite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Gold), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold), 4000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Magnetite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Magnetite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Gold), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold), 4000).buildAndRegister();
      
        //Add Missing Superconducter Wire Tiering Recipes
        ModHandler.addShapelessRecipe("superconducter_wire_gtsingle_doubling", OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtdouble_doubling", OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtquadruple_doubling", OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtoctal_doubling", OreDictUnifier.get(OrePrefix.wireGtHex, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtquadruple_splitting", OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor, 2), OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtoctal_splitting", OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor, 2), OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gthex_splitting", OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor, 2), OreDictUnifier.get(OrePrefix.wireGtHex, Tier.Superconductor));

        List<ItemStack> allWoodLeaves = OreDictionary.getOres("treeLeaves").stream()
                .flatMap(stack -> ModHandler.getAllSubItems(stack).stream())
                .collect(Collectors.toList());

        List<ItemStack> allSaplings = OreDictionary.getOres("treeSapling").stream()
                .flatMap(stack -> ModHandler.getAllSubItems(stack).stream())
                .collect(Collectors.toList());
        
        for (ItemStack stack : allSaplings) {
            RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(800).EUt(3).inputs(GTUtility.copyAmount(1, stack)).fluidInputs(Materials.Water.getFluid(100)).fluidOutputs(Materials.Biomass.getFluid(100)).buildAndRegister();
        	RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(GTUtility.copyAmount(8, stack)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
            RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(1200).EUt(3).inputs(GTUtility.copyAmount(1, stack)).fluidInputs(FluidRegistry.getFluidStack("for.honey", 100)).fluidOutputs(Materials.Biomass.getFluid(150)).buildAndRegister();            
            RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(1200).EUt(3).inputs(GTUtility.copyAmount(1, stack)).fluidInputs(FluidRegistry.getFluidStack("juice", 100)).fluidOutputs(Materials.Biomass.getFluid(150)).buildAndRegister();
        }

        //Biomass Process
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(1440).EUt(3).inputs(MetaItems.PLANT_BALL.getStackForm()).fluidInputs(Materials.Water.getFluid(180)).fluidOutputs(Materials.Biomass.getFluid(180)).buildAndRegister();        
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.POTATO)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.CARROT)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Blocks.CACTUS)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.REEDS)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Blocks.BROWN_MUSHROOM)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Blocks.RED_MUSHROOM)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.BEETROOT)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.CHORUS_FRUIT)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();

        //Plantballs
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.WHEAT, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.POTATO, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.CARROT, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Blocks.CACTUS, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.REEDS, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Blocks.BROWN_MUSHROOM, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Blocks.RED_MUSHROOM, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.BEETROOT, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.CHORUS_FRUIT, 8)).outputs(MetaItems.PLANT_BALL.getStackForm()).buildAndRegister();

        //Food To Methane
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(72).EUt(5).inputs(new ItemStack(Items.BREAD)).fluidOutputs(Materials.Methane.getFluid(9)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(72).EUt(5).inputs(new ItemStack(Items.COOKIE)).fluidOutputs(Materials.Methane.getFluid(9)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(72).EUt(5).inputs(new ItemStack(Items.MELON)).fluidOutputs(Materials.Methane.getFluid(9)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(72).EUt(5).inputs(new ItemStack(Items.CHORUS_FRUIT)).fluidOutputs(Materials.Methane.getFluid(9)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(new ItemStack(Items.APPLE)).fluidOutputs(Materials.Methane.getFluid(18)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(new ItemStack(Items.NETHER_WART)).fluidOutputs(Materials.Methane.getFluid(18)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(new ItemStack(Blocks.BROWN_MUSHROOM)).fluidOutputs(Materials.Methane.getFluid(18)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(new ItemStack(Blocks.RED_MUSHROOM)).fluidOutputs(Materials.Methane.getFluid(18)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(new ItemStack(Items.SPIDER_EYE)).fluidOutputs(Materials.Methane.getFluid(18)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(192).EUt(5).inputs(new ItemStack(Items.BAKED_POTATO)).fluidOutputs(Materials.Methane.getFluid(24)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Blocks.PUMPKIN)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.CARROT)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.COOKED_BEEF)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.MUSHROOM_STEW)).outputs(new ItemStack(Items.BOWL)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.COOKED_FISH)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.COOKED_FISH, 1, 1)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.COOKED_CHICKEN)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.POTATO)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.ROTTEN_FLESH)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.COOKED_PORKCHOP)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.COOKED_RABBIT)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(288).EUt(5).inputs(new ItemStack(Items.COOKED_MUTTON)).fluidOutputs(Materials.Methane.getFluid(36)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.PORKCHOP)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.FISH)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.FISH, 1, 1)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.FISH, 1, 2)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.FISH, 1, 3)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.POISONOUS_POTATO)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.CHICKEN)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.RABBIT)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.MUTTON)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(384).EUt(5).inputs(new ItemStack(Items.BEEF)).fluidOutputs(Materials.Methane.getFluid(48)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(576).EUt(5).inputs(new ItemStack(Items.CAKE)).fluidOutputs(Materials.Methane.getFluid(72)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(576).EUt(5).inputs(new ItemStack(Items.RABBIT_STEW)).outputs(new ItemStack(Items.BOWL)).fluidOutputs(Materials.Methane.getFluid(126)).buildAndRegister();

        //Redstone and glowstone melting
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(80).EUt(32).input(OrePrefix.dust, Materials.Redstone).fluidOutputs(Materials.Redstone.getFluid(144)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(80).EUt(32).input(OrePrefix.dust, Materials.Glowstone).fluidOutputs(Materials.Glowstone.getFluid(144)).buildAndRegister();

        //Forestry Shit
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(FluidRegistry.getFluidStack("seed.oil", 24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(40).EUt(256).fluidInputs(Materials.WoodVinegar.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(100), Materials.Water.getFluid(500), FluidRegistry.getFluidStack("bio.ethanol", 10), Materials.Methanol.getFluid(300), Materials.Acetone.getFluid(50), Materials.MethylAcetate.getFluid(10)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(75).EUt(180).fluidInputs(Materials.FermentedBiomass.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(25), FluidRegistry.getFluidStack("bio.ethanol", 150), Materials.Methanol.getFluid(150), Materials.Ammonia.getFluid(100), Materials.CarbonDioxide.getFluid(400), Materials.Methane.getFluid(600)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(400).fluidInputs(Materials.Biomass.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2)).fluidOutputs(FluidRegistry.getFluidStack("bio.ethanol", 600), Materials.Water.getFluid(300)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(Materials.SeedOil.getFluid(24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
     	RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(40).EUt(256).fluidInputs(Materials.WoodVinegar.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(100), Materials.Water.getFluid(500), Materials.Ethanol.getFluid(10), Materials.Methanol.getFluid(300), Materials.Acetone.getFluid(50), Materials.MethylAcetate.getFluid(10)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(75).EUt(180).fluidInputs(Materials.FermentedBiomass.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(25), Materials.Ethanol.getFluid(150), Materials.Methanol.getFluid(150), Materials.Ammonia.getFluid(100), Materials.CarbonDioxide.getFluid(400), Materials.Methane.getFluid(600)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(400).fluidInputs(Materials.Biomass.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2)).fluidOutputs(Materials.Ethanol.getFluid(600), Materials.Water.getFluid(300)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(80).EUt(480).fluidInputs(Materials.CalciumAcetate.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Quicklime, 3)).fluidOutputs(Materials.Acetone.getFluid(1000), Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();       
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(80).EUt(640).fluidInputs(Materials.Acetone.getFluid(1000)).fluidOutputs(Materials.Ethenone.getFluid(1000), Materials.Methane.getFluid(1000)).buildAndRegister();
      
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(380).input(OrePrefix.dust, Materials.Calcite).fluidInputs(Materials.AceticAcid.getFluid(4000)).fluidOutputs(Materials.Acetone.getFluid(4000), Materials.CarbonDioxide.getFluid(4000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(380).input(OrePrefix.dust, Materials.Quicklime).fluidInputs(Materials.AceticAcid.getFluid(4000)).fluidOutputs(Materials.Acetone.getFluid(4000), Materials.CarbonDioxide.getFluid(4000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(380).input(OrePrefix.dust, Materials.Calcium).fluidInputs(Materials.AceticAcid.getFluid(4000)).fluidOutputs(Materials.Acetone.getFluid(4000), Materials.CarbonDioxide.getFluid(4000)).buildAndRegister();

        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(5).inputs(new ItemStack(Items.WHEAT_SEEDS)).fluidOutputs(FluidRegistry.getFluidStack("seed.oil", 10)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(5).inputs(new ItemStack(Items.MELON_SEEDS)).fluidOutputs(FluidRegistry.getFluidStack("seed.oil", 10)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(5).inputs(new ItemStack(Items.PUMPKIN_SEEDS)).fluidOutputs(FluidRegistry.getFluidStack("seed.oil", 10)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2).inputs(new ItemStack(Items.WHEAT_SEEDS)).fluidOutputs(Materials.SeedOil.getFluid(10)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2).inputs(new ItemStack(Items.MELON_SEEDS)).fluidOutputs(Materials.SeedOil.getFluid(10)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2).inputs(new ItemStack(Items.PUMPKIN_SEEDS)).fluidOutputs(Materials.SeedOil.getFluid(10)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2).inputs(new ItemStack(Items.BEETROOT_SEEDS)).fluidOutputs(Materials.SeedOil.getFluid(10)).buildAndRegister();

        //Making BioDiesel
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs((FluidRegistry.getFluidStack("seed.oil", 6000)), Materials.Methanol.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs((FluidRegistry.getFluidStack("seed.oil", 6000)), FluidRegistry.getFluidStack("bio.ethanol", 1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs(Materials.SeedOil.getFluid(6000), Materials.Methanol.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs(Materials.SeedOil.getFluid(6000), Materials.Ethanol.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
        
		MaterialStack[] lubeDusts = {
	            new MaterialStack(Materials.Talc, 1),
	            new MaterialStack(Materials.Soapstone, 1),
	            new MaterialStack(Materials.Redstone, 1)
	    };
		
        for (MaterialStack lubeDust : lubeDusts) {
        	DustMaterial dust = (DustMaterial) lubeDust.material;
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.dust, dust).fluidInputs(Materials.Oil.getFluid(750)).fluidOutputs(Materials.Lubricant.getFluid(750)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.dust, dust).fluidInputs(Materials.Creosote.getFluid(750)).fluidOutputs(Materials.Lubricant.getFluid(750)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.dust, dust).fluidInputs(FluidRegistry.getFluidStack("seed.oil", 750)).fluidOutputs(Materials.Lubricant.getFluid(750)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.dust, dust).fluidInputs(Materials.SeedOil.getFluid(750)).fluidOutputs(Materials.Lubricant.getFluid(750)).buildAndRegister();
        }

        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(2880).EUt(3).inputs(MetaItems.PLANT_BALL.getStackForm()).fluidInputs(FluidRegistry.getFluidStack("juice", 180)).fluidOutputs(Materials.Biomass.getFluid(360)).buildAndRegister();                   
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(320).EUt(3).inputs(new ItemStack(Items.POTATO)).fluidInputs(FluidRegistry.getFluidStack("juice", 20)).fluidOutputs(Materials.Biomass.getFluid(40)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(320).EUt(3).inputs(new ItemStack(Items.CARROT)).fluidInputs(FluidRegistry.getFluidStack("juice", 20)).fluidOutputs(Materials.Biomass.getFluid(40)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(320).EUt(3).inputs(new ItemStack(Blocks.CACTUS)).fluidInputs(FluidRegistry.getFluidStack("juice", 20)).fluidOutputs(Materials.Biomass.getFluid(40)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(320).EUt(3).inputs(new ItemStack(Blocks.BROWN_MUSHROOM)).fluidInputs(FluidRegistry.getFluidStack("juice", 20)).fluidOutputs(Materials.Biomass.getFluid(40)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(320).EUt(3).inputs(new ItemStack(Blocks.RED_MUSHROOM)).fluidInputs(FluidRegistry.getFluidStack("juice", 20)).fluidOutputs(Materials.Biomass.getFluid(40)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(320).EUt(3).inputs(new ItemStack(Items.BEETROOT)).fluidInputs(FluidRegistry.getFluidStack("juice", 20)).fluidOutputs(Materials.Biomass.getFluid(40)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(320).EUt(3).inputs(new ItemStack(Items.CHORUS_FRUIT)).fluidInputs(FluidRegistry.getFluidStack("juice", 20)).fluidOutputs(Materials.Biomass.getFluid(40)).buildAndRegister();

        //Making Ethylene
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(120).fluidInputs(Materials.SulfuricAcid.getFluid(1000), FluidRegistry.getFluidStack("bio.ethanol", 1000)).fluidOutputs(Materials.Ethylene.getFluid(1000), Materials.DilutedSulfuricAcid.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(120).fluidInputs(Materials.SulfuricAcid.getFluid(1000), Materials.Ethanol.getFluid(1000)).fluidOutputs(Materials.Ethylene.getFluid(1000), Materials.DilutedSulfuricAcid.getFluid(1000)).buildAndRegister();
        
        //Replicators and Mass Fabs
        for (Material m : FluidMaterial.MATERIAL_REGISTRY) {
            if (m.getProtons() > 0 && m.getNeutrons() > 0 && m.getMass() != 98 && m instanceof FluidMaterial && OreDictUnifier.get(OrePrefix.dust, m).isEmpty()) {
            	MTERecipes.MASS_FABRICATOR_RECIPES.recipeBuilder().duration((int)(m.getMass() * 100)).EUt(32).fluidInputs(((FluidMaterial) m).getFluid(1000)).fluidOutputs(Materials.PositiveMatter.getFluid((int) m.getProtons()), Materials.NeutralMatter.getFluid((int) m.getNeutrons())).buildAndRegister();
            }
        }
        for (Material m : DustMaterial.MATERIAL_REGISTRY) {
            if (m.getProtons() >= 1 && m.getNeutrons() >= 0 && m.getMass() != 98 && m instanceof DustMaterial && m != Materials.Sphalerite && m != Materials.Ash && m != Materials.DarkAsh) {
            	MTERecipes.MASS_FABRICATOR_RECIPES.recipeBuilder().duration((int)(m.getMass() * 100)).EUt(32).inputs((OreDictUnifier.get(OrePrefix.dust, m))).fluidOutputs(Materials.PositiveMatter.getFluid((int) m.getProtons()), Materials.NeutralMatter.getFluid((int) m.getNeutrons())).buildAndRegister();
            }
        }
       for (Material m : FluidMaterial.MATERIAL_REGISTRY) {
            if (m.getProtons() > 0 && m.getNeutrons() > 0 && m.getMass() != 98 && m instanceof FluidMaterial && OreDictUnifier.get(OrePrefix.dust, m).isEmpty() && m != Materials.Air && m != Materials.LiquidAir) {
            	MTERecipes.REPLICATOR_RECIPES.recipeBuilder().duration((int)(m.getMass() * 100)).EUt(32).fluidOutputs(((FluidMaterial) m).getFluid(1000)).fluidInputs(Materials.PositiveMatter.getFluid((int) m.getProtons()), Materials.NeutralMatter.getFluid((int) m.getNeutrons()), ((FluidMaterial)m).getFluid(1000)).buildAndRegister();
            }
        }
        for (Material m : DustMaterial.MATERIAL_REGISTRY) {
            if (m.getProtons() >= 1 && m.getNeutrons() >= 0 && m.getMass() != 98 && m instanceof DustMaterial && m != Materials.Sphalerite && m != Materials.Ash && m != Materials.DarkAsh) {
            	MTERecipes.REPLICATOR_RECIPES.recipeBuilder().duration((int)(m.getMass() * 100)).EUt(32).notConsumable(OreDictUnifier.get(OrePrefix.dust, m)).outputs((OreDictUnifier.get(OrePrefix.dust, m))).fluidInputs(Materials.PositiveMatter.getFluid((int) m.getProtons()), Materials.NeutralMatter.getFluid((int) m.getNeutrons())).buildAndRegister();
            }
        }
	}
}
