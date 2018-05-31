package com.rong.tgi.dimension;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class TeleportToNetherEventHandler {
	
	private BlockPos pos;
	
	@SubscribeEvent
    public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
        	EntityPlayer player = (EntityPlayer) event.getEntity();
        	if(player.getPosition().getY() < 10 && player.dimension != -1) {
            	for(int obsSlot = 0; obsSlot < player.getInventoryEnderChest().getSizeInventory(); obsSlot++) {
            		ItemStack requiredStack = player.getInventoryEnderChest().getStackInSlot(obsSlot);
            		if(requiredStack != null && ItemStack.areItemsEqual(requiredStack, new ItemStack(Blocks.OBSIDIAN))) {
            			if(player.inventory.hasItemStack(new ItemStack(Items.DIAMOND)) && player.inventory.hasItemStack(new ItemStack(Items.LAVA_BUCKET))) {
            				player.inventory.decrStackSize(player.inventory.getSlotFor(new ItemStack(Items.DIAMOND)), 1);
            				player.inventory.decrStackSize(player.inventory.getSlotFor(new ItemStack(Items.LAVA_BUCKET)), 1);
            				player.getInventoryEnderChest().decrStackSize(obsSlot, 8);
            			//player.inventory.decrStackSize(diaSlot, 2);
            			//player.inventory.decrStackSize(lavaSlot, 1);
            				EntityPlayerMP thePlayer = (EntityPlayerMP) player;
            				if(!ForgeHooks.onTravelToDimension(thePlayer, 0)) return;
            				thePlayer.timeUntilPortal = 10;
            				thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, -1, new YLevelTeleporter(thePlayer.mcServer.getWorld(0)));
            			//player.changeDimension(-1);
            			}
            		} 
            	}
            }
        }
	}
}
