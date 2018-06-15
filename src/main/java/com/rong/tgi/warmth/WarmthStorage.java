package com.rong.tgi.warmth;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class WarmthStorage implements Capability.IStorage<IWarmth> {

    @Override
    public NBTBase writeNBT(Capability<IWarmth> capability, IWarmth instance, EnumFacing side) {
        return new NBTTagFloat(instance.getWarmth());
    }

    @Override
    public void readNBT(Capability<IWarmth> capability, IWarmth instance, EnumFacing side, NBTBase nbt)
    {
        instance.setWarmth(((NBTPrimitive) nbt).getFloat());
    }
}
