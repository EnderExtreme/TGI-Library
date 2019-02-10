package com.rong.tgi.mekanism;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.MetaFluids;
import mekanism.common.MekanismFluids;
import mekanism.common.recipe.RecipeHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class MekanismRecipeHandler {

	public static void initRecipes() {
		for(Material m : DustMaterial.MATERIAL_REGISTRY) {
			if(m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {										  				
				RecipeHandler.addChemicalInjectionChamberRecipe(OreDictUnifier.get(OrePrefix.crushed, m), MekanismFluids.HydrogenChloride, OreDictUnifier.get(OrePrefix.shard, m, 4));
				RecipeHandler.addChemicalInjectionChamberRecipe(OreDictUnifier.get(OrePrefix.crystal, m), MekanismFluids.HydrogenChloride, OreDictUnifier.get(OrePrefix.shard, m));
				
				RecipeHandler.addPurificationChamberRecipe(OreDictUnifier.get(OrePrefix.shard, m), OreDictUnifier.get(OrePrefix.clump, m));
				RecipeHandler.addCrusherRecipe(OreDictUnifier.get(OrePrefix.clump, m), OreDictUnifier.get(OrePrefix.dust, m));
			}
		}
	}
}
