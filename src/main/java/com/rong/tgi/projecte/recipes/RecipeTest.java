package com.rong.tgi.projecte.recipes;

import java.util.Iterator;

import com.rong.tgi.Helper;
import com.rong.tgi.projecte.PEEventHandler;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeTest extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean clear = false;
		for(int slot = 0; slot < inv.getSizeInventory(); slot++) {
			if(inv.getStackInSlot(slot).isItemEqual(new ItemStack(ObjHandler.interdictionTorch))) {
				clear = true;
			}
		}
		return clear;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		if(!PEEventHandler.isSpecialGUIOpen) return ItemStack.EMPTY;
		if(!canPlayerCraft(inv)) return ItemStack.EMPTY;
		return OreDictUnifier.get(OrePrefix.ingot, Materials.SolderingAlloy, 64);
	}

	@Override
	public boolean canFit(int width, int height) {
		return width == 3 && height == 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 5);
	}
	
	private boolean canPlayerCraft(InventoryCrafting inv) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            EntityPlayer player = Helper.getClientPlayer();
            if(player == null || player instanceof FakePlayer) {
                return true;
            }
            return player.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null).getEmc() > 4096;
        } 
		else {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            if(server != null) {
                PlayerList manager = server.getPlayerList();
                if(manager != null) {
                    Container container = inv.eventHandler;
                    if(container == null) {
                        return false;
                    }
                    EntityPlayerMP playerToCheck = null;
                    Iterator playerIterator = manager.getPlayers().iterator();
                    while(playerIterator.hasNext()) {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP)playerIterator.next();
                        if(entityPlayerMP.openContainer == container && 
                        		container.canInteractWith(entityPlayerMP) && container.getCanCraft(entityPlayerMP)) {
                            if(playerToCheck != null) {
                                return false;
                            }        
                            playerToCheck = entityPlayerMP;
                        }
                    }
                    if(playerToCheck != null) {
                    	return playerToCheck.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null).getEmc() > 4096;
                    }
                }
            }            
            return false;
        }
	}
}
