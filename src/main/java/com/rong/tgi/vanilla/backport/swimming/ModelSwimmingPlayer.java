package com.rong.tgi.vanilla.backport.swimming;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//Thanks Bagu!
@SideOnly(Side.CLIENT)
public class ModelSwimmingPlayer extends ModelPlayer {

	public ModelSwimmingPlayer(float modelSize, boolean smallArmsIn) {
		super(modelSize, smallArmsIn);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		this.bipedHead.rotateAngleX = -0.7853982F;
		this.bipedHead.rotateAngleY = (netHeadYaw * 0.017453292F);

		float f = 1.0F;

		f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY
				+ entityIn.motionZ * entityIn.motionZ);
		f /= 0.2F;
		f = f * f * f;
		if(f < 1.0F) {
			f = 1.0F;
		}
		this.bipedRightArm.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount
				* 0.5F / f);
		this.bipedLeftArm.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f);
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightLeg.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f);
		this.bipedLeftLeg.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount
				/ f);

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}
}
