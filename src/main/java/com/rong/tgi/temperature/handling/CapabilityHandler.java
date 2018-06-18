package com.rong.tgi.temperature.handling;

import com.rong.tgi.TGILibrary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler {

    public static final ResourceLocation TEMPERATURE_PROVIDER = new ResourceLocation(TGILibrary.MODID, "temperature");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<EntityPlayer> event) {
        event.addCapability(TEMPERATURE_PROVIDER, new TemperatureProvider());
    }
}
