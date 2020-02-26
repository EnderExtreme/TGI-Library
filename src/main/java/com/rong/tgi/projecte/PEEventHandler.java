package com.rong.tgi.projecte;

import com.rong.tgi.Helper;
import com.rong.tgi.TGILibrary;
import com.rong.tgi.projecte.recipes.ShapedDMOreRecipe;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.gameObjs.tiles.DMPedestalTile;
import moze_intel.projecte.utils.Constants;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.items.ItemsTC;

@EventBusSubscriber
public class PEEventHandler {

    public static boolean pedestalHasPS = false;

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntityPlayer().isSneaking()) return;
        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te != null && te instanceof DMPedestalTile) {
            DMPedestalTile tile = (DMPedestalTile) te;
            ItemStack stack = tile.getInventory().getStackInSlot(0);
            if (stack.getItem() == ObjHandler.philosStone) {
                World world = event.getWorld();
                if (!world.isRemote) {
                    event.getEntityPlayer().openGui(PECore.instance, Constants.PHILOS_STONE_GUI, world,
                            event.getHand() == EnumHand.MAIN_HAND ? 0 : 1, -1, -1);
                    pedestalHasPS = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        ShapedDMOreRecipe.addRecipe(new ResourceLocation(TGILibrary.MODID, "magic_crystal"), Helper.makeStack("ebwizardry:crystal_block", 1), 512,
                "XXX", "XAX", "XXX", 'X', ItemsTC.crystalEssence, 'A', Helper.makeStack("midnight:trenchstone", 0));
    }

}
