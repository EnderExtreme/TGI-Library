package com.rong.tgi.reskillable.traits;

import com.rong.tgi.TGILibrary;

import codersafterdark.reskillable.api.unlockable.Trait;
import codersafterdark.reskillable.lib.LibMisc;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.ItemFishedEvent;

public class TraitFishingLuck extends Trait {
	
    public TraitFishingLuck() {
        super(new ResourceLocation(TGILibrary.MODID, "luck_of_the_sea"), 0, 3, new ResourceLocation(LibMisc.MOD_ID, "gathering"), 0, "");
    }
    
    
    public void fishing(ItemFishedEvent event) {
    	
    }
}
    
    
