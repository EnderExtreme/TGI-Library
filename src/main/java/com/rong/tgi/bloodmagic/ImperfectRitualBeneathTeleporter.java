package com.rong.tgi.bloodmagic;

import com.rong.tgi.TGILibrary;

import WayofTime.bloodmagic.BloodMagic;
import WayofTime.bloodmagic.ritual.RitualRegister;
import WayofTime.bloodmagic.ritual.imperfect.IImperfectRitualStone;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitual;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

@RitualRegister.Imperfect("beneath_teleporter")
public class ImperfectRitualBeneathTeleporter extends ImperfectRitual {
	
    public ImperfectRitualBeneathTeleporter() {
        super("beneath_teleporter", s -> s.getBlock() == Blocks.BEDROCK, 25000, "ritual." + TGILibrary.MODID + ".imperfect.beneath_teleporter");
    }

    @Override
    public boolean onActivate(IImperfectRitualStone imperfectRitualStone, EntityPlayer player) {
    	World world = imperfectRitualStone.getRitualWorld();
    	BlockPos pos = imperfectRitualStone.getRitualPos().up();
    	IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
    	if(world.canBlockSeeSky(pos) && knowledge.isResearchKnown("m_deepdown"))
    		world.setBlockState(pos.up(), Block.getBlockFromName("beneath:teleporterbeneath").getDefaultState(), 3);
        return true;
    }
}
