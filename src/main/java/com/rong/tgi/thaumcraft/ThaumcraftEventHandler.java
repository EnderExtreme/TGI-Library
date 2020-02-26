package com.rong.tgi.thaumcraft;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import thaumcraft.common.container.InventoryFake;

@Mod.EventBusSubscriber
public class ThaumcraftEventHandler {

    //BOOM
    @SubscribeEvent
    public static void onInfusionCrafted(ItemCraftedEvent event) {
        IInventory inventory = event.craftMatrix;
        if (!(inventory instanceof InventoryFake)) return;
        InventoryFake infusionInventory = (InventoryFake)inventory;
        ItemStack craftedStack = event.crafting;
        if (!(craftedStack.getItem() == ThaumcraftAddon.nightmareBook)) return;
        World world = event.player.world;
        EntityItem book = new EntityItem(world, 0, 0, 0, craftedStack);
        book.setAlwaysRenderNameTag(true);
        book.setGlowing(true);
        book.setNoDespawn();
        book.setEntityInvulnerable(true);
        world.createExplosion(book, 0, 10, 0, 20F, true);
    }

}
