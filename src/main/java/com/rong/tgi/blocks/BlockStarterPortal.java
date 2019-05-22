package com.rong.tgi.blocks;

import com.rong.tgi.TGILibrary;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStarterPortal extends BlockPortal {
	
	public BlockStarterPortal() {
		setHardness(-1.0F);
		setSoundType(SoundType.GLASS);
		setLightLevel(0.75F);
		setUnlocalizedName(TGILibrary.MODID + "starter_portal");
		setRegistryName("starter_portal");
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumFacing.Axis axis = (EnumFacing.Axis)state.getValue(AXIS);
	}

}
