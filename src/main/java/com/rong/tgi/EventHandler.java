package com.rong.tgi;

import java.util.concurrent.ThreadLocalRandom;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rong.tgi.dimension.BlockPortalToggleable;
import com.rong.tgi.temperature.handling.ITemperature;
import com.rong.tgi.temperature.handling.TemperatureProvider;
import com.rong.tgi.temperature.items.ItemCoolingPad;
import com.rong.tgi.temperature.items.ItemHeatingPad;
import com.rong.tgi.temperature.recipes.RecipesCoolingPad;
import com.rong.tgi.temperature.recipes.RecipesHeatingPad;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import scala.xml.Null;

@Mod.EventBusSubscriber
public class EventHandler {

    public static final ResourceLocation TEMPERATURE_PROVIDER = new ResourceLocation(TGILibrary.MODID, "temperature");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<EntityPlayer> event) {
        event.addCapability(TEMPERATURE_PROVIDER, new TemperatureProvider());
    }

    @SubscribeEvent
    public static void playerTemperatureCheck(TickEvent.PlayerTickEvent event) {
        ITemperature temperature = event.player.getCapability(TemperatureProvider.TEMPERATURE_CAPABILITY, null);
        ItemStack helmetSlot = event.player.inventory.armorInventory.get(0);
        ItemStack chestSlot = event.player.inventory.armorInventory.get(1);
        ItemStack pantsSlot = event.player.inventory.armorInventory.get(2);
        ItemStack bootsSlot = event.player.inventory.armorInventory.get(3);
        NBTTagCompound helmetTag = Helper.getStackNBTSafe(helmetSlot);
        NBTTagCompound chestTag = Helper.getStackNBTSafe(chestSlot);
        NBTTagCompound pantsTag = Helper.getStackNBTSafe(pantsSlot);
        NBTTagCompound bootsTag = Helper.getStackNBTSafe(bootsSlot);
        boolean doesArmorExist = !helmetSlot.isEmpty() && !chestSlot.isEmpty() && !pantsSlot.isEmpty() && !bootsSlot.isEmpty();
        boolean armorSetHasCooling = helmetTag.getBoolean("coolingPad") && chestTag.getBoolean("coolingPad") && pantsTag.getBoolean("coolingPad") && bootsTag.getBoolean("coolingPad");
        //boolean helmetSlotEmpty = event.player.
        BlockPos playerPos = event.player.getPosition();
        float biomeTemperature = event.player.world.getBiome(playerPos).getTemperature(playerPos);
        boolean isDefault = event.player.isCreative() && event.player.getIsInvulnerable();

        //Check if everything is done server-side, only if we implement something graphical we would need to check for client
        if(event.side == Side.SERVER) {
            //Default Warmth, either player in creative or is set invulnerable
            if(isDefault) {
				temperature.setTemperature(0.0F);
            }
            //Handling all cooling pad temp stuff here (Total points should be 80, helmet decreases 15, chestplate decreases 30, pants decreases 25 and boots decreases by 10
            if(!helmetSlot.isEmpty() && helmetTag.getBoolean("coolingPad")) {
                temperature.delTemperature(15.0F);
            }
            if(!chestSlot.isEmpty() && chestTag.getBoolean("coolingPad")) {
                temperature.delTemperature(30.0F);
            }
            if(!pantsSlot.isEmpty() && pantsTag.getBoolean("coolingPad")) {
                temperature.delTemperature(25.0F);
            }
            if(!bootsSlot.isEmpty() && bootsTag.getBoolean("coolingPad")) {
                temperature.delTemperature(10.0F);
            }
            //PlayerTemp >= 85 (without any cooling pads) TEMPERATURE#GETTEMPERATURE THROWS NULLPOINTEREXCEPTION
            if(temperature.getTemperature() >= 85.0F && doesArmorExist && !armorSetHasCooling) {
                event.player.setFire(5);
            }
            //Handles cold biomes (Anything below Extreme Hills temperature)
            if(!isDefault && biomeTemperature < 0.2F && biomeTemperature > -0.05F) {
            	//event.player.world.tick
                temperature.delTemperature(0.01F);
            }

        }
    }

    @SubscribeEvent
    public static void drinkMoreWater(LivingEntityUseItemEvent.Finish event) {
        if(event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            ITemperature temperature = player.getCapability(TemperatureProvider.TEMPERATURE_CAPABILITY, null);
            if(event.getResultStack().getItem() == Items.GLASS_BOTTLE) {
                temperature.delTemperature(2.0F);
            }
            if(event.getResultStack().getItem() instanceof ItemFood) {
                temperature.addTemperature(5.0F);
            }
        }
    }

    @SubscribeEvent
    public static void itemToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        NBTTagCompound nbt = Helper.getStackNBTSafe(stack);
        if(nbt.getBoolean("coolingPad") && stack.getItem() instanceof ItemArmor) {
            event.getToolTip().add(ChatFormatting.AQUA + "Actively Cooled");
        }
        if(nbt.getBoolean("heatingPad") && stack.getItem() instanceof ItemArmor) {
            event.getToolTip().add(ChatFormatting.RED + "Actively Heated");
        }
    }

	//Dimension Switching Mumbo-Jumbo
	@SubscribeEvent
	public static void playerInDimensionCheck(TickEvent.PlayerTickEvent event) {
		boolean doTeleport = false;
		if(event.side == Side.SERVER && event.phase == TickEvent.Phase.START && event.player.getEntityWorld().provider.getDimension() == 0) { doTeleport = !doTeleport; }
		if(doTeleport && event.player.getPosition().getY() <= /*Arbitrary number before testing with CC, should work seemlessly*/ 30) {
			for(int tries = 1; tries > 1; tries++) {
				BlockPos spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - ThreadLocalRandom.current().nextInt(16) * 2, ThreadLocalRandom.current().nextInt(10, 120), event.player.getPosition().getZ() / 8 - ThreadLocalRandom.current().nextInt(16) * 2);
				if(event.player.world.isAirBlock(spawnPos.down())) { continue; }
				else { System.out.print("took " + Integer.toString(tries) + " tries to find a valid spot for safe nether spawn!"); break; }
			}
			BlockPos spawnPos = new BlockPos(event.player.getPosition().getX() / 8 - ThreadLocalRandom.current().nextInt(16) * 2, ThreadLocalRandom.current().nextInt(10, 120), event.player.getPosition().getZ() / 8 - ThreadLocalRandom.current().nextInt(16) * 2);
			event.player.setPortal(spawnPos);
			if(event.player.changeDimension(-1) != null) {
				while(event.player.world.getBlockState(spawnPos).isNormalCube()){
					event.player.world.setBlockToAir(spawnPos);
					event.player.world.setBlockToAir(spawnPos.add(1, 0, 1));
					event.player.world.setBlockToAir(spawnPos.add(1, 1, 1));
					event.player.world.setBlockToAir(spawnPos.add(1, 2, 1));
					event.player.world.setBlockToAir(spawnPos.add(0, 0, 1));
					event.player.world.setBlockToAir(spawnPos.add(0, 1, 1));
					event.player.world.setBlockToAir(spawnPos.add(0, 2, 1));
					event.player.world.setBlockToAir(spawnPos.add(0, 1, 0));
					event.player.world.setBlockToAir(spawnPos.add(0, 2, 0));
					event.player.world.setBlockToAir(spawnPos.add(1, 0, 0));
					event.player.world.setBlockToAir(spawnPos.add(1, 1, 0));
					event.player.world.setBlockToAir(spawnPos.add(1, 2, 0));
				}
				event.player.setPositionAndUpdate(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
				event.player.world.setBlockState(spawnPos.add(3, 0, 0), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(3, 0, 1), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(3, 0, 0), Blocks.PORTAL.getDefaultState());
				event.player.world.setBlockState(spawnPos.add(3, 0, 1), Blocks.PORTAL.getDefaultState());
				event.player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 60, 1));
				event.player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60, 3));
				event.player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 60, 1));
				event.player.setFire(5);
			}
		}
	}

	//Needed for the dimension stuff, overrides vanilla portal block
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockPortalToggleable());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemCoolingPad().setCreativeTab(CreativeTabs.MATERIALS).setMaxStackSize(4).setUnlocalizedName(TGILibrary.MODID + ".coolingpad").setRegistryName("coolingpad"));
        event.getRegistry().register(new ItemHeatingPad().setCreativeTab(CreativeTabs.MATERIALS).setMaxStackSize(4).setUnlocalizedName(TGILibrary.MODID + ".heatingpad").setRegistryName("heatingpad"));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        registry.register(new RecipesCoolingPad().setRegistryName("COOLING_PAD_TO_ARMOR"));
        registry.register(new RecipesHeatingPad().setRegistryName("HEATING_PAD_TO_ARMOR"));
    }
}
