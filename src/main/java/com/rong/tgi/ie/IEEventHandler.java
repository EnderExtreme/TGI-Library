package com.rong.tgi.ie;

import java.util.List;

import blusunrize.immersiveengineering.common.blocks.wooden.BlockWoodenDevice0;
import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class IEEventHandler {
	
	//Nerf IE Crates
	@SubscribeEvent
	public static void onBlockDrop(HarvestDropsEvent event) {
		if(!(event.getState().getBlock() instanceof BlockWoodenDevice0)) return;
		NBTTagList invTagList = null;
		List<ItemStack> drops = event.getDrops();
		for(ItemStack stack : drops) {
			if((stack.getItem() instanceof ItemBlock) && 
					((ItemBlock)stack.getItem()).getBlock() instanceof BlockWoodenDevice0 &&
					stack.hasTagCompound() && stack.getTagCompound().hasKey("inventory", 9)) {
				invTagList = stack.getTagCompound().getTagList("inventory", 10);
				stack.getTagCompound().removeTag("inventory");
				if(stack.getTagCompound().hasNoTags()) {
					stack.setTagCompound(null);
				}
		    }
		 }
		drops.addAll(Utils.readInventory(invTagList, 27));    
	}
	
}
