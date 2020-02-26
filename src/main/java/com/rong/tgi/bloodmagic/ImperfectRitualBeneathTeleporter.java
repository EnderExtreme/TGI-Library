package com.rong.tgi.bloodmagic;

import com.rong.tgi.TGILibrary;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.ritual.RitualRegister;
import WayofTime.bloodmagic.ritual.imperfect.IImperfectRitualStone;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitual;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

@RitualRegister.Imperfect("beneath_teleporter")
public class ImperfectRitualBeneathTeleporter extends ImperfectRitual {

    public ImperfectRitualBeneathTeleporter() {
        super("beneath_teleporter", s -> s.getBlock() == Blocks.BEDROCK, 25000,
                "ritual." + TGILibrary.MODID + ".imperfect.beneath_teleporter");
    }

    @Override
    public boolean onActivate(IImperfectRitualStone imperfectRitualStone, EntityPlayer player) {
        World world = imperfectRitualStone.getRitualWorld();
        BlockPos pos = imperfectRitualStone.getRitualPos().up();
        IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
        SoulNetwork network = NetworkHelper.getSoulNetwork(player);
        if (world.canBlockSeeSky(pos) && knowledge.isResearchComplete("UNLOCKAUROMANCY")
                && network.getCurrentEssence() >= this.getActivationCost()) {
            world.setBlockToAir(pos.up());
            world.setBlockState(pos.up(), Block.getBlockFromName("beneath:teleporterbeneath").getDefaultState());
            return true;
        } else return false;
    }
}
