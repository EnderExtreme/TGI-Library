package com.rong.tgi.cofh.thermalexpansion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cofh.thermalexpansion.util.managers.device.FactorizerManager;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class FactorizerRecipes {
	
	public static void init(Iterator<IRecipe> registeredRecipes) {
        
        //for(IRecipe recipe : CraftingManager.REGISTRY) {
		while(registeredRecipes.hasNext()) {
			IRecipe recipe = registeredRecipes.next();
        	NonNullList<Ingredient> ingredients = recipe.getIngredients();
        	ItemStack outputStack = recipe.getRecipeOutput();
        	if(ingredients.size() == 9) {
        		if(ingredients.get(0).getMatchingStacks().length > 0 && !outputStack.isItemEqual(ItemStack.EMPTY)) {
        			 boolean match = true;
                     for(int i = 1; i < ingredients.size(); i++) {
                         if(ingredients.get(i).getMatchingStacks().length == 0 || 
                         		!ingredients.get(0).getMatchingStacks()[0].isItemEqual(ingredients.get(i).getMatchingStacks()[0])) {
                             match = false;
                             break;
                         }
                     }
                     if(match) {
                     	ModHandler.removeRecipeByName(recipe.getRegistryName());
                     	ItemStack outputStackForRemoval = ingredients.get(0).getMatchingStacks()[0];
                     	//RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2)
                     		//.inputs(CountableIngredient.from(outputStackForRemoval, ingredients.size()))
                     		//.outputs(outputStack)
                     		//.buildAndRegister();
                     	FactorizerManager.addRecipe(outputStackForRemoval, outputStack, false);  
                     	outputStackForRemoval.setCount(9);
                     	ModHandler.removeRecipes(outputStackForRemoval);
                     }	
        		}
        	}
        	if(ingredients.size() == 4) {
        		if(ingredients.get(0).getMatchingStacks().length > 0 && !outputStack.isItemEqual(ItemStack.EMPTY)) {
        			boolean match = true;
                    for(int i = 1; i < ingredients.size(); i++) {
                        if(ingredients.get(i).getMatchingStacks().length == 0 || 
                        		!ingredients.get(0).getMatchingStacks()[0].isItemEqual(ingredients.get(i).getMatchingStacks()[0])) {
                            match = false;
                            break;
                        }
                    }
                    if(match) {
                    	ModHandler.removeRecipeByName(recipe.getRegistryName());
                    	ItemStack outputStackForRemoval = ingredients.get(0).getMatchingStacks()[0];
                    	//RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2)
                 			//.inputs(CountableIngredient.from(outputStackForRemoval, ingredients.size()))
                 			//.outputs(outputStack)
                 			//.buildAndRegister();
                    	FactorizerManager.addRecipe(ingredients.get(0).getMatchingStacks()[0], outputStack, false);    
                    	outputStackForRemoval.setCount(4);
                     	ModHandler.removeRecipes(outputStackForRemoval);
                    }	
        		}	
        	}		
        }
	}
}
