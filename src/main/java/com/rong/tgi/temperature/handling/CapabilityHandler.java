package com.rong.tgi.temperature.handling;

import com.rong.tgi.TGILibrary;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler {

    public static final ResourceLocation TEMPERATURE_PROVIDER = new ResourceLocation(TGILibrary.MODID, "temperature");

    //Forge, please fix this shit. I was stuck on this for God knows how long.
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer) {
            event.addCapability(TEMPERATURE_PROVIDER, new TemperatureProvider());
        }
    }
}
