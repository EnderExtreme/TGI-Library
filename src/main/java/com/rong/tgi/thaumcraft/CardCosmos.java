package com.rong.tgi.thaumcraft;

import java.util.Random;
import java.util.TreeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerWarp.EnumWarpType;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.internal.IInternalMethodHandler;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.theorycraft.ResearchTableData;
import thaumcraft.api.research.theorycraft.TheorycraftCard;
import zmaster587.advancedRocketry.api.AdvancedRocketryBlocks;

public class CardCosmos extends TheorycraftCard {
    int md1;
    int md2;
    String cat = "CELESTIAL";

    public NBTTagCompound serialize() {
        NBTTagCompound nbt = super.serialize();
        nbt.setInteger("md1", this.md1);
        nbt.setInteger("md2", this.md2);
        nbt.setString("cat", this.cat);
        return nbt;
    }

    public void deserialize(NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.md1 = nbt.getInteger("md1");
        this.md2 = nbt.getInteger("md2");
        this.cat = nbt.getString("cat");
    }

    public String getResearchCategory() {
        return this.cat;
    }

    public boolean initialize(EntityPlayer player, ResearchTableData data) {
        if (!data.categoryTotals.isEmpty()) {
            if (ThaumcraftCapabilities.knowsResearch(player, new String[] { "CELESTIALSCANNING" })) {
            }
        } else {
            return false;
        }

        Random r = new Random(getSeed());
        this.md1 = MathHelper.getInt(r, 0, 12);
        this.md2 = this.md1;
        while (this.md1 == this.md2) {
            this.md2 = MathHelper.getInt(r, 0, 12);
        }
        int hVal = 0;
        String hKey = "";
        for (String category : data.categoryTotals.keySet()) {
            int q = data.getTotal(category);
            if (q > hVal) {
                hVal = q;
                hKey = category;
            }
        }
        this.cat = hKey;
        return this.cat != null;
    }

    public int getInspirationCost() {
        return 1;
    }

    public String getLocalizedName() {
        return new TextComponentTranslation("card.cosmos.name", new Object[0]).getFormattedText();
    }

    public String getLocalizedText() {
        return new TextComponentTranslation("card.cosmos.text", new Object[] { TextFormatting.BOLD + new TextComponentTranslation(new StringBuilder().append("tc.research_category.").append(this.cat).toString(), new Object[0]).getFormattedText() + TextFormatting.RESET }).getUnformattedText();
    }

    public ItemStack[] getRequiredItems() {
        return new ItemStack[] { new ItemStack(Item.getByNameOrId("astralsorcery:itemconstellationpaper")), new ItemStack(AdvancedRocketryBlocks.blockMoonTurf) };
    }

    public boolean[] getRequiredItemsConsumed() {
        return new boolean[] { true, true };
    }

    public boolean activate(EntityPlayer player, ResearchTableData data) {
        data.addTotal(getResearchCategory(), MathHelper.getInt(player.getRNG(), 25, 50));
        boolean rng1 = (this.md1 == 0) || (this.md2 == 0);
        boolean rng2 = (this.md1 > 4) || (this.md2 > 4);
        boolean rng3 = ((this.md1 > 0) && (this.md1 < 5)) || ((this.md2 > 0) && (this.md2 < 5));
        if (rng1) {
            int amt = MathHelper.getInt(player.getRNG(), 0, 5);
            data.addTotal("CELESTIAL", amt * 2);
            ThaumcraftApi.internalMethods.addWarpToPlayer(player, amt, EnumWarpType.TEMPORARY);
        }
        if (rng2) {
            data.penaltyStart += 1;
        }
        if (rng3) {
            data.bonusDraws += 1;
        }
        return true;
    }
}
