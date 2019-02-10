package com.rong.tgi.temperature.recipes;

import com.rong.tgi.Helper;
import com.rong.tgi.TGILibrary;
import com.rong.tgi.temperature.TemperatureEventHandler;
import com.rong.tgi.temperature.items.ItemCoolingPad;

import gregtech.api.recipes.RecipeMaps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import zmaster587.advancedRocketry.api.AdvancedRocketryAPI;

public class TemperatureRecipes {
	
    public static void equipPads() {
    	for(ResourceLocation key : Item.REGISTRY.getKeys()) {
			Item item = Item.REGISTRY.getObject(key);
			if(item instanceof ItemArmor) {
				ItemStack cooledEnchanted = new ItemStack(item);
				ItemStack heatedEnchant = new ItemStack(item);
				cooledEnchanted.addEnchantment(TemperatureEventHandler.enchantmentCooled, 1);
				heatedEnchant.addEnchantment(TemperatureEventHandler.enchantmentCooled, 1);
				if(!(new ItemStack(item).isItemEqual(cooledEnchanted)) && !(new ItemStack(item).isItemEqual(heatedEnchant))) {
					RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(24).inputs(new ItemStack(item), new ItemStack(Item.getByNameOrId(TGILibrary.MODID + ".coolingpad"))).outputs(cooledEnchanted).buildAndRegister();
					RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(24).inputs(new ItemStack(item), new ItemStack(Item.getByNameOrId(TGILibrary.MODID + ".heatingpad"))).outputs(heatedEnchant).buildAndRegister();
				}
			}
    	}
    }
}
