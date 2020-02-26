package com.rong.tgi.as;

import com.rong.tgi.nc.RadiationEventHandler;

import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import nc.capability.radiation.source.IRadiationSource;
import nc.radiation.RadiationHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ASEventHandler {

    @SubscribeEvent
    public static void skipLightCheck(WorldEvent.Load event) {
        GameRules rules = event.getWorld().getGameRules();
        if (!(rules.getBoolean(MiscUtils.GAMERULE_SKIP_SKYLIGHT_CHECK))) {
            rules.addGameRule(MiscUtils.GAMERULE_SKIP_SKYLIGHT_CHECK, "true", GameRules.ValueType.BOOLEAN_VALUE);
        }
    }

    /*
    @SubscribeEvent
    public static void onStarlightInWorldCrafting(ItemEvent event) {
        if (event.getEntity().world == null)
            return;
        World world = event.getEntityItem().getEntityWorld();
        BlockPos pos = event.getEntityItem().getPosition();
        Block block = world.getBlockState(event.getEntityItem().getPosition()).getBlock();
        if (block == BlocksAS.blockLiquidStarlight && !event.getEntityItem().isDead) {
            IRadiationSource source = RadiationHelper.getRadiationSource(world.getChunkFromBlockCoords(pos));
            RadiationHelper.addToSourceRadiation(source, RadiationEventHandler.ASTRAL * 2);
        }
    }

    @SubscribeEvent
    public static void onNeighbourCheck(NeighborNotifyEvent event) {
        IBlockState state = event.getState();
        if (state.getBlock() == BlocksAS.blockLiquidStarlight) {
            IRadiationSource source = RadiationHelper.getRadiationSource(event.getWorld().getChunkFromBlockCoords(event.getPos()));
            RadiationHelper.addToSourceRadiation(source, RadiationEventHandler.ASTRAL);
        }
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.PlaceEvent event) {
        IBlockState state = event.getState();
        if (state.getBlock() == BlocksAS.blockLiquidStarlight) {
            IRadiationSource source = RadiationHelper.getRadiationSource(event.getWorld().getChunkFromBlockCoords(event.getPos()));
            RadiationHelper.addToSourceRadiation(source, RadiationEventHandler.ASTRAL);
        }
    }

    @SubscribeEvent
    public static void onBucketEmpty(FillBucketEvent event) {
        if (FluidUtil.getFluidContained(event.getEmptyBucket()) != null && FluidUtil.getFluidContained(event.getEmptyBucket()).getFluid() == BlocksAS.fluidLiquidStarlight) {
            IRadiationSource source = RadiationHelper.getRadiationSource(event.getWorld().getChunkFromBlockCoords(event.getTarget().getBlockPos()));
            RadiationHelper.addToSourceRadiation(source, RadiationEventHandler.ASTRAL);
        }
    }
    */
}