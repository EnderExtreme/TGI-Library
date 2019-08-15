package com.rong.tgi.projecte;

import com.rong.tgi.TGILibrary;
import com.rong.tgi.projecte.recipes.RecipeTest;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.gameObjs.tiles.DMPedestalTile;
import moze_intel.projecte.utils.Constants;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class PEEventHandler {

    public static boolean isSpecialGUIOpen = false;

    @SubscribeEvent
    public static void onRightClickingPedestalWithPS(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntityPlayer().isSneaking())
            return;
        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te != null && te instanceof DMPedestalTile) {
            DMPedestalTile tile = (DMPedestalTile) te;
            ItemStack stack = tile.getInventory().getStackInSlot(0);
            if (stack.getItem() == ObjHandler.philosStone) {
                if (!event.getWorld().isRemote) {
                    event.getEntityPlayer().openGui(PECore.instance, Constants.PHILOS_STONE_GUI, event.getWorld(), event.getHand() == EnumHand.MAIN_HAND ? 0 : 1, -1, -1);
                    isSpecialGUIOpen = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        registry.register(new RecipeTest().setRegistryName(TGILibrary.MODID + "testing_testing_123"));
    }

}
