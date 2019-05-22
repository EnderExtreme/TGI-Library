package com.rong.tgi.vanilla;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.rong.tgi.TGILibrary;
import com.rong.tgi.vanilla.backport.swimming.RenderSwimmingPlayer;
import com.rong.tgi.vanilla.slabfix.ISlabGetter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class VanillaEventHandler {

	static Method setSize = ReflectionHelper.findMethod(Entity.class, "setSize", "func_70105_a", void.class,
			float.class, float.class);

	// This covers both sneaking + swimming!
	@SubscribeEvent
	public void onInputUpdate(InputUpdateEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		AxisAlignedBB axisAlignedBB = player.getEntityBoundingBox();
		axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ,
				axisAlignedBB.minX + 0.6, axisAlignedBB.minY + 1.8, axisAlignedBB.minZ + 0.6);
		if(!player.isSneaking() && isSneakingPose(player) && player.world.collidesWithAnyBlock(axisAlignedBB)) {
			event.getMovementInput().sneak = true;
			event.getMovementInput().moveStrafe = event.getMovementInput().moveStrafe * 0.3F;
			event.getMovementInput().moveForward = event.getMovementInput().moveForward * 0.3F;
		}
	}

	@SubscribeEvent
	public static void adjustSize(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if(player.isSneaking()) {
			player.height = 1.5F;
			player.width = 0.6F;
			player.eyeHeight = 1.27F;
			try {
				setSize.invoke(player, player.width, player.height);
			}
			catch(Exception e) {
				TGILibrary.logger.error("Unable to set entity size when sneaking!");
				e.printStackTrace();
			}
			AxisAlignedBB axisAlignedBB = player.getEntityBoundingBox();
			axisAlignedBB = new AxisAlignedBB(player.posX - player.width / 2.0D, axisAlignedBB.minY,
					player.posZ - player.width / 2.0D, player.posX + player.width / 2.0D,
					axisAlignedBB.minY + player.height, player.posZ + player.width / 2.0D);
			player.setEntityBoundingBox(axisAlignedBB);
		}
		else if(((player.isInWater()) && (player.isSprinting()))
				|| ((player.world.getBlockState(player.getPosition().up()).getMaterial() != Material.AIR)
						&& (player.world.getBlockState(player.getPosition().up()).getMaterial() != Material.WATER))) {
			player.height = 0.8F;
			player.width = 0.8F;
			player.eyeHeight = 0.6F;
			try {
				setSize.invoke(player, player.width, player.height);
			}
			catch(Exception e) {
				TGILibrary.logger.error("Unable to set entity size when swimming!");
				e.printStackTrace();
			}
			AxisAlignedBB axisAlignedBB = player.getEntityBoundingBox();

			axisAlignedBB = new AxisAlignedBB(player.posX - player.width / 2.0D, axisAlignedBB.minY,
					player.posZ - player.width / 2.0D, player.posX + player.width / 2.0D,
					axisAlignedBB.minY + player.height, player.posZ + player.width / 2.0D);

			player.setEntityBoundingBox(axisAlignedBB);
		}
		else {
			player.eyeHeight = player.getDefaultEyeHeight();
		}
	}

	@SubscribeEvent
	public static void onLivingPlayer(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entityLivingBase = event.getEntityLiving();
		if((entityLivingBase instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if((player.isInWater()) && (player.isSprinting())) {
				if(player.motionX < -0.4D) {
					player.motionX = -0.38999998569488525D;
				}
				if(player.motionX > 0.4D) {
					player.motionX = 0.38999998569488525D;
				}
				if(player.motionY < -0.4D) {
					player.motionY = -0.38999998569488525D;
				}
				if(player.motionY > 0.4D) {
					player.motionY = 0.38999998569488525D;
				}
				if(player.motionZ < -0.4D) {
					player.motionZ = -0.38999998569488525D;
				}
				if(player.motionZ > 0.4D) {
					player.motionZ = 0.38999998569488525D;
				}
				double d3 = player.getLookVec().y;
				double d4 = d3 < -0.2D ? 0.085D : 0.06D;
				if((d3 <= 0.0D) || (player.world
						.getBlockState(new BlockPos(player.posX, player.posY + 1.0D - 0.1D, player.posZ))
						.getMaterial() == Material.WATER)) {
					player.motionY += (d3 - player.motionY) * d4;
				}
				player.motionX *= 1.0049999952316284D;
				player.motionZ *= 1.0049999952316284D;

				player.move(MoverType.SELF, player.motionX, player.motionY, player.motionZ);
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onLivingRender(RenderPlayerEvent.Pre event) {
		World world = Minecraft.getMinecraft().world;
		EntityPlayer player = event.getEntityPlayer();
		ModelPlayer model = event.getRenderer().getMainModel();
		ResourceLocation skinLoc = DefaultPlayerSkin.getDefaultSkin(player.getPersistentID());
		boolean type = false;
		if(((player.isInWater()) && (player.isSprinting()))
				|| ((player.world.getBlockState(player.getPosition().up()).getMaterial() != Material.AIR)
						&& (player.world.getBlockState(player.getPosition().up()).getMaterial() != Material.WATER))) {
			event.setCanceled(true);
			if((Minecraft.getMinecraft().getRenderViewEntity() instanceof AbstractClientPlayer)) {
				AbstractClientPlayer client = (AbstractClientPlayer) Minecraft.getMinecraft().getRenderViewEntity();
				type = client.getSkinType().equals("slim");
			}
			RenderSwimmingPlayer sp = new RenderSwimmingPlayer(event.getRenderer().getRenderManager(), type);

			sp.doRender((AbstractClientPlayer) event.getEntity(), event.getX(), event.getY(), event.getZ(),
					((AbstractClientPlayer) event.getEntity()).rotationYaw, event.getPartialRenderTick());
		}
	}

	// Slab nonsense!
	private static final Map<Block, ISlabGetter> slabMap = new HashMap<Block, ISlabGetter>();

	@SubscribeEvent
	public static void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
		EntityPlayer player = event.getPlayer();
		if(!player.isSneaking()) return;
		if(event.getTarget().typeOfHit != RayTraceResult.Type.BLOCK) return;
		BlockPos pos = event.getTarget().getBlockPos();
		if(pos == null) return;
		IBlockState target = event.getPlayer().world.getBlockState(pos);
		ISlabGetter slabConverter = slabMap.get(target.getBlock());
		if(slabConverter != null) {
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			GlStateManager.glLineWidth(2f);
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(false);
			double offsetX = player.lastTickPosX
					+ (player.posX - player.lastTickPosX) * (double) event.getPartialTicks();
			double offsetY = player.lastTickPosY
					+ (player.posY - player.lastTickPosY) * (double) event.getPartialTicks();
			double offsetZ = player.lastTickPosZ
					+ (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks();
			AxisAlignedBB halfAABB = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1,
					pos.getY() + 0.5, pos.getZ() + 1);
			if(event.getTarget().hitVec.y - (double) pos.getY() > 0.5) {
				halfAABB = halfAABB.offset(0, 0.5, 0);
			}
			RenderGlobal.drawSelectionBoundingBox(halfAABB.grow(0.002).offset(-offsetX, -offsetY, -offsetZ), 0f, 0f, 0f,
					0.4f);
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onBreakBlock(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if(player instanceof FakePlayer) return;
		BlockPos pos = event.getPos();
		RayTraceResult mop = rayTrace(player, 6);
		Vec3d hitVec = mop != null ? mop.hitVec : null;
		if(hitVec != null) {
			hitVec = hitVec.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
		}
		if(player.isSneaking()) {
			IBlockState state = event.getState();
			ISlabGetter slabConverter = slabMap.get(state.getBlock());
			if(slabConverter == null || !slabConverter.isDoubleSlab(state)) return;
			IBlockState dropState = slabConverter.getSingleSlab(state, BlockSlab.EnumBlockHalf.BOTTOM);
			if(!event.getWorld().isRemote && player.canHarvestBlock(event.getState())
					&& !player.capabilities.isCreativeMode) {
				Item slabItem = Item.getItemFromBlock(dropState.getBlock());
				if(slabItem != Items.AIR) {
					spawnItem(new ItemStack(slabItem, 1, dropState.getBlock().damageDropped(dropState)),
							event.getWorld(), pos.getX(), pos.getY(), pos.getZ());
				}
			}
			IBlockState newState;
			if(hitVec != null && hitVec.y < 0.5f) {
				newState = slabConverter.getSingleSlab(state, BlockSlab.EnumBlockHalf.TOP);
			}
			else {
				newState = slabConverter.getSingleSlab(state, BlockSlab.EnumBlockHalf.BOTTOM);
			}
			event.getWorld().setBlockState(pos, newState, 1 | 2);
			event.setCanceled(true);
		}
	}

	private static void spawnItem(ItemStack stack, World world, float x, float y, float z) {
		Random rand = world.rand;
		float scale = 0.7f;
		double xOffset = rand.nextFloat() * scale + 1F - scale * 0.5F;
		double yOffset = rand.nextFloat() * scale + 1F - scale * 0.5F;
		double zOffset = rand.nextFloat() * scale + 1F - scale * 0.5F;
		EntityItem entityItem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, stack);
		entityItem.setPickupDelay(10);
		world.spawnEntity(entityItem);
	}

	private static RayTraceResult rayTrace(EntityLivingBase entity, double length) {
		Vec3d startPos = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
		Vec3d endPos = startPos.addVector(entity.getLookVec().x * length, entity.getLookVec().y * length,
				entity.getLookVec().z * length);
		return entity.world.rayTraceBlocks(startPos, endPos);
	}

	private boolean isSneakingPose(EntityPlayer player) {
		boolean flag = Math.abs(player.width - 0.6) < 0.01F;
		boolean flag1 = Math.abs(player.height - 1.5) < 0.01F;
		return flag && flag1;
	}

}
