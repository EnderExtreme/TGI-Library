package com.rong.tgi.temperature;

import java.util.Map;
import java.util.Random;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rong.tgi.Helper;
import com.rong.tgi.TGILibrary;
import com.rong.tgi.temperature.enchant.EnchantmentCooled;
import com.rong.tgi.temperature.enchant.EnchantmentHeated;
import com.rong.tgi.temperature.handling.ITemperature;
import com.rong.tgi.temperature.handling.Temperature;
import com.rong.tgi.temperature.handling.TemperatureProvider;
import com.rong.tgi.temperature.items.ItemCoolingPad;
import com.rong.tgi.temperature.items.ItemHeatingPad;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import scala.Int;

@Mod.EventBusSubscriber
public class TemperatureEventHandler {

    private static String coolPad = "coolingPad";
    private static String heatPad = "heatingPad";

    public static Enchantment enchantmentHeated;
    public static Enchantment enchantmentCooled;

    // Naming is fucked, getFrom returns ItemStack that has been taken off of the
    // EntityEquipmentSlot, and can be ItemStack.EMPTY
    // However, getTo is the ItemStack put on in EntityEquipmentSlot, and can only
    // be ItemStack.EMPTY when taking off armor not replacing
    // Extra check that ItemStack.getItem is actually ItemArmor because I've heard
    // instances where it can actually be not of ItemArmor (what the fuck?)
    @SubscribeEvent
    public static void onArmorChange(LivingEquipmentChangeEvent event) {
        // Make sure entity getting temperature capability is EntityPlayer
        if (event.getEntityLiving() instanceof EntityPlayer) {
            ITemperature temperature = event.getEntityLiving().getCapability(TemperatureProvider.TEMPERATURE_CAPABILITY, null);
            ItemStack slotStack = event.getTo();
            if (event.getSlot() == EntityEquipmentSlot.HEAD && slotStack.getItem() instanceof ItemArmor) {
                if (getEnchantmentMap(slotStack).containsKey(enchantmentCooled)) {
                    switch (enchantLevel(enchantmentCooled, slotStack)) {
                    case 1:
                        temperature.decrease(15.0F);
                    case 2:
                        temperature.decrease(20.0F);
                    }
                }
                if (getEnchantmentMap(slotStack).containsKey(enchantmentHeated)) {
                    switch (enchantLevel(enchantmentHeated, slotStack)) {
                    case 1:
                        temperature.increase(15.0F);
                    case 2:
                        temperature.increase(20.0F);
                    }
                }
            }
            if (event.getSlot() == EntityEquipmentSlot.CHEST && slotStack.getItem() instanceof ItemArmor) {
                if (getEnchantmentMap(slotStack).containsKey(enchantmentCooled)) {
                    switch (enchantLevel(enchantmentCooled, slotStack)) {
                    case 1:
                        temperature.decrease(30.0F);
                    case 2:
                        temperature.decrease(35.0F);
                    }
                }
                if (getEnchantmentMap(slotStack).containsKey(enchantmentHeated)) {
                    switch (enchantLevel(enchantmentHeated, slotStack)) {
                    case 1:
                        temperature.increase(30.0F);
                    case 2:
                        temperature.increase(35.0F);
                    }
                }
            }
            if (event.getSlot() == EntityEquipmentSlot.LEGS && slotStack.getItem() instanceof ItemArmor) {
                if (getEnchantmentMap(slotStack).containsKey(enchantmentCooled)) {
                    switch (enchantLevel(enchantmentCooled, slotStack)) {
                    case 1:
                        temperature.decrease(20.0F);
                    case 2:
                        temperature.decrease(25.0F);
                    }
                }
                if (getEnchantmentMap(slotStack).containsKey(enchantmentHeated)) {
                    switch (enchantLevel(enchantmentHeated, slotStack)) {
                    case 1:
                        temperature.increase(20.0F);
                    case 2:
                        temperature.increase(25.0F);
                    }
                }
            }
            if (event.getSlot() == EntityEquipmentSlot.FEET && slotStack.getItem() instanceof ItemArmor) {
                if (getEnchantmentMap(slotStack).containsKey(enchantmentCooled)) {
                    switch (enchantLevel(enchantmentCooled, slotStack)) {
                    case 1:
                        temperature.decrease(10.0F);
                    case 2:
                        temperature.decrease(15.0F);
                    }
                }
                if (getEnchantmentMap(slotStack).containsKey(enchantmentHeated)) {
                    switch (enchantLevel(enchantmentHeated, slotStack)) {
                    case 1:
                        temperature.increase(10.0F);
                    case 2:
                        temperature.increase(15.0F);
                    }
                }
            }
        }
    }

    /*
     * ---CUSTOM TEMPERATURE BIOME SYSTEM--- TAIGA = -20.0 (FREEZING) NETHER = 100.0
     * END = -100.0
     * 
     * 
     */

    /*
     * ---CUSTOM TEMPERATURE CONTROL SYSTEM--- HEAD = +10, -10 CHEST = +30. -30 LEGS
     * = +25, -25 FEET = +5, -5 ENCHANTMENTS: FIRE PROTECTION (LVL1-5) = -5.0,
     * -10.0, -15.0, -20.0, -25.0
     */

    @SubscribeEvent
    public static void playerTemperatureCheck(TickEvent.PlayerTickEvent event) {

        Random rand = new Random();
        ITemperature temperature = event.player.getCapability(TemperatureProvider.TEMPERATURE_CAPABILITY, null);
        ItemStack helmetSlot = event.player.inventory.armorInventory.get(0);
        ItemStack chestSlot = event.player.inventory.armorInventory.get(1);
        ItemStack legSlot = event.player.inventory.armorInventory.get(2);
        ItemStack bootsSlot = event.player.inventory.armorInventory.get(3);
        BlockPos playerPos = event.player.getPosition();
        float biomeTemperature = event.player.world.getBiome(playerPos).getTemperature(playerPos);
        int playerCurrentDim = event.player.dimension;
        boolean isDefault = event.player.isCreative() && event.player.getIsInvulnerable();
        boolean playerHasFullArmorOn = !helmetSlot.isEmpty() && !chestSlot.isEmpty() && !legSlot.isEmpty() && !bootsSlot.isEmpty();
        boolean coolingSafeNBT = Helper.checkIfNBTNotNull(helmetSlot, coolPad) && Helper.checkIfNBTNotNull(chestSlot, coolPad) && Helper.checkIfNBTNotNull(legSlot, coolPad) && Helper.checkIfNBTNotNull(bootsSlot, coolPad);
        boolean heatingSafeNBT = Helper.checkIfNBTNotNull(helmetSlot, heatPad) && Helper.checkIfNBTNotNull(chestSlot, heatPad) && Helper.checkIfNBTNotNull(legSlot, heatPad) && Helper.checkIfNBTNotNull(bootsSlot, heatPad);
        boolean isArmorSetCooled = Helper.getStackNBTSafe(helmetSlot).getBoolean(coolPad) && Helper.getStackNBTSafe(chestSlot).getBoolean(coolPad) && Helper.getStackNBTSafe(legSlot).getBoolean(coolPad) && Helper.getStackNBTSafe(bootsSlot).getBoolean(coolPad);
        boolean isArmorSetHeated = Helper.getStackNBTSafe(helmetSlot).getBoolean(heatPad) && Helper.getStackNBTSafe(chestSlot).getBoolean(heatPad) && Helper.getStackNBTSafe(legSlot).getBoolean(heatPad) && Helper.getStackNBTSafe(bootsSlot).getBoolean(heatPad);

        // Check if everything is done server-side, only if we implement something
        // graphical we would need to check for client
        if (event.side == Side.SERVER) {
            // Default temperature, either player in creative or is set invulnerable
            if (isDefault) {
                temperature.set(0.0F);
            }
            // HOT ENVIRONMENT (MOSTLY PLANETS) HANDLING
            // NETHER: 100
            if (coolingSafeNBT && !isArmorSetCooled && playerCurrentDim == -1 /* nether */) {
                event.player.attackEntityFrom(DamageSource.LAVA, 2.0F);
            }
            // COLD ENVIRONMENT (MOSTLY PLANETS) HANDLING
            // END: DEFAULT -10, CAN GO DOWN TO -100
            if (heatingSafeNBT && !isArmorSetHeated && rand.nextInt(500) == 250 && playerCurrentDim == 1 /* end */) {
                event.player.attackEntityFrom(DamageSource.MAGIC, 2.0F);
            }
            if (temperature.get() > 85.0F) {
                event.player.setFire(1);
            }
            // Handles cold biomes (Anything below Extreme Hills temperature)
            if (!isDefault && biomeTemperature < 0.2F && biomeTemperature > -0.05F) {
                temperature.decrease(0.01F);
            }
        }
    }

    @SubscribeEvent
    public static void padItemToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (getEnchantmentMap(stack).containsKey(enchantmentCooled) && stack.getItem() instanceof ItemArmor) {
            event.getToolTip().add(ChatFormatting.AQUA + "Actively Cooled");
        }
        if (getEnchantmentMap(stack).containsKey(enchantmentHeated) && stack.getItem() instanceof ItemArmor) {
            event.getToolTip().add(ChatFormatting.RED + "Actively Heated");
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemCoolingPad().setCreativeTab(CreativeTabs.MATERIALS).setMaxStackSize(4).setUnlocalizedName(TGILibrary.MODID + ".coolingpad").setRegistryName("coolingpad"));
        event.getRegistry().register(new ItemHeatingPad().setCreativeTab(CreativeTabs.MATERIALS).setMaxStackSize(4).setUnlocalizedName(TGILibrary.MODID + ".heatingpad").setRegistryName("heatingpad"));
    }

    @SubscribeEvent
    public static void registerEnchants(RegistryEvent.Register<Enchantment> event) {
        enchantmentHeated = new EnchantmentHeated();
        enchantmentHeated.setRegistryName(new ResourceLocation(TGILibrary.MODID, "heated"));
        enchantmentCooled = new EnchantmentCooled();
        enchantmentCooled.setRegistryName(new ResourceLocation(TGILibrary.MODID, "cooled"));
        event.getRegistry().register(enchantmentHeated);
        event.getRegistry().register(enchantmentCooled);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        ITemperature temperature = player.getCapability(TemperatureProvider.TEMPERATURE_CAPABILITY, null);
        ITemperature oldTemperature = event.getOriginal().getCapability(TemperatureProvider.TEMPERATURE_CAPABILITY, null);
        temperature.set(oldTemperature.get());
    }

    private static Map getEnchantmentMap(ItemStack stack) {
        return EnchantmentHelper.getEnchantments(stack);
    }

    public static int enchantLevel(Enchantment enchantment, ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(enchantment, stack);
    }
}
