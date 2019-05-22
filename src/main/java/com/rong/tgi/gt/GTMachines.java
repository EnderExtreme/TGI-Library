package com.rong.tgi.gt;

import com.rong.tgi.TGILibrary;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import net.minecraft.util.ResourceLocation;

public class GTMachines {
	
	public static MetaTileEntityAtmosphericAccumulator[] ATMOSPHERIC_ACCUMULATOR = new MetaTileEntityAtmosphericAccumulator[7];
	
	public static void init() {
		for(int i = 0; i < 7; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            ATMOSPHERIC_ACCUMULATOR[i] = new MetaTileEntityAtmosphericAccumulator(id("atmospheric_accumulator" + voltageName), i);
            GregTechAPI.registerMetaTileEntity(900 + (i), ATMOSPHERIC_ACCUMULATOR[i]);
		}
	}
	
	private static ResourceLocation id(String name) {
        return new ResourceLocation(TGILibrary.MODID, name);
	}
}
