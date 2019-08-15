package com.rong.tgi.thaumcraft;

import net.minecraft.block.Block;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.research.theorycraft.ITheorycraftAid;
import thaumcraft.api.research.theorycraft.TheorycraftCard;
import thaumcraft.common.lib.research.theorycraft.CardCelestial;

public class AidBasicCelestial implements ITheorycraftAid {

    public Object getAidObject() {
        return Block.getBlockFromName("astralsorcery:blockmarble");
    }

    public Class<TheorycraftCard>[] getCards() {
        return new Class[] { CardCelestial.class, CardCosmos.class };
    }
}
