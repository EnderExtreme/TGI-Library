package com.rong.tgi.warmth;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WarmthProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IWarmth.class)
    public static final Capability<IWarmth> WARMTH_CAPABILITY = null;

    private IWarmth instance = WARMTH_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == WARMTH_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == WARMTH_CAPABILITY ? WARMTH_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return WARMTH_CAPABILITY.getStorage().writeNBT(WARMTH_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        WARMTH_CAPABILITY.getStorage().readNBT(WARMTH_CAPABILITY, this.instance, null, nbt);
    }
}
