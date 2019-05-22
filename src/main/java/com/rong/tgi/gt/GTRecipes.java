package com.rong.tgi.gt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.crafting.AlloyRecipe;
import blusunrize.immersiveengineering.api.crafting.CokeOvenRecipe;
import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.IERecipes;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zmaster587.advancedRocketry.api.AdvancedRocketryAPI;
import zmaster587.advancedRocketry.api.AdvancedRocketryItems;
import zmaster587.advancedRocketry.armor.ItemSpaceArmor;

public class GTRecipes {
	
	public static void advancedRocketryInit() {
		
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
	 * 5. (DONE)THERMAL FOUNDATION ROCKWOOL
	 * 6. ITEMFRAMES
	 * 7. CANDLES
	 * 8. STAINED PLANKS
	 * 9. ENDERIO SHIT
	 * 10. OC FLOPPY DISKS
	 * 11. QUARK ITEMS
	 * 
	 */
	
	public static void dyes() {
		
		/*List<String> itemDyes = Arrays.asList("dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite");
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
			}
			
			if(dye != 7) {
			}			
		}*/
	}		
	
	public static void immersiveEngineeringAddon() {

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
			if(m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
				DustMaterial material = (DustMaterial)m;
				if(m instanceof GemMaterial) {
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gem, m, 3), OreDictUnifier.get(OrePrefix.oreStone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Stone), 0.75F);			
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gem, m, 3), OreDictUnifier.get(OrePrefix.oreSandstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.65F);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gem, m, 3), OreDictUnifier.get(OrePrefix.oreGravel, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Flint), 0.55F);
					//IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gem, m, 3), OreDictUnifier.get(OrePrefix.oreSand, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);		
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gem, m, 4), OreDictUnifier.get(OrePrefix.oreNetherrack, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Netherrack), 0.75F);		
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.gem, m, 4), OreDictUnifier.get(OrePrefix.oreEndstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Endstone), 0.75F);					
				}
				else {
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreStone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Stone), 0.75F);			
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreSandstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreGravel, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Flint), 0.75F);
					//IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreSand, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 0.75F);		
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreNetherrack, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Netherrack), 0.75F);		
					IERecipes.addCrusherRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m, 3), OreDictUnifier.get(OrePrefix.oreEndstone, m), 18000, OreDictUnifier.get(OrePrefix.dust, Materials.Endstone), 0.75F);					
				}
			}
		}
		
	}

	public static void init() {
		
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(480).fluidInputs(FluidRegistry.getFluidStack("rocket_fuel", 4000), Materials.Methanol.getFluid(2000), Materials.Ammonia.getFluid(2000)).input(OrePrefix.dust, Materials.Sodium).input(OrePrefix.dust, Materials.MagnesiumChloride, 2).fluidOutputs(GTMaterials.RefinedRocketFuel.getFluid(4000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Magnesium)).buildAndRegister();
		
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(480).fluidInputs(new FluidStack(Materials.ChemicalDyeGreen.getMaterialFluid(), 4000)).inputs(CountableIngredient.from(OrePrefix.dustPure, Materials.Quartzite, 12), CountableIngredient.from(OrePrefix.dustPure, Materials.Olivine, 2)).outputs(new ItemStack(Item.getByNameOrId("enderio:item_material"), 1, 51)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(480).fluidInputs(new FluidStack(Materials.ChemicalDyeRed.getMaterialFluid(), 4000)).inputs(CountableIngredient.from("dustSoularium", 3), CountableIngredient.from(OrePrefix.dustPure, Materials.Alexandrite, 8)).outputs(new ItemStack(Item.getByNameOrId("enderio:item_material"), 1, 52)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(5112).fluidInputs(new FluidStack(Materials.ChemicalDyeBlack.getMaterialFluid(), 4000)).inputs(CountableIngredient.from("itemPulsatingPowder", 4), CountableIngredient.from(OrePrefix.dustPure, Materials.Onyx, 4)).outputs(new ItemStack(Item.getByNameOrId("enderio:item_material"), 1, 67)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(2).fluidInputs(Materials.Hydrogen.getFluid(1000), Materials.CarbonDioxide.getFluid(1000)).fluidOutputs(FluidRegistry.getFluidStack("carbon_monoxide", 1000), ModHandler.getWater(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Air.getFluid(1000), Materials.Ethylene.getFluid(144)).fluidOutputs(Materials.Polyethylene.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Oxygen.getFluid(1000), Materials.Ethylene.getFluid(144)).fluidOutputs(Materials.Polyethylene.getFluid(216)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Air.getFluid(7500), Materials.Ethylene.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.Polyethylene.getFluid(3240)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Oxygen.getFluid(7500), Materials.Ethylene.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.Polyethylene.getFluid(4320)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(18).inputs(OreDictUnifier.get(OrePrefix.dust, Materials.SodiumCyanate, 2)).input("dustThermite", 2).fluidOutputs(Materials.SodiumCyanide.getFluid(250)).buildAndRegister(); 
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Oxygen.getFluid(10000), Materials.Ammonia.getFluid(4000)).fluidOutputs(Materials.NitricOxide.getFluid(4000), Materials.Water.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(120).fluidInputs(Materials.Ammonia.getFluid(1000), Materials.Methanol.getFluid(2000)).fluidOutputs(Materials.Water.getFluid(2000), Materials.Dimethylamine.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.HypochlorousAcid.getFluid(1000), Materials.Ammonia.getFluid(1000)).fluidOutputs(Materials.Water.getFluid(1000), Materials.Chloramine.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Materials.Oxygen.getFluid(4000), Materials.Ammonia.getFluid(1000)).fluidOutputs(Materials.NitricAcid.getFluid(1000), Materials.Water.getFluid(1000)).buildAndRegister();
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

        //Forestry Shit
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(FluidRegistry.getFluidStack("seed.oil", 24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(40).EUt(256).fluidInputs(Materials.WoodVinegar.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(100), Materials.Water.getFluid(500), FluidRegistry.getFluidStack("bio.ethanol", 10), Materials.Methanol.getFluid(300), Materials.Acetone.getFluid(50), Materials.MethylAcetate.getFluid(10)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(75).EUt(180).fluidInputs(Materials.FermentedBiomass.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(25), FluidRegistry.getFluidStack("bio.ethanol", 150), Materials.Methanol.getFluid(150), Materials.Ammonia.getFluid(100), Materials.CarbonDioxide.getFluid(400), Materials.Methane.getFluid(600)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(400).fluidInputs(Materials.Biomass.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Wood, 8)).fluidOutputs(FluidRegistry.getFluidStack("bio.ethanol", 600), Materials.Water.getFluid(300)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(Materials.SeedOil.getFluid(24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
     	RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(40).EUt(256).fluidInputs(Materials.WoodVinegar.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(100), Materials.Water.getFluid(500), Materials.Ethanol.getFluid(10), Materials.Methanol.getFluid(300), Materials.Acetone.getFluid(50), Materials.MethylAcetate.getFluid(10)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(75).EUt(180).fluidInputs(Materials.FermentedBiomass.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(25), Materials.Ethanol.getFluid(150), Materials.Methanol.getFluid(150), Materials.Ammonia.getFluid(100), Materials.CarbonDioxide.getFluid(400), Materials.Methane.getFluid(600)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(400).fluidInputs(Materials.Biomass.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Wood, 8)).fluidOutputs(Materials.Ethanol.getFluid(600), Materials.Water.getFluid(300)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(80).EUt(480).fluidInputs(Materials.CalciumAcetate.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Quicklime, 12)).fluidOutputs(Materials.Acetone.getFluid(1000), Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();       
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(80).EUt(640).fluidInputs(Materials.Acetone.getFluid(1000)).fluidOutputs(Materials.Ethenone.getFluid(1000), Materials.Methane.getFluid(1000)).buildAndRegister();
      
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(380).input(OrePrefix.dust, Materials.Calcite).fluidInputs(Materials.AceticAcid.getFluid(4000)).fluidOutputs(Materials.Acetone.getFluid(4000), Materials.CarbonDioxide.getFluid(4000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(380).input(OrePrefix.dust, Materials.Quicklime).fluidInputs(Materials.AceticAcid.getFluid(4000)).fluidOutputs(Materials.Acetone.getFluid(4000), Materials.CarbonDioxide.getFluid(4000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(380).input(OrePrefix.dust, Materials.Calcium).fluidInputs(Materials.AceticAcid.getFluid(4000)).fluidOutputs(Materials.Acetone.getFluid(4000), Materials.CarbonDioxide.getFluid(4000)).buildAndRegister();

        //Making Ethylene
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(120).fluidInputs(Materials.SulfuricAcid.getFluid(1000), FluidRegistry.getFluidStack("bio.ethanol", 1000)).fluidOutputs(Materials.Ethylene.getFluid(1000), Materials.DilutedSulfuricAcid.getFluid(1000)).buildAndRegister();
	}	
}