package com.rong.tgi.projecte.recipes;

import java.util.Iterator;

import javax.annotation.Nonnull;

import com.rong.tgi.Helper;
import com.rong.tgi.projecte.PEEventHandler;

import moze_intel.projecte.api.ProjectEAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedDMOreRecipe extends ShapedOreRecipe {
    
    protected int emc;
    
    public ShapedDMOreRecipe(ResourceLocation group, @Nonnull ItemStack result, int emc, Object... recipe) {
        super(group, result, recipe);
        this.emc = emc;
    }
    
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        if (canPlayerCraft(inv) && PEEventHandler.pedestalHasPS) return output;
        else return ItemStack.EMPTY;
    }
    
    public static void addRecipe(ResourceLocation path, ItemStack result, int emc, Object... data) {
        IRecipe recipe = new ShapedDMOreRecipe(null, result, emc, data).setRegistryName(path);
        ForgeRegistries.RECIPES.register(recipe);
    }
    
    public int getEMC() {
        return emc;
    }
    
    private boolean canPlayerCraft(InventoryCrafting inv) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            EntityPlayer player = Helper.getClientPlayer();
            if (player == null || player instanceof FakePlayer) return true;
            return player.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null).getEmc() >= emc;
        } else {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            if (server != null) {
                PlayerList manager = server.getPlayerList();
                if (manager != null) {
                    Container container = inv.eventHandler;
                    if (container == null) return false;
                    EntityPlayerMP playerToCheck = null;
                    Iterator playerIterator = manager.getPlayers().iterator();
                    while (playerIterator.hasNext()) {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) playerIterator.next();
                        if (entityPlayerMP.openContainer == container && container.canInteractWith(entityPlayerMP) && container.getCanCraft(entityPlayerMP)) {
                            if (playerToCheck != null) return false;
                            playerToCheck = entityPlayerMP;
                        }
                    }
                    if (playerToCheck != null) return playerToCheck.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null).getEmc() >= emc;
                }
            }
            return false;
        }
    }
}