package com.rong.tgi.temperature;

import com.rong.tgi.temperature.handling.ITemperature;
import com.rong.tgi.temperature.handling.TemperatureProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class TemperatureEventHandler {

    @SubscribeEvent
    public void onPlayerLoggingIn(PlayerEvent.PlayerLoggedInEvent event) {
        ITemperature temperature = event.player.getCapability(TemperatureProvider.TEMPERATURE_CAPABILITY, null);
        System.out.println(temperature.get());
    }
}
