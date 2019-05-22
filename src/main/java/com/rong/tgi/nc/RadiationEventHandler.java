package com.rong.tgi.nc;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import hellfirepvp.astralsorcery.common.tile.network.TileCollectorCrystal;
import nc.capability.radiation.source.IRadiationSource;
import nc.radiation.RadSources;
import nc.radiation.RadiationHandler;
import nc.util.RadiationHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class RadiationEventHandler {
	
	public static final double ASTRAL = 0.0000000932D;	
	public static final double CLUSTER = 0.000000000842D;
	
	@SubscribeEvent
	public static void addRadiation(TickEvent.WorldTickEvent event) {
		World world = event.world;
		for(TileEntity tile : world.loadedTileEntityList) {
			if(tile.getClass() == TileCollectorCrystal.class) {
				BlockPos pos = tile.getPos();
				Chunk chunk = world.getChunkFromBlockCoords(pos);
				IRadiationSource source = RadiationHelper.getRadiationSource(chunk);
				RadiationHelper.addToSourceRadiation(source, ASTRAL);
			}
		}
	}
	
	public static void addRadSources() {
		for(Material m : Material.MATERIAL_REGISTRY) {
			if(m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
				if(m.isRadioactive()) {
					RadSources.addToStackMap(OreDictUnifier.get(OrePrefix.cluster, m), CLUSTER * 2);
				}
				else {
					RadSources.addToStackMap(OreDictUnifier.get(OrePrefix.cluster, m), CLUSTER);
				}
			}
		}
	}

}
