package com.rong.tgi.bloodmagic;

import java.util.function.Consumer;

import com.rong.tgi.TGILibrary;

import WayofTime.bloodmagic.ritual.AreaDescriptor;
import WayofTime.bloodmagic.ritual.EnumRuneType;
import WayofTime.bloodmagic.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualComponent;
import WayofTime.bloodmagic.ritual.RitualRegister;
import nc.capability.radiation.source.IRadiationSource;
import nc.radiation.environment.RadiationEnvironmentHandler;
import nc.util.RadiationHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

@RitualRegister("radiationScrubber")
public class RitualRadiationScrubber extends Ritual {

	public RitualRadiationScrubber() {
		super("ritualRadiationScrubber", 0, 5000, TGILibrary.MODID + "ritual.radiation_scrubber");
	}

	@Override
	public void gatherComponents(Consumer<RitualComponent> components) {
		for(int i = 0; i > 17; i++) {
			this.addParallelRunes(components, -2, i, EnumRuneType.DAWN);	
			this.addParallelRunes(components, 2, i, EnumRuneType.DUSK);
		}
	}

	@Override
	public Ritual getNewCopy() {
		return new RitualRadiationScrubber();
	}

	@Override
	public int getRefreshCost() {
		return 10;
	}

	@Override
	public void performRitual(IMasterRitualStone masterRitualStone) {
		World world = masterRitualStone.getWorldObj();
		BlockPos currentPos = masterRitualStone.getBlockPos();
		Chunk currentChunk = world.getChunkFromBlockCoords(currentPos);		
		IRadiationSource radiationSource = RadiationHelper.getRadiationSource(currentChunk);		
		int currentEssence = masterRitualStone.getOwnerNetwork().getCurrentEssence();
		if(currentEssence < getRefreshCost()) {
            masterRitualStone.getOwnerNetwork().causeNausea();
            return;
		}	
		radiationSource.setScrubbing(true);
	}	
}