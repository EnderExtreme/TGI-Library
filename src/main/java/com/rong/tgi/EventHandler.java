package com.rong.tgi;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.rong.tgi.dimension.BlockPortalToggleable;
import com.rong.tgi.warmth.IWarmth;
import com.rong.tgi.warmth.WarmthProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import scala.Int;

@Mod.EventBusSubscriber
public class EventHandler {

    //Happens every time player is added to the world
    @SubscribeEvent
    public static void setDefaultWarm(EntityJoinWorldEvent event) {
        EntityPlayer player = (EntityPlayer) event.getEntity();
        IWarmth warmth = player.getCapability(WarmthProvider.WARMTH_CAPABILITY, null);
        if(!event.getWorld().isRemote)
            warmth.setWarmth(1.0F);
    }

    @SubscribeEvent
    public static void playerWarmthTick(TickEvent.PlayerTickEvent event) {
        IWarmth warmth = event.player.getCapability(WarmthProvider.WARMTH_CAPABILITY, null);
        BlockPos playerPos = event.player.getPosition();
        float biomeTemp = event.player.world.getBiome(playerPos).getTemperature(playerPos);
        if(event.player.world.isRemote) {
            //Default Warmth
            if(event.player.isCreative() && event.player.getIsInvulnerable()) {
                warmth.setWarmth(1.0F);
            }
            //Handles cold biomes (Anything below Extreme Hills temperature)
            if(!isDefaultWarm && biomeTemp < 0.2 && biomeTemp > -0.05) {
                warmth.delWarmth(0.01F);
            }
            if()
            }
        }

    }

	//Dimension Switching Mumbo-Jumbo

	@SubscribeEvent
	public static void playerInDimensionCheck(TickEvent.PlayerTickEvent event) {
		boolean doTeleport = false;
		if(event.side == Side.SERVER && event.phase == TickEvent.Phase.START && event.player.getEntityWorld().provider.getDimension() == 0) { doTeleport = !doTeleport; }
		if(doTeleport && event.player.getPosition().getY() <= /*Arbitrary number before testing with CC, should work seemlessly*/ 30) {
			for(int tries = 1; tries > 1; tries++) {
				BlockPos spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - ThreadLocalRandom.current().nextInt(16) * 2, ThreadLocalRandom.current().nextInt(10, 120), event.player.getPosition().getZ() / 8 - ThreadLocalRandom.current().nextInt(16) * 2);
				if(event.player.world.isAirBlock(spawnPos.down())) { continue; }
				else { System.out.print("took " + Integer.toString(tries) + " tries to find a valid spot for safe nether landing!"); break; }
			}
			BlockPos spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - ThreadLocalRandom.current().nextInt(16) * 2, ThreadLocalRandom.current().nextInt(10, 120), event.player.getPosition().getZ() / 8 - ThreadLocalRandom.current().nextInt(16) * 2);
			event.player.setPortal(spawnPos);
			if(event.player.changeDimension(-1) != null) {
				while(event.player.world.getBlockState(spawnPos).isNormalCube()){
					event.player.world.setBlockToAir(spawnPos);
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
				}
				event.player.setPositionAndUpdate(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
				event.player.world.setBlockState(spawnPos.add(3, 0, 0), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(3, 0, 1), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(3, 0, 0), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(3, 0, 1), Blocks.PORTAL.getDefaultState());
				event.player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 60, 1));
				event.player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60, 3));
				event.player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 60, 1));
				event.player.setFire(5);
			}
		}
	}

	//Needed for the dimension stuff, overrides vanilla portal block
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockPortalToggleable());
    }
}
