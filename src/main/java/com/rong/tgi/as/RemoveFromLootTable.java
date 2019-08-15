package com.rong.tgi.as;

import com.google.common.collect.ImmutableSet;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.util.LootTableUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod.EventBusSubscriber
public class RemoveFromLootTable {

    public static final ResourceLocation LOOT_TABLE_SHRINE = new ResourceLocation(AstralSorcery.MODID.toLowerCase(), "chest_shrine");

    private static final ImmutableSet<ResourceLocation> constellationPaperTables = ImmutableSet.of(LootTableList.CHESTS_STRONGHOLD_LIBRARY, LootTableList.CHESTS_ABANDONED_MINESHAFT, LootTableList.CHESTS_JUNGLE_TEMPLE, LootTableList.CHESTS_DESERT_PYRAMID, LootTableList.CHESTS_IGLOO_CHEST);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        if (event.getTable().isFrozen()) {
            if (constellationPaperTables.contains(name)) {
                LootTable table = event.getLootTableManager().getLootTableFromLocation(LOOT_TABLE_SHRINE);
                ReflectionHelper.setPrivateValue(LootTable.class, table, false, "isFrozen");
                event.getTable().getPool("main").removeEntry("astralsorcery:constellation_paper");
                table.getPool(AstralSorcery.MODID.toLowerCase() + "chest_shrine").removeEntry("astralsorcery:constellation_paper");
            }
        }
        if (!event.getTable().isFrozen()) {
            event.getTable().freeze();
        }
    }
}
