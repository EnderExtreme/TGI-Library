package com.rong.tgi;

import java.util.Map;

import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

import com.rong.tgi.entities.EntityManaPearl;
import com.rong.tgi.entities.RenderManaPearl;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        if (Helper.isModLoaded("botania")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityManaPearl.class, new RenderManaPearl(Minecraft.getMinecraft().getRenderManager(), (new ItemStack(Item.getByNameOrId("botania:manaresource"), 1, 1)), Minecraft.getMinecraft().getRenderItem()));
        }
    }

    @SubscribeEvent
    protected void registerModels(ModelRegistryEvent event) {
    }
}
