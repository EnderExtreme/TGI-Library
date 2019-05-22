package com.rong.tgi.gt;

import static com.rong.tgi.DimensionIDs.*;

import java.util.Random;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.unification.material.Materials;
import gregtech.common.metatileentities.electric.MetaTileEntityAirCollector;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;

public class MetaTileEntityAtmosphericAccumulator extends MetaTileEntityAirCollector {
	
	Random random = new Random();

	public MetaTileEntityAtmosphericAccumulator(ResourceLocation metaTileEntityId, int tier) {
		super(metaTileEntityId, tier);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, new FluidTank(16000));
	}
	
	@Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            long energyToConsume = GTValues.V[getTier()];
            if(checkOpenSides() && getTimer() % 100 == 0L && energyContainer.getCurrentEnergyStored() >= energyToConsume) {
            	int dimension = getWorld().provider.getDimension();
            	if(dimension == NETHER) {
                    exportFluids.fill(Materials.CarbonMonoxide.getFluid(50 * 1 << getTier()), true);
                    energyContainer.removeEnergy(energyToConsume);
            	} 
            	else if(dimension == END) {
                    exportFluids.fill(Materials.Helium3.getFluid(2 * 1 << getTier()), true);
                    energyContainer.removeEnergy(energyToConsume);
            	} 
            	else if(dimension == MOON) {
            		exportFluids.fill(Materials.Helium3.getFluid(10 * 1 << getTier()), true);
                    energyContainer.removeEnergy(energyToConsume);
            	}
            	else if(dimension == MARS) {
            		exportFluids.fill(Materials.CarbonDioxide.getFluid(100 * 1 << getTier()), true);
                    energyContainer.removeEnergy(energyToConsume);
            	}
            	else if(dimension == TWILIGHT_FOREST) {
            		exportFluids.fill(FluidRegistry.getFluidStack("mana", 10 * 1 << getTier()), true);
                    energyContainer.removeEnergy(energyToConsume);
            	}
            	else if(dimension == BETWEENLANDS) {
            		exportFluids.fill(FluidRegistry.getFluidStack("biocrude", 10 * 1 << getTier()), true);
                    energyContainer.removeEnergy(energyToConsume);
            	}
            	else if(dimension == BENEATH) {
            		exportFluids.fill(FluidRegistry.getFluidStack("dirt", 100 * 1 << getTier()), true);
                    energyContainer.removeEnergy(energyToConsume);
            	}
            	else if(dimension == AETHER && random.nextInt(10) == 0) {
            		exportFluids.fill(Materials.Gold.getFluid(10 * 1 << getTier()), true);
                    energyContainer.removeEnergy(energyToConsume);
            	}
            	else {
            		exportFluids.fill(Materials.Air.getFluid(300), true);
                    energyContainer.removeEnergy(energyToConsume);
            	}
            }
            if(getTimer() % 10 == 0) {
                pushFluidsIntoNearbyHandlers(getFrontFacing());
            }
        }
    }
	
	private boolean checkOpenSides() {
        EnumFacing frontFacing = getFrontFacing();
        for(EnumFacing side : EnumFacing.VALUES) {
            if(side == frontFacing) continue;
            if(getWorld().isAirBlock(getPos().offset(side)))
                return true;
        }
        return false;
	}

}
