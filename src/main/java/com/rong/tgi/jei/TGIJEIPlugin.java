package com.rong.tgi.jei;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.rong.tgi.projecte.recipes.ShapedDMOreRecipe;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.translation.I18n;

public class TGIJEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {

        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        registry.handleRecipes(ShapedDMOreRecipe.class, recipe -> new DefaultRecipeWrapper(registry, recipe) {
                    @Override
                    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
                        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
                        IDrawable drawPhilo = registry.getJeiHelpers().getGuiHelper()
                                .createDrawableIngredient(new ItemStack(ObjHandler.philosStone));
                        IDrawable drawDarkPedestal = registry.getJeiHelpers().getGuiHelper()
                                .createDrawableIngredient(new ItemStack(ObjHandler.dmPedestal));
                        drawPhilo.draw(minecraft, 65, 35);
                        drawDarkPedestal.draw(minecraft, 65, 55);
                        minecraft.fontRenderer.drawString(I18n.translateToLocal("tgi.gui.emc") + recipe.getEMC(), 65, 0, 0x87ceeb);
                    }
         }, VanillaRecipeCategoryUid.CRAFTING);
    }

    private class DefaultRecipeWrapper implements IRecipeWrapper {
        
        private final IModRegistry registry;
        private final IRecipe recipe;
        private Consumer<ItemStack> stackModifier = stack -> {};

        public DefaultRecipeWrapper(IModRegistry registry, IRecipe recipe) {
            this.registry = registry;
            this.recipe = recipe;
        }

        DefaultRecipeWrapper modifyInputs(Consumer<ItemStack> stackModifier) {
            this.stackModifier = stackModifier;
            return this;
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            List<List<ItemStack>> inputLists = registry.getJeiHelpers().getStackHelper().expandRecipeItemStackInputs(recipe.getIngredients());
            inputLists.forEach(l -> l.forEach(stackModifier));
            ingredients.setInputLists(VanillaTypes.ITEM, inputLists);
            ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
        }
    }

}
