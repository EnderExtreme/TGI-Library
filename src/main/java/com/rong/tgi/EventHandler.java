package com.rong.tgi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.rong.tgi.entities.EntityManaPearl;
import com.rong.tgi.gt.GTRecipes;
import com.rong.tgi.temperature.recipes.TemperatureRecipes;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.item.ItemBloodOrb;
import WayofTime.bloodmagic.orb.IBloodOrb;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import gregtech.common.items.MetaTool;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.FluidPlaceBlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thebetweenlands.common.herblore.elixir.ElixirEffectRegistry;
import twilightforest.advancements.TFAdvancements;
import twilightforest.block.BlockTFPortal;
import twilightforest.block.TFBlocks;
import zmaster587.advancedRocketry.api.AdvancedRocketryItems;

@Mod.EventBusSubscriber
public class EventHandler {
	
	static String[] machines = {"enderio:block_simple_furnace", 
			  "enderio:block_simple_alloy_smelter", 
			  "enderio:block_simple_sag_mill", 
			  "enderio:block_powered_spawner",
			  "enderio:block_farm_station",
			  "enderio:block_soul_binder",
			  "enderio:block_attractor_obelisk",
			  "enderio:block_aversion_obelisk",
			  "enderio:block_inhibitor_obelisk",
			  "enderio:block_experience_obelisk",
			  "enderio:block_weather_obelisk",
			  "enderio:block_slice_and_splice",
			  "enderio:block_power_monitor",
			  "enderio:block_sag_mill",
			  "enderio:block_wired_charger",
			  "enderio:block_normal_wireless_charger",
			  "enderio:block_alloy_smelter",
			  "enderio:block_vat",
			  "enderio:block_painter",
			  "enderio:block_buffer",
			  "enderio:block_impulse_hopper",
			  "enderio:block_crafter"};

	//Dimension Switching Mumbo-Jumbo
	/*@SubscribeEvent
	public static void playerInDimensionCheck(TickEvent.PlayerTickEvent event) {
		Random rand = new Random();
		boolean doTeleport = false;
		if(event.side == Side.SERVER && event.phase == TickEvent.Phase.START && event.player.getEntityWorld().provider.getDimension() == 0) { doTeleport = !doTeleport; }
		if(doTeleport && event.player.getPosition().getY() <= /*Arbitrary number before testing with CC, should work seemlessly 30) {
			for(int tries = 1; tries > 1; tries++) {
				BlockPos spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - rand.nextInt(16) * 2, rand.nextInt(100), event.player.getPosition().getZ() / 8 - rand.nextInt(16) * 2);
				if(event.player.world.isAirBlock(spawnPos.down())) { continue; }
				else { System.out.print("took " + Integer.toString(tries) + " tries to find a valid spot for safe nether spawn!"); break; }
			}
			BlockPos spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - rand.nextInt(16) * 2, rand.nextInt(100), event.player.getPosition().getZ() / 8 - rand.nextInt(16) * 2);
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
				event.player.world.setBlockState(spawnPos.add(3, 1, 0), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(3, 1, 1), Blocks.PORTAL.getDefaultState());
			}
		}
	}*/
	
	@SubscribeEvent
	public static void disallowPickaxesBreakingMachines(BreakSpeed event) {
		IBlockState state = event.getState();
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
		
		for(String targetMachines : machines) {
			if(state == Block.getBlockFromName(targetMachines)) {
				if(stack.isItemEqual(MetaItems.WRENCH.getStackForm()) && stack.isItemEqual(MetaItems.WRENCH_LV.getStackForm()) && stack.isItemEqual(MetaItems.WRENCH_MV.getStackForm()) && stack.isItemEqual(MetaItems.WRENCH_HV.getStackForm())) {
					event.setNewSpeed(stack.getDestroySpeed(state) + 3.0F);
				}
				else {
					event.setNewSpeed(0.5F);
					event.getEntityPlayer().sendStatusMessage(new TextComponentString("Aren't you using the wrong tool to retrieve the machine?"), true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void wrenchMachines(HarvestCheck event) {		
		boolean harvestable = event.canHarvest();
		IBlockState state = event.getTargetBlock();
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
				
		for(String targetMachines : machines) {
			if(state == Block.getBlockFromName(targetMachines)) {
				if(stack.isItemEqual(MetaItems.WRENCH.getStackForm()) && stack.isItemEqual(MetaItems.WRENCH_LV.getStackForm()) && stack.isItemEqual(MetaItems.WRENCH_MV.getStackForm()) && stack.isItemEqual(MetaItems.WRENCH_HV.getStackForm())) {
					event.setCanHarvest(true);
				}
				else { 
					event.setCanHarvest(false);
				}
			}
		}
	}
	
	//This is so we don't get VanillaFoodPantry's llama meat and also lamb meat at the same time
	@SubscribeEvent
	public static void dropCancellation(LivingDropsEvent event) {
		if(event.getEntity() instanceof EntityLlama) {
			World world = event.getEntity().world;
			int x = event.getEntity().chunkCoordX;
			int y = event.getEntity().chunkCoordY;
			int z = event.getEntity().chunkCoordZ;
			event.getDrops().remove(new EntityItem(world, x, y, z, new ItemStack(Items.MUTTON)));
		}
	}
	
	@SubscribeEvent
	public static void doNotSpawnPortal(BlockEvent.PortalSpawnEvent event) {
		int dimension = event.getWorld().provider.getDimension();
		if(/*(event.getPortalSize().getHeight() < 5 && event.getPortalSize().getWidth() < 5) ||*/ dimension == 0) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void dontPlaceNearTwilightPortal(BlockEvent.PlaceEvent event) {
		IBlockState up = event.getWorld().getBlockState(event.getPos().up());
		IBlockState down = event.getWorld().getBlockState(event.getPos().down());
		IBlockState north = event.getWorld().getBlockState(event.getPos().north());
		IBlockState east = event.getWorld().getBlockState(event.getPos().east());
		IBlockState south = event.getWorld().getBlockState(event.getPos().south());
		IBlockState west = event.getWorld().getBlockState(event.getPos().west());
		boolean allDirections = up == TFBlocks.portal.getDefaultState() || down == TFBlocks.portal.getDefaultState() || north == TFBlocks.portal.getDefaultState() || east == TFBlocks.portal.getDefaultState() || south == TFBlocks.portal.getDefaultState() || west == TFBlocks.portal.getDefaultState();
		if(event.getPlacedAgainst() == TFBlocks.portal.getDefaultState() || allDirections) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void dontBreakNearTwilightPortal(BlockEvent.BreakEvent event) {
		IBlockState up = event.getWorld().getBlockState(event.getPos().up());
		IBlockState down = event.getWorld().getBlockState(event.getPos().down());
		IBlockState north = event.getWorld().getBlockState(event.getPos().north());
		IBlockState east = event.getWorld().getBlockState(event.getPos().east());
		IBlockState south = event.getWorld().getBlockState(event.getPos().south());
		IBlockState west = event.getWorld().getBlockState(event.getPos().west());
		boolean allDirections = up == TFBlocks.portal.getDefaultState() || down == TFBlocks.portal.getDefaultState() || north == TFBlocks.portal.getDefaultState() || east == TFBlocks.portal.getDefaultState() || south == TFBlocks.portal.getDefaultState() || west == TFBlocks.portal.getDefaultState();
		if(allDirections) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void makeManaPearlGreatAgain(PlayerInteractEvent.RightClickItem event) {
		
		ItemStack stack = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		Random rand = new Random();
		
		//Sorry Folks
		float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        double d0 = player.posX;
        double d1 = player.posY + (double)player.getEyeHeight();
        double d2 = player.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
        RayTraceResult raytraceResult = world.rayTraceBlocks(vec3d, vec3d1, false);
       
        if(stack.isItemEqual(new ItemStack(Item.getByNameOrId("botania:manaresource"), 1, 1))) {
        	if (raytraceResult != null && raytraceResult.typeOfHit == RayTraceResult.Type.BLOCK && world.getBlockState(raytraceResult.getBlockPos()).getBlock() == Blocks.END_PORTAL_FRAME) {}
        	else {
        		player.setActiveHand(event.getHand());
        		if (!world.isRemote) {
        			BlockPos pos = ((WorldServer)world).getChunkProvider().getNearestStructurePos(world, "Stronghold", new BlockPos(player), false);
        			if (pos != null && !(((WorldServer)world).getChunkProvider().isInsideStructure(world, "Stronghold", player.getPosition()))) {
        				EntityManaPearl entityManaPearl = new EntityManaPearl(world, player.posX, player.posY + (double)(player.height / 2.0F), player.posZ);
        				entityManaPearl.moveTowards(pos);
        				world.spawnEntity(entityManaPearl);        				
        				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
        				world.playEvent((EntityPlayer)null, 1003, new BlockPos(player), 0);
        				if (!player.capabilities.isCreativeMode) { stack.shrink(1); }
        					player.addStat(StatList.getObjectUseStats(stack.getItem()));
        			}
        		}
        	}
        }
        if(stack.isItemEqual(new ItemStack(Items.ENDER_EYE))) {
        	event.setCanceled(true);
        	if (event.isCanceled() && raytraceResult != null && raytraceResult.typeOfHit == RayTraceResult.Type.BLOCK && (world.getBlockState(raytraceResult.getBlockPos()).getBlock() == Blocks.END_BRICKS || world.getBlockState(raytraceResult.getBlockPos()).getBlock() == Blocks.PURPUR_BLOCK)) {}
        	else {
        		player.setActiveHand(event.getHand());
        		if (!world.isRemote) {
        			BlockPos exploredPos = ((WorldServer)world).getChunkProvider().getNearestStructurePos(world, "EndCity", new BlockPos(player), false);
        			if (exploredPos != null && !(((WorldServer)world).getChunkProvider().isInsideStructure(world, "EndCity", player.getPosition()))) {
        				EntityEnderEye entityEnderEye = new EntityEnderEye(world, player.posX, player.posY + (double)(player.height / 2.0F), player.posZ);
        				entityEnderEye.moveTowards(exploredPos);
        				world.spawnEntity(entityEnderEye);        				
        				if (player instanceof EntityPlayerMP) {
        					CriteriaTriggers.USED_ENDER_EYE.trigger((EntityPlayerMP)player, exploredPos);
        				}
        				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
        				world.playEvent((EntityPlayer)null, 1003, new BlockPos(player), 0);
        				if (!player.capabilities.isCreativeMode) { stack.shrink(1); }
        					player.addStat(StatList.getObjectUseStats(stack.getItem()));
        			}
        			BlockPos unexploredPos = ((WorldServer)world).getChunkProvider().getNearestStructurePos(world, "EndCity", new BlockPos(player), true);
        			if(unexploredPos != null && (((WorldServer)world).getChunkProvider().isInsideStructure(world, "EndCity", player.getPosition()))) {        				
        				EntityEnderEye entityEnderEye = new EntityEnderEye(world, player.posX, player.posY + (double)(player.height / 2.0F), player.posZ);
        				entityEnderEye.moveTowards(exploredPos);
        				world.spawnEntity(entityEnderEye);        				
        				if (player instanceof EntityPlayerMP) {
        					CriteriaTriggers.USED_ENDER_EYE.trigger((EntityPlayerMP)player, exploredPos);
        				}
        				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
        				world.playEvent((EntityPlayer)null, 1003, new BlockPos(player), 0);
        				if (!player.capabilities.isCreativeMode) { stack.shrink(1); }
        					player.addStat(StatList.getObjectUseStats(stack.getItem()));
        			}
        		}
        	}
        }
	}
	
	@SubscribeEvent
	public static void carbonCartridge(PlayerInteractEvent.RightClickItem event) {
		if(event.getItemStack().isItemEqual(new ItemStack(AdvancedRocketryItems.itemCarbonScrubberCartridge, 1, AdvancedRocketryItems.itemCarbonScrubberCartridge.getMaxDamage()))) {
			event.getItemStack().setItemDamage(0);
			event.getEntityPlayer().addItemStackToInventory(new ItemStack(Items.COAL, 4, 1));
		}
	}
	
	@SubscribeEvent
	public static void createBeneathTeleporter(PlayerInteractEvent.RightClickBlock event) {
		BlockPos bedrockPos = event.getPos();
		World world = event.getEntity().getEntityWorld();
		EntityPlayer player = event.getEntityPlayer();
		IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
		SoulNetwork network = NetworkHelper.getSoulNetwork(player);
		if(knowledge.isResearchKnown("m_deepdown") && 
		   world.getBlockState(bedrockPos) == Blocks.BEDROCK && 
		   player.getHeldItem(event.getHand()).isItemEqual(new ItemStack(Item.getByNameOrId("bloodmagic:sigil_void"))))
		   /*network.getCurrentEssence() >= 25000 &&*/
		   /*world.canBlockSeeSky(bedrockPos))*/ {
			world.setBlockState(bedrockPos.up(), Block.getBlockFromName("beneath:teleporterbeneath").getDefaultState());				
			//network.syphon(25000);
		}
	}

	@SubscribeEvent
	public static void endPortalShinanigans(PlayerInteractEvent.RightClickBlock event) {		
		Random itemRand = new Random();
		World world = event.getWorld();
		IBlockState state = world.getBlockState(event.getPos());
		ItemStack stack = event.getItemStack();
		BlockPos pos = event.getPos();

        if(stack.isItemEqual(new ItemStack(Item.getByNameOrId("botania:manaresource"), 1, 1)) && event.getEntityPlayer().canPlayerEdit(pos.offset(event.getFace()), event.getFace(), stack) && state.getBlock() == Blocks.END_PORTAL_FRAME && !(state.getValue(BlockEndPortalFrame.EYE)).booleanValue()) {
            if(world.isRemote) {}
            else {
                world.setBlockState(pos, state.withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(true)), 2);
                world.updateComparatorOutputLevel(pos, Blocks.END_PORTAL_FRAME);
                stack.shrink(1);

                for(int i = 0; i < 16; ++i) {
                    double d0 = (double) ((float) pos.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
                    double d1 = (double) ((float) pos.getY() + 0.8125F);
                    double d2 = (double) ((float) pos.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
                    double d3 = 0.0D;
                    double d4 = 0.0D;
                    double d5 = 0.0D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }

                world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                BlockPattern.PatternHelper blockpattern$patternhelper = BlockEndPortalFrame.getOrCreatePortalShape().match(world, pos);

                if(blockpattern$patternhelper != null) {
                    BlockPos blockpos = blockpattern$patternhelper.getFrontTopLeft().add(-3, 0, -3);

                    for(int j = 0; j < 3; ++j) {
                        for(int k = 0; k < 3; ++k) {
                            world.setBlockState(blockpos.add(j, 0, k), Block.getBlockFromName("twilightforest:twilight_portal").getDefaultState(), 2);
                        }
                    }
                    TFAdvancements.MADE_TF_PORTAL.trigger((EntityPlayerMP) event.getEntityPlayer());
                    EntityLightningBolt bolt = new EntityLightningBolt(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, true);
                    world.addWeatherEffect(bolt);
                    world.playBroadcastSound(1038, blockpos.add(1, 0, 1), 0);
                }
            }
        }
        
        if(stack.isItemEqual(new ItemStack(Items.ENDER_EYE))) {
        	event.setCanceled(true);
        }  
	}
	
	@SubscribeEvent
	public static void toTheEnd(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if(ElixirEffectRegistry.EFFECT_FOGGEDMIND.isActive(player) && ElixirEffectRegistry.EFFECT_FOGGEDMIND.getDuration(player) < 40) {
			if (!event.player.world.isRemote && !player.isRiding()) {
				player.changeDimension(1);
	        }
		}
	}
}
