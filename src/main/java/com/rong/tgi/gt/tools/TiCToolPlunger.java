package com.rong.tgi.gt.tools;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.rong.tgi.CommonProxy;

import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.AoeToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;

public class TiCToolPlunger extends AoeToolCore {
	
	public TiCToolPlunger() {
	    this(PartMaterialType.handle(TinkerTools.toolRod),
	         PartMaterialType.head(CommonProxy.plungerHead));
	  }

	  protected TiCToolPlunger(PartMaterialType... requiredComponents) {
	    super(requiredComponents);

	    addCategory(Category.TOOL);
	    addCategory(Category.WEAPON);
	}

	@Override
	protected ToolNBT buildTagData(List<Material> materials) {
		
		HandleMaterialStats handle = materials.get(0).getStatsOrUnknown(MaterialTypes.HANDLE);
		HeadMaterialStats head = materials.get(1).getStatsOrUnknown(MaterialTypes.HEAD);

		ToolNBT data = new ToolNBT();
		data.head(head);
		data.handle(handle);

		data.harvestLevel = head.harvestLevel;

		return data;
	}

	@Override
	public double attackSpeed() {
		return 0.9F;
	}

	@Override
	public float damagePotential() {
		return 0.9F;
	}
	
	@Override
	  public float knockback() {
	    return 0.2f;
	}
	
	@Override
	  public float damageCutoff() {
	    return 12f;
	}
	
	@Override
	public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
		boolean hit = super.dealDamage(stack, player, entity, damage);
		if(hit || player.getEntityWorld().isRemote) {
			player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 2f, 1f);
	    }
	    return hit;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Nonnull
	@Override
	public ItemStack getContainerItem(@Nonnull ItemStack itemStack) {
		ItemStack stack = itemStack.copy();
		EntityPlayer player = null;
		ToolHelper.damageTool(stack, 4, player);
		//stack.setItemDamage(stack.getItemDamage() + 1);
		return stack;
}
	
	@Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity == null) {
            return EnumActionResult.PASS;
        }
        IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
        if(fluidHandler == null) {
            return EnumActionResult.PASS;
        }
        ItemStack toolStack = player.getHeldItem(hand);
        boolean isSneaking = player.isSneaking();
        IFluidHandler handlerToRemoveFrom = isSneaking ? (fluidHandler instanceof FluidHandlerProxy ? ((FluidHandlerProxy) fluidHandler).input : null) :
            (fluidHandler instanceof FluidHandlerProxy ? ((FluidHandlerProxy) fluidHandler).output : fluidHandler);
        if(handlerToRemoveFrom != null) {          
        	FluidStack drainStack = fluidHandler.drain(1000, true);
        	int amountOfFluid = drainStack == null ? 0 : drainStack.amount;
        	if(amountOfFluid > 0) {
        		ToolHelper.damageTool(toolStack, 10, player);
        		if(world.isRemote) {
        			player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0f, amountOfFluid / 1000.0f);
        		}
            }      	
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	tooltip.addAll(Arrays.asList(I18n.format("behavior.plunger.description").split("/n")));
    }
}
