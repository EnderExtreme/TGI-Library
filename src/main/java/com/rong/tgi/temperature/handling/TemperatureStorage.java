package com.rong.tgi.temperature.handling;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TemperatureStorage implements Capability.IStorage<ITemperature> {

    @Override
    public NBTBase writeNBT(Capability<ITemperature> capability, ITemperature instance, EnumFacing side) {
        return new NBTTagFloat(instance.getTemperature());
    }

    @Override
    public void readNBT(Capability<ITemperature> capability, ITemperature instance, EnumFacing side, NBTBase nbt)
    {
        instance.setTemperature(((NBTPrimitive) nbt).getFloat());
    }
}
