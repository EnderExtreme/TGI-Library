package com.rong.tgi.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.IScanThing;
import thaumcraft.common.lib.utils.InventoryUtils;

public class ScanAstralSky implements IScanThing {

	public boolean checkThing(EntityPlayer player, Object obj) {
		if(!(obj == null) && player.world.provider.getDimension() == 1) {
			if(ThaumcraftCapabilities.knowsResearchStrict(player, new String[] { "BASEELDRITCH" })) {}
			if(player.rotationPitch == 180F) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public void onSuccess(EntityPlayer player, Object obj) {
		if(!(obj == null) && player.world.provider.getDimension() == 1) {
			if(player.rotationPitch == 180F) {
				if ((InventoryUtils.isPlayerCarryingAmount(player, new ItemStack(ItemsTC.scribingTools, 1, 32767), true)) && (InventoryUtils.consumePlayerItem(player, new ItemStack(Items.PAPER), false, true))) {
					ItemStack stack = new ItemStack(Item.getByNameOrId("astralsorcery:itemconstellationpaper"));
					if (!player.inventory.addItemStackToInventory(stack)) {
				          player.dropItem(stack, false);
				    }
					ThaumcraftApi.internalMethods.progressResearch(player, "CONSTELLATIONPAPER");
				}
			}
			else {
			}
		}
		else {
		}
	}

	public String getResearchKey(EntityPlayer player, Object obj) {
		return "";
	}
}
