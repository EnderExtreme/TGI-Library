package com.rong.tgi.gt;

import com.rong.tgi.Helper;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class GTEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void recipesLow(Register<IRecipe> event) {
        GTRecipes.dyes();
    }

}
