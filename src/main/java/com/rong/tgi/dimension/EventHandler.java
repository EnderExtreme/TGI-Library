package com.rong.tgi.dimension;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber
public class EventHandler {
	
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		boolean doTeleport = false;
		if(event.side == Side.SERVER && event.phase == TickEvent.Phase.START && event.player.getEntityWorld().provider.getDimension() == 0) { doTeleport = !doTeleport; }
		if(doTeleport && event.player.getPosition().getY() <= /*Arbitrary number before testing with CC, should work seemlessly*/ 30) {
			for(int tries = 1; tries > 1; tries++) {
				BlockPos spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - ThreadLocalRandom.current().nextInt(16) * 2, ThreadLocalRandom.current().nextInt(50, 120), event.player.getPosition().getZ() / 8 - ThreadLocalRandom.current().nextInt(16) * 2);
				if(event.player.world.isAirBlock(spawnPos.down())) { continue; }
				else { break; }
				/*if(event.player.world.isAirBlock(spawnPos.down())) {
					event.player.world.setBlockState(spawnPos.add(0, -1, 0), Blocks.NETHERRACK.getDefaultState());
					event.player.world.setBlockState(spawnPos.add(-1, -1, 0), Blocks.NETHERRACK.getDefaultState());
					event.player.world.setBlockState(spawnPos.add(0, -1, -1), Blocks.NETHERRACK.getDefaultState());
					event.player.world.setBlockState(spawnPos.add(-1, -1, -1), Blocks.NETHERRACK.getDefaultState());
				}*/
			}
			BlockPos spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - ThreadLocalRandom.current().nextInt(16) * 2, ThreadLocalRandom.current().nextInt(50, 120), event.player.getPosition().getZ() / 8 - ThreadLocalRandom.current().nextInt(16) * 2);
			event.player.setPortal(spawnPos);
			if(event.player.changeDimension(-1) != null) {
				while(event.player.world.getBlockState(spawnPos).isNormalCube()){
					event.player.world.setBlockToAir(spawnPos); /*x  y  z*/
					event.player.world.setBlockToAir(spawnPos.add(1, 0, 1));
					event.player.world.setBlockToAir(spawnPos.add(1, 1, 1));
					event.player.world.setBlockToAir(spawnPos.add(1, 2, 1));
					event.player.world.setBlockToAir(spawnPos.add(0, 0, 1));
					event.player.world.setBlockToAir(spawnPos.add(0, 1, 1));
					event.player.world.setBlockToAir(spawnPos.add(0, 2, 1));
					event.player.world.setBlockToAir(spawnPos.add(0, 1, 0));
					event.player.world.setBlockToAir(spawnPos.add(0, 2, 0));
					event.player.world.setBlockToAir(spawnPos.add(1, 0, 0));
					event.player.world.setBlockToAir(spawnPos.add(1, 1, 0));
					event.player.world.setBlockToAir(spawnPos.add(1, 2, 0));
					//spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - 16 * 2 * expandMult + rand.nextInt(16 * 4 * expandMult), rand.nextInt(10), event.player.getPosition().getZ() / 8 - 16 * 2 * expandMult + rand.nextInt(16 * 4 * expandMult));
					//event.player.setPortal(spawnPos);
					//expandMult++;
				}
				event.player.setPositionAndUpdate(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
				event.player.world.setBlockState(spawnPos.add(0, 3, 0), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(1, 3, 1), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(1, 3, 0), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(0, 3, 1), Blocks.PORTAL.getDefaultState());
				//Set blindness + fire, blindness for 10 seconds and fire for 5
				event.player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10, 3));
				event.player.setFire(5);
			}
		}
	}
}
