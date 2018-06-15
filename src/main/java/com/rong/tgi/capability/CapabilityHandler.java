package com.rong.tgi.capability;

import com.rong.tgi.TGILibrary;
import com.rong.tgi.warmth.WarmthProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler {

    public static final ResourceLocation WARMTH_CAPABILITY = new ResourceLocation(TGILibrary.MODID, "warmth");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<EntityPlayer> event) {
        event.addCapability(WARMTH_CAPABILITY, new WarmthProvider());
    }
}
