package com.rong.tgi.vanilla.slabfix;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;

public interface ISlabGetter {
	
	IBlockState getSingleSlab(IBlockState state, BlockSlab.EnumBlockHalf blockHalf);

	default boolean isDoubleSlab(IBlockState state) { return true; }

}
