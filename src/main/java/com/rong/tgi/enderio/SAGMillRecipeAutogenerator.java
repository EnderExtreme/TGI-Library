package com.rong.tgi.enderio;

import java.util.Arrays;

import crazypants.enderio.base.recipe.IRecipe;
import crazypants.enderio.base.recipe.IRecipeInput;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeInput;
import crazypants.enderio.base.recipe.RecipeLevel;
import crazypants.enderio.base.recipe.RecipeOutput;
import crazypants.enderio.base.recipe.sagmill.SagMillRecipeManager;
import muramasa.gtu.api.materials.IMaterialFlag;
import muramasa.gtu.api.materials.MaterialType;
import net.minecraft.item.ItemStack;

public class SAGMillRecipeAutogenerator {

    /*
    public static void addRecipe() {

        MaterialType.ORE.getMats().forEach(material -> {
            if (material.has(MaterialType.GEM)) {
                IRecipe removeOre = SagMillRecipeManager.getInstance().getRecipeForInput(RecipeLevel.IGNORE, OreDictUnifier.get(OrePrefix.ore, m));
                IRecipe removeCrushed = SagMillRecipeManager.getInstance().getRecipeForInput(RecipeLevel.IGNORE, OreDictUnifier.get(OrePrefix.crushed, m));
                if (removeOre != null) {
                    SagMillRecipeManager.getInstance().getRecipes().remove(removeOre);
                }
                if (removeCrushed != null) {
                    SagMillRecipeManager.getInstance().getRecipes().remove(removeCrushed);
                }

                IRecipeInput gemRecipeInput = new RecipeInput(OreDictUnifier.get(OrePrefix.ore, m));
                RecipeOutput[] gemRecipeOutputs = { new RecipeOutput(OreDictUnifier.get(OrePrefix.crushed, m), 1F, 2.4F), new RecipeOutput(OreDictUnifier.get(OrePrefix.dustImpure, m), 0.3F, 0.5F) };
                SAGMillRecipe oreRecipe = new SAGMillRecipe(gemRecipeInput, 3800, RecipeBonusType.MULTIPLY_OUTPUT, gemRecipeOutputs);
                SagMillRecipeManager.getInstance().addRecipe(oreRecipe);

                IRecipeInput gemCrushedRecipeInput = new RecipeInput(OreDictUnifier.get(OrePrefix.dustImpure, m));
                RecipeOutput[] gemFromCrushedRecipeOutputs = { new RecipeOutput(OreDictUnifier.get(OrePrefix.gem, m), 1F, 3.7F), new RecipeOutput(OreDictUnifier.get(OrePrefix.dustTiny, m, 5), 0.25F, 1F) };
                SAGMillRecipe crushedRecipe = new SAGMillRecipe(gemCrushedRecipeInput, 3200, RecipeBonusType.MULTIPLY_OUTPUT, gemRecipeOutputs);
                SagMillRecipeManager.getInstance().addRecipe(crushedRecipe);
            }
        });

        for (Material m : Material.MATERIAL_REGISTRY) {
            if (m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
                if (m instanceof GemMaterial) {

                    IRecipe removeOre = SagMillRecipeManager.getInstance().getRecipeForInput(RecipeLevel.IGNORE, OreDictUnifier.get(OrePrefix.ore, m));
                    IRecipe removeCrushed = SagMillRecipeManager.getInstance().getRecipeForInput(RecipeLevel.IGNORE, OreDictUnifier.get(OrePrefix.crushed, m));
                    if (removeOre != null) {
                        SagMillRecipeManager.getInstance().getRecipes().remove(removeOre);
                    }
                    if (removeCrushed != null) {
                        SagMillRecipeManager.getInstance().getRecipes().remove(removeCrushed);
                    }

                    IRecipeInput gemRecipeInput = new RecipeInput(OreDictUnifier.get(OrePrefix.ore, m));
                    RecipeOutput[] gemRecipeOutputs = { new RecipeOutput(OreDictUnifier.get(OrePrefix.crushed, m), 1F, 2.4F), new RecipeOutput(OreDictUnifier.get(OrePrefix.dustImpure, m), 0.3F, 0.5F) };
                    SAGMillRecipe oreRecipe = new SAGMillRecipe(gemRecipeInput, 3800, RecipeBonusType.MULTIPLY_OUTPUT, gemRecipeOutputs);
                    SagMillRecipeManager.getInstance().addRecipe(oreRecipe);

                    IRecipeInput gemCrushedRecipeInput = new RecipeInput(OreDictUnifier.get(OrePrefix.dustImpure, m));
                    RecipeOutput[] gemFromCrushedRecipeOutputs = { new RecipeOutput(OreDictUnifier.get(OrePrefix.gem, m), 1F, 3.7F), new RecipeOutput(OreDictUnifier.get(OrePrefix.dustTiny, m, 5), 0.25F, 1F) };
                    SAGMillRecipe crushedRecipe = new SAGMillRecipe(gemCrushedRecipeInput, 3200, RecipeBonusType.MULTIPLY_OUTPUT, gemRecipeOutputs);
                    SagMillRecipeManager.getInstance().addRecipe(crushedRecipe);
                }

                IRecipe removeOre = SagMillRecipeManager.getInstance().getRecipeForInput(RecipeLevel.IGNORE, OreDictUnifier.get(OrePrefix.ore, m));
                IRecipe removeCrushed = SagMillRecipeManager.getInstance().getRecipeForInput(RecipeLevel.IGNORE, OreDictUnifier.get(OrePrefix.crushed, m));
                if (removeOre != null) {
                    SagMillRecipeManager.getInstance().getRecipes().remove(removeOre);
                }
                if (removeCrushed != null) {
                    SagMillRecipeManager.getInstance().getRecipes().remove(removeCrushed);
                }

                IRecipeInput oreRecipeInput = new RecipeInput(OreDictUnifier.get(OrePrefix.ore, m));
                DustMaterial byproductMaterial = GTUtility.selectItemInList(0, ((DustMaterial) m), ((DustMaterial) m).oreByProducts, DustMaterial.class);
                RecipeOutput[] oreRecipeOutputs = { new RecipeOutput(OreDictUnifier.get(OrePrefix.crushed, m), 0.5F, 0.8F), new RecipeOutput(OreDictUnifier.get(OrePrefix.crushed, byproductMaterial), 1.4F, 7.2F) };
                SAGMillRecipe oreRecipe = new SAGMillRecipe(oreRecipeInput, 8800, RecipeBonusType.NONE, oreRecipeOutputs);
                SagMillRecipeManager.getInstance().addRecipe(oreRecipe);

                IRecipeInput crushedRecipeInput = new RecipeInput(OreDictUnifier.get(OrePrefix.crushed, m));
                DustMaterial crusheByproductMaterial = GTUtility.selectItemInList(0, ((DustMaterial) m), ((DustMaterial) m).oreByProducts, DustMaterial.class);
                if (GTUtility.selectItemInList(1, ((DustMaterial) m), ((DustMaterial) m).oreByProducts, DustMaterial.class) != null) {
                    DustMaterial crushedTertiaryproductMaterial = GTUtility.selectItemInList(1, ((DustMaterial) m), ((DustMaterial) m).oreByProducts, DustMaterial.class);
                    RecipeOutput[] crushedRecipeOutputs = { new RecipeOutput(OreDictUnifier.get(OrePrefix.dustImpure, m), 0.5F, 0.8F), new RecipeOutput(OreDictUnifier.get(OrePrefix.dustImpure, byproductMaterial), 1.1F, 4.2F), new RecipeOutput(OreDictUnifier.get(OrePrefix.dustImpure, crushedTertiaryproductMaterial), 1.2F, 9.2F) };
                    SAGMillRecipe crushedRecipe = new SAGMillRecipe(crushedRecipeInput, 13500, RecipeBonusType.CHANCE_ONLY, crushedRecipeOutputs);
                    SagMillRecipeManager.getInstance().addRecipe(crushedRecipe);
                } else {
                    RecipeOutput[] crushedRecipeOutputs = { new RecipeOutput(OreDictUnifier.get(OrePrefix.dustImpure, m), 0.5F, 0.8F), new RecipeOutput(OreDictUnifier.get(OrePrefix.dustImpure, byproductMaterial), 1.4F, 7.2F) };
                    SAGMillRecipe crushedRecipe = new SAGMillRecipe(crushedRecipeInput, 13500, RecipeBonusType.CHANCE_ONLY, crushedRecipeOutputs);
                    SagMillRecipeManager.getInstance().addRecipe(crushedRecipe);
                }
            }
        }
    } */
}
