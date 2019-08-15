package com.rong.tgi.stages;

import static com.rong.tgi.DimensionIDs.*;
import static com.rong.tgi.as.ASConstellations.AEVITAS;
import static com.rong.tgi.as.ASConstellations.ALCARA;
import static com.rong.tgi.as.ASConstellations.ARMARA;
import static com.rong.tgi.as.ASConstellations.BOOTES;
import static com.rong.tgi.as.ASConstellations.DISCIDIA;
import static com.rong.tgi.as.ASConstellations.EVORSIO;
import static com.rong.tgi.as.ASConstellations.FORNAX;
import static com.rong.tgi.as.ASConstellations.GELU;
import static com.rong.tgi.as.ASConstellations.HOROLOGIUM;
import static com.rong.tgi.as.ASConstellations.LUCERNA;
import static com.rong.tgi.as.ASConstellations.MINERALIS;
import static com.rong.tgi.as.ASConstellations.OCTANS;
import static com.rong.tgi.as.ASConstellations.PELOTRIO;
import static com.rong.tgi.as.ASConstellations.ULTERIA;
import static com.rong.tgi.as.ASConstellations.VICIO;
import static com.rong.tgi.as.ASConstellations.VORUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rong.tgi.as.ASConstellations;

import hellfirepvp.astralsorcery.common.integrations.mods.crafttweaker.tweaks.GameStageTweaks;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import thebetweenlands.common.handler.AdvancementHandler;

@Mod.EventBusSubscriber
public class StageEventHandler {

    public static final String stagePrefix = "stage.";

    public static List<String> allConstellations = new ArrayList<String>(Arrays.asList(AEVITAS, ARMARA, DISCIDIA, EVORSIO, VICIO, BOOTES, FORNAX, HOROLOGIUM, LUCERNA, MINERALIS, OCTANS, PELOTRIO, ALCARA, GELU, ULTERIA, VORUX));

    // Set at the beginning
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer && event.getWorld().provider.getDimension() == OVERWORLD) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            if (playerMP.getStatFile().readStat(StatList.PLAY_ONE_MINUTE) < 10) {
                for (String constellation : allConstellations) {
                    GameStageTweaks.addConstellationDiscoveryStage(stagePrefix + constellation, constellation);
                }
            }
        }
    }

    // This is done to primarily handle AS Constellations gating!
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {

        int current = event.fromDim;
        int destination = event.toDim;
        EntityPlayer player = event.player;
        World world = player.world;

        if (player.world.isRemote)
            return;

        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        WorldServer worldServer = (WorldServer) world;
        AdvancementManager manager = worldServer.getAdvancementManager();
        AdvancementProgress endGatewayProgress = playerMP.getAdvancements().getProgress(manager.getAdvancement(new ResourceLocation("end/enter_end_gateway")));

        if (endGatewayProgress.isDone()) {
            if (destination == OVERWORLD) {
                for (String constellation : allConstellations) {
                    if (constellation == PELOTRIO || constellation == BOOTES) {
                        GameStageHelper.addStage(player, stagePrefix + PELOTRIO);
                        GameStageHelper.addStage(player, stagePrefix + BOOTES);
                    } else {
                        GameStageHelper.removeStage(player, stagePrefix + constellation);
                    }
                }
            } else if (destination == END) {
                GameStageHelper.addStage(player, stagePrefix + VICIO);
                GameStageHelper.addStage(player, stagePrefix + GELU);
            } else if (destination == NETHER) {
                GameStageHelper.addStage(player, stagePrefix + DISCIDIA);
                GameStageHelper.addStage(player, stagePrefix + FORNAX);
                GameStageHelper.addStage(player, stagePrefix + VORUX);
            } else if (destination == LOST_CITIES) {
                GameStageHelper.addStage(player, stagePrefix + ALCARA);
                GameStageHelper.addStage(player, stagePrefix + ULTERIA);
            //} else if (destination == LIMBO) { TODO: substitute!
                //GameStageHelper.addStage(player, stagePrefix + HOROLOGIUM);
                //GameStageHelper.addStage(player, stagePrefix + GELU);
            } else if (destination == TWILIGHT_FOREST) {
                GameStageHelper.addStage(player, stagePrefix + BOOTES);
                GameStageHelper.addStage(player, stagePrefix + AEVITAS);
            } else if (destination == BENEATH) {
                GameStageHelper.addStage(player, stagePrefix + ARMARA);
                GameStageHelper.addStage(player, stagePrefix + EVORSIO);
                GameStageHelper.addStage(player, stagePrefix + MINERALIS);
            } else if (destination == AETHER) {
                GameStageHelper.addStage(player, stagePrefix + LUCERNA);
                GameStageHelper.addStage(player, stagePrefix + AEVITAS);
            } else if (destination == BETWEENLANDS) {
                GameStageHelper.addStage(player, stagePrefix + OCTANS);
            }
        }
    }
}