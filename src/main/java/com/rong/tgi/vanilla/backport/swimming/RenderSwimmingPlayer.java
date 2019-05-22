package com.rong.tgi.vanilla.backport.swimming;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSwimmingPlayer extends RenderLivingBase<AbstractClientPlayer> {

	private final boolean smallArms;

	public RenderSwimmingPlayer(RenderManager renderManager) {
		this(renderManager, false);
	}

	public RenderSwimmingPlayer(RenderManager renderManager, boolean useSmallArms) {
		super(renderManager, new ModelSwimmingPlayer(0.0F, useSmallArms), 0.5F);
		this.smallArms = useSmallArms;
		addLayer(new LayerBipedArmor(this));
		addLayer(new LayerHeldItem(this));
		addLayer(new LayerArrow(this));
		addLayer(new LayerCustomHead(getMainModel().bipedHead));
		addLayer(new LayerElytra(this));
		addLayer(new LayerEntityOnShoulder(renderManager));
	}

	public ModelSwimmingPlayer getMainModel() {
		return (ModelSwimmingPlayer)super.getMainModel();
	}

	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		if((!entity.isUser()) || (this.renderManager.renderViewEntity == entity)) {
			double d0 = y;
			if(entity.isSneaking()) {
				d0 = y - 0.125D;
			}
			setModelVisibilities(entity);
			GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
			super.doRender(entity, x, d0, z, entityYaw, partialTicks);
			GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
		}
	}

	private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
		ModelSwimmingPlayer modelplayer = getMainModel();
		if(clientPlayer.isSpectator()) {
			modelplayer.setVisible(false);
			modelplayer.bipedHead.showModel = true;
			modelplayer.bipedHeadwear.showModel = true;
		}
		else {
			ItemStack itemstack = clientPlayer.getHeldItemMainhand();
			ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
			modelplayer.setVisible(true);
			modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
			modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
			modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
			modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
			modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
			modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
			modelplayer.isSneak = clientPlayer.isSneaking();
			ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
			ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;
			if(!itemstack.isEmpty()) {
				modelbiped$armpose = ModelBiped.ArmPose.ITEM;
				if(clientPlayer.getItemInUseCount() > 0) {
					EnumAction enumaction = itemstack.getItemUseAction();
					if(enumaction == EnumAction.BLOCK) {
						modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
					}
					else if(enumaction == EnumAction.BOW) {
						modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}
			if(!itemstack1.isEmpty()) {
				modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;
				if(clientPlayer.getItemInUseCount() > 0) {
					EnumAction enumaction1 = itemstack1.getItemUseAction();
					if(enumaction1 == EnumAction.BLOCK) {
						modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
					}
					else if(enumaction1 == EnumAction.BOW) {
						modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}
			if(clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT) {
				modelplayer.rightArmPose = modelbiped$armpose;
				modelplayer.leftArmPose = modelbiped$armpose1;
			}
			else {
				modelplayer.rightArmPose = modelbiped$armpose1;
				modelplayer.leftArmPose = modelbiped$armpose;
			}
		}
	}

	public ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
		return entity.getLocationSkin();
	}

	public void transformHeldFull3DItemLayer() {
		GlStateManager.translate(0.0F, 0.1875F, 0.0F);
	}

	protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
		float f = 0.9375F;
		GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
	}

	protected void renderEntityName(AbstractClientPlayer entityIn, double x, double y, double z, String name,
			double distanceSq) {
		if(distanceSq < 100.0D) {
			Scoreboard scoreboard = entityIn.getWorldScoreboard();
			ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
			if(scoreobjective != null) {
				Score score = scoreboard.getOrCreateScore(entityIn.getName(), scoreobjective);
				renderLivingLabel(entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z,
						64);
				y += getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * 0.025F;
			}
		}
		super.renderEntityName(entityIn, x, y, z, name, distanceSq);
	}

	public void renderRightArm(AbstractClientPlayer clientPlayer) {
		float f = 1.0F;
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		float f1 = 0.0625F;
		ModelSwimmingPlayer modelplayer = getMainModel();
		setModelVisibilities(clientPlayer);
		GlStateManager.enableBlend();
		modelplayer.swingProgress = 0.0F;
		modelplayer.isSneak = false;
		modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
		modelplayer.bipedRightArm.rotateAngleX = 0.0F;
		modelplayer.bipedRightArm.render(0.0625F);
		modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
		modelplayer.bipedRightArmwear.render(0.0625F);
		GlStateManager.disableBlend();
	}

	public void renderLeftArm(AbstractClientPlayer clientPlayer) {
		float f = 1.0F;
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		float f1 = 0.0625F;
		ModelSwimmingPlayer modelplayer = getMainModel();
		setModelVisibilities(clientPlayer);
		GlStateManager.enableBlend();
		modelplayer.isSneak = false;
		modelplayer.swingProgress = 0.0F;
		modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
		modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
		modelplayer.bipedLeftArm.render(0.0625F);
		modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
		modelplayer.bipedLeftArmwear.render(0.0625F);
		GlStateManager.disableBlend();
	}

	protected void renderLivingAt(AbstractClientPlayer entityLivingBaseIn, double x, double y, double z) {
		if((entityLivingBaseIn.isEntityAlive()) && (entityLivingBaseIn.isPlayerSleeping())) {
			super.renderLivingAt(entityLivingBaseIn, x + entityLivingBaseIn.renderOffsetX,
					y + entityLivingBaseIn.renderOffsetY, z + entityLivingBaseIn.renderOffsetZ);
		}
		else {
			super.renderLivingAt(entityLivingBaseIn, x, y, z);
		}
	}

	public void applyRotations(AbstractClientPlayer entityLiving, float p_77043_2_, float rotationYaw,
			float partialTicks) {
		if((entityLiving.isEntityAlive()) && (entityLiving.isPlayerSleeping())) {
			GlStateManager.rotate(entityLiving.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
		}
		else if(entityLiving.isElytraFlying()) {
			super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
			float f = entityLiving.getTicksElytraFlying() + partialTicks;
			float f1 = MathHelper.clamp(f * f / 100.0F, 0.0F, 1.0F);
			GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
			Vec3d vec3d = entityLiving.getLook(partialTicks);
			double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
			double d1 = vec3d.x * vec3d.x + vec3d.z * vec3d.z;
			if((d0 > 0.0D) && (d1 > 0.0D)) {
				double d2 = (entityLiving.motionX * vec3d.x + entityLiving.motionZ * vec3d.z)
						/ (Math.sqrt(d0) * Math.sqrt(d1));
				double d3 = entityLiving.motionX * vec3d.z - entityLiving.motionZ * vec3d.x;
				GlStateManager.rotate((float) (Math.signum(d3) * Math.acos(d2)) * 180.0F / 3.1415927F, 0.0F, 1.0F,
						0.0F);
			}
		}
		else {
			super.applyRotations(entityLiving, partialTicks, rotationYaw, partialTicks);

			float f3 = lerp(entityLiving.rotationPitch, -90.0F - entityLiving.rotationPitch, 1.0F);

			GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);

			GlStateManager.translate(0.0F, -1.0F, 0.3F);
		}
	}

	//derp
	private float lerp(float p_205126_1_, float p_205126_2_, float p_205126_3_) {
		return p_205126_1_ + (p_205126_2_ - p_205126_1_) * p_205126_3_;
	}
}
