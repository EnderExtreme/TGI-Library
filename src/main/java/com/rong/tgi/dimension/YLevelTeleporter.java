package com.rong.tgi.dimension;

import java.util.Random;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;

public class YLevelTeleporter extends Teleporter {
	
	//TO-DO: ALLOW PLAYER TO SPAWN SAFELY (NOT IN BOX, BUT AIR) + SPAWN PORTAL NEAR THE PLAYER FOR RETURNING PURPOSE
	
	protected final Random random;

	public YLevelTeleporter(WorldServer worldIn) {
		super(worldIn);
		this.random = new Random(worldIn.getSeed());
	}
	

	/*@Override
	public void placeInPortal(Entity entity, float par8){
		if(worldServer.getBlockState(pos).getBlock() != Beneath.teleporter && entity.dimension == -1){
			for(int x = -2; x < 3; x++)
				for(int y = -1; y < 3; y++)
					for(int z = -2; z < 3; z++)
						if(y > -1)
							worldServer.setBlockToAir(pos.add(x, y, z));
						else if(worldServer.isAirBlock(pos.add(x, y, z)))
						worldServer.setBlockState(pos.add(-2, 1, -2), Blocks.LAVA.getDefaultState());
						worldServer.setBlockState(pos.add(-2, 1, 2), Blocks.LAVA.getDefaultState());
						worldServer.setBlockState(pos.add(2, 1, -2), Blocks.LAVA.getDefaultState());
						worldServer.setBlockState(pos.add(2, 1, 2), Blocks.LAVA.getDefaultState());
						for(int x = -2; x < 3; x++)
							for(int y = 0; y < 3; y++)
								for(int z = -2; z < 3; z++)
									worldServer.checkLight(pos.add(x, y, z));
						worldServer.setBlockState(pos, Blocks.PORTAL.getDefaultState());
						}
			if(movementFactor > 1){
				double x = pos.getX() > entity.posX ? pos.getX() - 0.5 : pos.getX() + 1.5;
				double z = pos.getZ() > entity.posZ ? pos.getZ() - 0.5 : pos.getZ() + 1.5;
				entity.setPositionAndUpdate(MathHelper.floor(x), MathHelper.floor(entity.posY), MathHelper.floor(z));
			}
		if(entity.dimension == -1)
			worldServer.playSound(null, entity.getPosition(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.HOSTILE, 1, 1);
	}*/
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		int i = MathHelper.floor(entityIn.posX);
        int j = MathHelper.floor(entityIn.posY) - 1;
        int k = MathHelper.floor(entityIn.posZ);
        int l = 1;
        int i1 = 0;

        for (int j1 = -2; j1 <= 2; ++j1)
        {
            for (int k1 = -2; k1 <= 2; ++k1)
            {
                for (int l1 = -1; l1 < 3; ++l1)
                {
                    int i2 = i + k1 * 1 + j1 * 0;
                    int j2 = j + l1;
                    int k2 = k + k1 * 0 - j1 * 1;
                    boolean flag = l1 < 0;
                    this.world.setBlockState(new BlockPos(i2, j2, k2), flag ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                }
            }
        }
        entityIn.setLocationAndAngles((double)i, (double)j, (double)k, entityIn.rotationYaw, 0.0F);
        entityIn.motionX = 0.0D;
        entityIn.motionY = 0.0D;
        entityIn.motionZ = 0.0D;
    }
}
