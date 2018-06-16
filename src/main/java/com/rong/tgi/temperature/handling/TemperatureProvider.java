package com.rong.tgi.temperature.handling;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class TemperatureProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(ITemperature.class)
    public static final Capability<ITemperature> TEMPERATURE_CAPABILITY = null;

    private ITemperature instance = TEMPERATURE_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == TEMPERATURE_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == TEMPERATURE_CAPABILITY ? TEMPERATURE_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return TEMPERATURE_CAPABILITY.getStorage().writeNBT(TEMPERATURE_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        TEMPERATURE_CAPABILITY.getStorage().readNBT(TEMPERATURE_CAPABILITY, this.instance, null, nbt);
    }
}
