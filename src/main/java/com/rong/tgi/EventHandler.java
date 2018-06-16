package com.rong.tgi;

import java.util.concurrent.ThreadLocalRandom;

import com.rong.tgi.dimension.BlockPortalToggleable;
import com.rong.tgi.temperature.handling.ITemperature;
import com.rong.tgi.temperature.handling.TemperatureProvider;
import com.rong.tgi.temperature.items.ItemCoolingPad;
import com.rong.tgi.temperature.recipes.RecipesCoolingPad;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void playerTemperatureCheck(TickEvent.PlayerTickEvent event) {
		ITemperature temperature = event.player.getCapability(TemperatureProvider.TEMPERATURE_CAPABILITY, null);
        BlockPos playerPos = event.player.getPosition();
        float biomeTemperature = event.player.world.getBiome(playerPos).getTemperature(playerPos);
        boolean isDefaultTemperature = event.player.isCreative() && event.player.getIsInvulnerable();
        if(event.side == Side.SERVER) {
            //Default Warmth
            if(isDefaultTemperature) {
				temperature.setTemperature(0.0F);
            }
            //Handles cold biomes (Anything below Extreme Hills temperature)
            if(!isDefaultTemperature && biomeTemperature < 0.2 && biomeTemperature > -0.05 && event.player.world instanceof World) {
            	//event.player.world.tick
                temperature.delTemperature(0.01F);
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
				else { System.out.print("took " + Integer.toString(tries) + " tries to find a valid spot for safe nether spawn!"); break; }
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

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemCoolingPad().setCreativeTab(CreativeTabs.MATERIALS).setMaxStackSize(4).setUnlocalizedName(TGILibrary.MODID + ".coolingpad").setRegistryName("coolingpad"));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        registry.register(new RecipesCoolingPad().setRegistryName("COOLING_PAD_TO_ARMOR"));
    }
}
