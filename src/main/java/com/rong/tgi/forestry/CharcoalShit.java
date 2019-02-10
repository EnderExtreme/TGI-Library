package com.rong.tgi.forestry;

import forestry.api.arboriculture.ICharcoalManager;
import forestry.api.arboriculture.TreeManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import twilightforest.block.TFBlocks;

public class CharcoalShit {
	
	public static void init() {
		ICharcoalManager manager = TreeManager.charcoalManager;
		if(manager != null) {
			manager.removeWall(Blocks.CLAY);
			manager.removeWall(Blocks.END_STONE);
			manager.removeWall(Blocks.END_BRICKS);
			manager.removeWall(Blocks.DIRT);
			manager.removeWall(Blocks.GRAVEL);
			manager.removeWall(Blocks.NETHERRACK);
			manager.removeWall(Block.getBlockFromName("forestry:ash_brick"));
			manager.removeWall(Block.getBlockFromName("forestry:loam").getStateFromMeta(0));
			
			//manager.registerWall(Blocks.BRICK_BLOCK, -3);
			manager.registerWall(Blocks.NETHERRACK, 1);		
			manager.registerWall(Block.getBlockFromName("forestry:ash_brick"), 2);
			manager.registerWall(Block.getBlockFromName("forestry:loam"), 4);
			manager.registerWall(TFBlocks.block_storage.getStateFromMeta(2), 9);
			manager.registerWall(Blocks.END_BRICKS, 7);
		}
	}
}
