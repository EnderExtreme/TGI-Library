package com.rong.tgi.reskillable.traits;

import com.rong.tgi.TGILibrary;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.unlockable.Trait;
import codersafterdark.reskillable.lib.LibMisc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class TraitPassiveMining extends Trait {
	
    public TraitPassiveMining() {
        super(new ResourceLocation(TGILibrary.MODID, "passive_mining"), 0, 3, new ResourceLocation(LibMisc.MOD_ID, "mining"), 0, "");
    }

    @Override
    public void getBreakSpeed(BreakSpeed event) {
        float editedSpeed = event.getNewSpeed();
        EntityPlayer player = event.getEntityPlayer();
        PlayerSkillInfo info = PlayerDataHandler.get(player).getSkillInfo(getParentSkill());
        editedSpeed += 0.05F * info.getLevel();
        event.setNewSpeed(editedSpeed);
    }
}