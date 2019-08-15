package com.rong.tgi.reskillable.traits;

import com.rong.tgi.TGILibrary;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.unlockable.Trait;
import codersafterdark.reskillable.lib.LibMisc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TraitPassiveDefense extends Trait {

    public TraitPassiveDefense() {
        super(new ResourceLocation(TGILibrary.MODID, "passive_defense"), 0, 3, new ResourceLocation(LibMisc.MOD_ID, "defense"), 0, "");
    }

    @Override
    public void onHurt(LivingHurtEvent event) {
        if (event.isCanceled() || !(event.getEntityLiving() instanceof EntityPlayer))
            return;

        float baseDamage = event.getAmount();
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        PlayerSkillInfo info = PlayerDataHandler.get(player).getSkillInfo(getParentSkill());
        baseDamage -= 0.05F * info.getLevel();
        event.setAmount(baseDamage);
    }
}
