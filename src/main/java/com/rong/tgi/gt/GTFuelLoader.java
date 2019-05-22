package com.rong.tgi.gt;

import gregtech.api.GTValues;
import gregtech.api.recipes.machines.FuelRecipeMap;
import gregtech.loaders.FuelLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GTFuelLoader {
	
	public static void gasGen() {
		
	}
	
	public static void semiFluidGen() {
		FuelLoader.registerSemiFluidGeneratorFuel(FluidRegistry.getFluidStack("pyrotheum", 10), 25, GTValues.LV);
		FuelLoader.registerSemiFluidGeneratorFuel(FluidRegistry.getFluidStack("aerotheum", 10), 35, GTValues.LV);
		FuelLoader.registerSemiFluidGeneratorFuel(FluidRegistry.getFluidStack("petrotheum", 5), 25, GTValues.LV);
	}
	
	private static void removeFuelRecipe(FuelRecipeMap map, FluidStack fluidStack) {
        map.removeRecipe(map.findRecipe(Integer.MAX_VALUE, fluidStack));
    }

}
