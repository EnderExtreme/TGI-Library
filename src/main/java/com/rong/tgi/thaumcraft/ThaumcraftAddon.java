package com.rong.tgi.thaumcraft;

import org.apache.commons.lang3.StringUtils;

import com.rong.tgi.TGILibrary;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.api.research.theorycraft.TheorycraftManager;
import thaumcraft.common.config.ConfigAspects;
import thaumcraft.common.lib.crafting.DustTriggerOre;
import thaumcraft.common.lib.crafting.DustTriggerSimple;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.utils.Utils;

public class ThaumcraftAddon {
    
    private static final ResourceLocation _NULL_ = new ResourceLocation("");
    public static Item nightmareBook = Item.getByNameOrId("grimoireofgaia:weapon_book_nightmare");

    public static Aspect SPACE = new Aspect("Cosmos", 8388736, new Aspect[] { Aspect.FLIGHT, Aspect.DARKNESS }, new ResourceLocation(TGILibrary.MODID, "textures/aspects/cosmos.png"), 1);

    public static void initAspects() {

        ThaumcraftApi.registerObjectTag("turfMoon", new AspectList().add(SPACE, 10));
        appendAspects(new ItemStack(Blocks.END_STONE), new AspectList().add(SPACE, 5));
        appendAspects(new ItemStack(Blocks.END_BRICKS), new AspectList().add(SPACE, 3));
        appendAspects(new ItemStack(Blocks.END_ROD), new AspectList().add(SPACE, 2));
        appendAspects(new ItemStack(Blocks.END_BRICKS), new AspectList().add(SPACE, 3));
    }

    public static void initCards() {
        TheorycraftManager.registerCard(CardCosmos.class);
    }

    public static void initTheoryCraft() {
        TheorycraftManager.registerAid(new AidBasicCelestial());
    }

    public static void initCategories() {
        ResourceLocation BACK_OVER = new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png");
        ResearchCategories.registerCategory("CELESTIAL", "BASEELDRITCH", new AspectList(), new ResourceLocation("astralsorcery", "textures/items/looking_glass.png"), new ResourceLocation(TGILibrary.MODID, "textures/gui/celestial_back.png"), BACK_OVER);
    }

    public static void initResearches() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(TGILibrary.MODID, "research/astral"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(TGILibrary.MODID, "research/general"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(TGILibrary.MODID, "research/portalgun"));
    }

    public static void initRecipes() {
        IDustTrigger.registerDustTrigger(new DustTriggerOre("MANAORE", "oreIron", new ItemStack(Item.getByNameOrId("thermalfoundation:ore"), 1, 8)));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_0"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreStoneIron", new AspectList().add(Aspect.ALCHEMY, 2)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_1"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreSandstoneIron", new AspectList().add(Aspect.ALCHEMY, 2)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_2"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 2, 136), "oreNetherrackIron", new AspectList().add(Aspect.ALCHEMY, 3)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_3"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 3, 136), "oreEndstoneIron", new AspectList().add(Aspect.ALCHEMY, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "mana_ore_4"), new CrucibleRecipe("MANAORE", new ItemStack(Item.getByNameOrId("thermalfoundation:material"), 1, 136), "oreGravelIron", new AspectList().add(Aspect.ALCHEMY, 2)));

        //Subject to change, may add potions to this
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(TGILibrary.MODID, "imbued_marble"), new CrucibleRecipe("IMBUEDMARBLE", new ItemStack(Item.getByNameOrId("astralsorcery:blockmarble")), "stoneMarble", new AspectList().add(Aspect.MAGIC, 8).add(Aspect.ALCHEMY, 2)));      

        //PortalGun stuff
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(TGILibrary.MODID, "miniature_black_hole"), new InfusionRecipe("MINIATURE_BLACK_HOLE", new ItemStack(Item.getByNameOrId("portalgun:item_miniature_black_hole")), 4, new AspectList().add(Aspect.VOID, 120).add(Aspect.DARKNESS, 30), new ItemStack(Items.NETHER_STAR), new Object[] { "bEnderAirBottle", "bEnderAirBottle", "bEnderAirBottle", "bEnderAirBottle" }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(TGILibrary.MODID, "portal_gun"), new InfusionRecipe("PORTALGUN", new ItemStack(Item.getByNameOrId("portalgun:item_portalgun"), 1, 0), 6, new AspectList().add(Aspect.CRAFT, 25).add(Aspect.VOID, 20).add(Aspect.ELDRITCH, 20).add(Aspect.MAGIC, 30), new ItemStack(Item.getByNameOrId("portalgun:item_miniature_black_hole")), new Object[] { "blockObsidian", "plateDenseStainlessSteel", "plateGold", new ItemStack(Items.ENDER_PEARL) }));

        //Rustic stuff
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(TGILibrary.MODID, "alchemic_condenser"), new ShapedArcaneRecipe(_NULL_, "ALCHEMICCONDENSER", 100, new AspectList() .add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1), new ItemStack(Item.getByNameOrId("rustic:condenser")), new Object[] { " F ", "RBR", "RCR", 'F', "ingotBrick", 'B', new ItemStack(Item.getByNameOrId("pyrotech:tank"), 1, 1), 'C', "moonstone", 'F', new ItemStack(Item.getByNameOrId("pyrotech:material"), 1, 5)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(TGILibrary.MODID, "advanced_alchemic_condenser"), new ShapedArcaneRecipe(_NULL_, "ADVANCEDALCHEMICCONDENSER", 200, new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1), new ItemStack(Item.getByNameOrId("rustic:condenser_advanced")), new Object[] { " F ", "FBF", "FCF", 'F', "ingotBrickNetherGlazed", 'B', new ItemStack(BlocksTC.jarNormal), 'C', Item.getByNameOrId("enderio:block_reinforced_obsidian") }));

        //Progression stuff
        //Use custom book as catalyst!
        //ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(TGILibrary.MODID, "nightmare_midnight_book"), new InfusionRecipe(_NULL_, "NIGHTMARE", new ItemStack(nightmareBook), 2, new AspectList().add(Aspect.VOID, 10).add(Aspect.ELDRITCH, 10).add(Aspect.DESIRE, 20).add(Aspect.MIND, 20));
    }

    private static void appendAspects(ItemStack stack, AspectList toAdd) {
        toAdd = toAdd.copy();
        {
            AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
            if (al != null)
                toAdd = toAdd.add(al);
        }
        CommonInternals.objectTags.put(CommonInternals.generateUniqueItemstackId(stack), toAdd);
    }
}