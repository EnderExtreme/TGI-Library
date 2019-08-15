package com.rong.tgi;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.rong.tgi.enderio.SAGMillRecipeAutogenerator;
import com.rong.tgi.entities.EntityManaPearl;
import com.rong.tgi.thaumcraft.ThaumcraftAddon;

import cofh.thermalexpansion.block.dynamo.TileDynamoBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void recipesLow(Register<IRecipe> event) {
    	//SAGMillRecipeAutogenerator.addRecipe();
        ThaumcraftAddon.initRecipes();
    }

    @SubscribeEvent
    public static void registerBlocks(Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();
        // r.register(new BlockFluidLiquidStarlight());
        // GameRegistry.registerTileEntity(TileEntityLiquidStarlight.class, new
        // ResourceLocation(TGILibrary.MODID + "liquid_starlight"));
    }

    @SubscribeEvent
    public static void registerItems(Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();
    }

    @SubscribeEvent
    public static void registerEntities(Register<EntityEntry> event) {
        EntityEntry entry = EntityEntryBuilder.create().entity(EntityManaPearl.class).id(new ResourceLocation(TGILibrary.MODID), 1).name("manapearl").tracker(64, 4, true).build();
        event.getRegistry().register(entry);
    }
}