package com.rong.tgi.gt;

import com.google.common.collect.ImmutableList;

import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.type.FluidMaterial;

public class GTMaterials {
	
	public static FluidMaterial RefinedRocketFuel;
	
	public static void init() {
		RefinedRocketFuel = new FluidMaterial(970, "refined_rocket_fuel", 0xFFFF00, MaterialIconSet.FLUID, ImmutableList.of(), 0);
	}

}
