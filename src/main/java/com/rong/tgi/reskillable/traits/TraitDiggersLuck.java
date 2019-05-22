package com.rong.tgi.reskillable.traits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.rong.tgi.TGILibrary;

import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.unlockable.Trait;
import codersafterdark.reskillable.lib.LibMisc;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import slimeknights.tconstruct.tools.tools.Shovel;

public class TraitDiggersLuck extends Trait {
	
	List<ItemStack> rare = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.GUNPOWDER)));
	List<ItemStack> epic = new ArrayList<ItemStack>();

	public TraitDiggersLuck() {
		super(new ResourceLocation(TGILibrary.MODID, "diggers_luck"), 0, 3, new ResourceLocation(LibMisc.MOD_ID, "gathering"), 5, "");
	}

	/**
	 * - Skill level affects what drops you will get
	 * - Fortunate affects how many drops you will get
	 */
	@Override
	public void onBlockDrops(HarvestDropsEvent event) {
		
		Random random = new Random();
		World world = event.getWorld();
		IBlockState state = event.getState();
		Block block = state.getBlock();
		BlockPos pos = event.getPos();
		Material material = state.getMaterial();
		EntityPlayer player = event.getHarvester();
		List<ItemStack> drops = event.getDrops();
		int amount = 1 + event.getFortuneLevel();
		int skillLevel = PlayerDataHandler.get(player).getSkillInfo(getParentSkill()).getLevel();
	    
		/**
		 * Accessible at 5, but uncommon is only gotten after 15, rare = 25, epic = max.
		 * Not sure if I should use isToolEffective or getMaterial to determine...
		 * Also, since we have no way to find out if mined blocks are natural or artificial we have to remove all drops
		 * (unless the roundabout way MCMMO does it where its sorta taxing)... 
		 */
		if(block.isToolEffective("shovel", state) && 
				(player.getHeldItemMainhand().getItem() instanceof ItemSpade || player.getHeldItemMainhand().getItem() instanceof Shovel)){			
			if(player.isPotionActive(MobEffects.LUCK)) {
				int rand = random.nextInt(64);
				if(rand == 0) {
					drops.clear();
					drops.add(OreDictUnifier.get(OrePrefix.gem, Materials.Alexandrite, amount));
				}
				else if(rand == 1) {
					drops.clear();
					drops.add(OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite, amount));
				}
				else if(rand == 2) {
					drops.clear();
					drops.add(OreDictUnifier.get(OrePrefix.gem, Materials.Spessartine, amount));
				}
				else if(rand == 3) {
					drops.clear();
					drops.add(OreDictUnifier.get(OrePrefix.gem, Materials.Ruby, amount));
				}
				else if(rand == 4) {
					drops.clear();
					drops.add(OreDictUnifier.get(OrePrefix.gem, Materials.Onyx, amount));
				}
			}
			if(skillLevel <= 15) {
				int rand = random.nextInt(30);
				if(rand == 0) {
					drops.clear();
					drops.add(new ItemStack(Items.FLINT, amount));
				}
				else if(rand == 1) {
					drops.clear();
					drops.add(new ItemStack(Items.BONE, amount));
				}				
				else if(rand == 2) {
					drops.clear();
					drops.add(new ItemStack(Items.ARROW, amount));
				}
				else if(rand == 3) {
					drops.clear();
					drops.add(new ItemStack(Items.DYE, amount, 3));
				}
			}
			if(skillLevel > 15 || skillLevel <= 25) {
				int rand = random.nextInt(40);
				if(rand == 0) {
					drops.clear();
					drops.add(new ItemStack(Items.SLIME_BALL, amount));
				}
				else if(rand == 1) {
					drops.clear();
					drops.add(new ItemStack(Items.APPLE, amount));
				}
				else if(rand == 2) {
					drops.clear();
					drops.add(new ItemStack(Items.PRISMARINE_SHARD, amount));
				}
				else if(rand == 3) {
					drops.clear();
					drops.add(new ItemStack(Blocks.BROWN_MUSHROOM, amount));
				}
				else if(rand == 4) {
					drops.clear();
					drops.add(new ItemStack(Blocks.RED_MUSHROOM, amount));
				}
			}
			if(skillLevel > 25 || skillLevel <= 32) {
				int rand = random.nextInt(50);
				if(rand == 0) {
					drops.clear();
					drops.add(new ItemStack(Items.GLOWSTONE_DUST, amount));
				}
				else if(rand == 1) {
					drops.clear();
					drops.add(new ItemStack(Items.GUNPOWDER, amount));
				}
			}
		}
	}
}