package com.rong.tgi.enderio;

import java.util.Arrays;

import com.enderio.core.common.util.NNList;

import crazypants.enderio.base.recipe.IRecipeInput;
import crazypants.enderio.base.recipe.MachineRecipeInput;
import crazypants.enderio.base.recipe.Recipe;
import crazypants.enderio.base.recipe.RecipeBonusType;
import crazypants.enderio.base.recipe.RecipeOutput;
import crazypants.enderio.base.recipe.sagmill.SagMillRecipeManager;
import net.minecraft.item.ItemStack;

public class SAGMillRecipe extends Recipe {

    public SAGMillRecipe(IRecipeInput input, int energyRequired, RecipeBonusType bonusType, RecipeOutput[] output) {
        super(input, energyRequired, bonusType, output);
    }

    @Override
    public boolean isInputForRecipe(NNList<MachineRecipeInput> machineInputs) {
        return machineInputs.size() == 1 && getInputs()[0].isInput(machineInputs.get(0).item);
    }
}
