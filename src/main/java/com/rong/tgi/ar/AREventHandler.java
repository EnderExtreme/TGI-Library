package com.rong.tgi.ar;

import static muramasa.gtu.api.data.Materials.Nitrogen;
import static muramasa.gtu.api.data.RecipeMaps.CHEMICAL_REACTING;

import com.rong.tgi.Helper;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zmaster587.advancedRocketry.api.AdvancedRocketryAPI;
import zmaster587.advancedRocketry.api.AdvancedRocketryItems;
import zmaster587.advancedRocketry.armor.ItemSpaceArmor;

@Mod.EventBusSubscriber
public class AREventHandler {
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void recipesLow(Register<IRecipe> event) {
        if (!Helper.isModLoaded("advancedrocketry")) return;
        
        CHEMICAL_REACTING.RB().ii(new ItemStack(Items.BONE)).fi(Nitrogen.getLiquid(100)).io(new ItemStack(Items.DYE, 10, 15)).add(300, 24);
        
        for (ResourceLocation key : Item.REGISTRY.getKeys()) {
            Item item = Item.REGISTRY.getObject(key);
            if (item instanceof ItemArmor && !(item instanceof ItemSpaceArmor)) {
                ItemStack enchanted = new ItemStack(item);
                enchanted.addEnchantment(AdvancedRocketryAPI.enchantmentSpaceProtection, 1);
                if (((ItemArmor) item).armorType == EntityEquipmentSlot.CHEST) {
                    CHEMICAL_REACTING.RB().ii(new ItemStack(AdvancedRocketryItems.itemPressureTank, 1, 3), new ItemStack(item)).fi(FluidRegistry.getFluidStack("ice", 2592)).io(enchanted).add(1200, 286);
                } else {
                    CHEMICAL_REACTING.RB().ii(new ItemStack(item)).fi(FluidRegistry.getFluidStack("ice", 2592)).io(enchanted).add(600, 286);
                }
            }
        }
    }

}
