package com.rong.tgi.gt;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.OrientedOverlayRenderer.OverlayFace;
import gregtech.api.render.SimpleCubeRenderer;
import net.minecraft.util.ResourceLocation;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;

import com.rong.tgi.TGILibrary;
import com.rong.tgi.gt.multiblocks.TileEntityFusionReactor;

import gregtech.api.GregTechAPI;
import gregtech.api.gui.GuiTextures;

public class GTMTEs {
	
	public static SimpleMachineMetaTileEntity[] MASS_FABRICATOR = new SimpleMachineMetaTileEntity[4];
	public static SimpleMachineMetaTileEntity[] REPLICATOR = new SimpleMachineMetaTileEntity[4];
	public static TileEntityFusionReactor[] FUSION_REACTOR = new TileEntityFusionReactor[2];
	
	public static void init() {
		
		MASS_FABRICATOR[0] = GregTechAPI.registerMetaTileEntity(2175, new SimpleMachineMetaTileEntity(id("mass_fabricator.lv"), MTERecipes.MASS_FABRICATOR_RECIPES, MTETextures.MASS_FABRICATOR_OVERLAY, 1));
		MASS_FABRICATOR[1] = GregTechAPI.registerMetaTileEntity(2176, new SimpleMachineMetaTileEntity(id("mass_fabricator.mv"), MTERecipes.MASS_FABRICATOR_RECIPES, MTETextures.MASS_FABRICATOR_OVERLAY, 2));
		MASS_FABRICATOR[2] = GregTechAPI.registerMetaTileEntity(2177, new SimpleMachineMetaTileEntity(id("mass_fabricator.hv"), MTERecipes.MASS_FABRICATOR_RECIPES, MTETextures.MASS_FABRICATOR_OVERLAY, 3));
		MASS_FABRICATOR[3] = GregTechAPI.registerMetaTileEntity(2178, new SimpleMachineMetaTileEntity(id("mass_fabricator.ev"), MTERecipes.MASS_FABRICATOR_RECIPES, MTETextures.MASS_FABRICATOR_OVERLAY, 4));
		
		REPLICATOR[0] = GregTechAPI.registerMetaTileEntity(2179, new SimpleMachineMetaTileEntity(id("replicator.lv"), MTERecipes.REPLICATOR_RECIPES, MTETextures.REPLICATOR_OVERLAY, 1));
		REPLICATOR[1] = GregTechAPI.registerMetaTileEntity(2180, new SimpleMachineMetaTileEntity(id("replicator.mv"), MTERecipes.REPLICATOR_RECIPES, MTETextures.REPLICATOR_OVERLAY, 2));
        REPLICATOR[2] = GregTechAPI.registerMetaTileEntity(2181, new SimpleMachineMetaTileEntity(id("replicator.hv"), MTERecipes.REPLICATOR_RECIPES, MTETextures.REPLICATOR_OVERLAY, 3));
        REPLICATOR[3] = GregTechAPI.registerMetaTileEntity(2182, new SimpleMachineMetaTileEntity(id("replicator.ev"), MTERecipes.REPLICATOR_RECIPES, MTETextures.REPLICATOR_OVERLAY, 4));

        //FUSION_REACTOR[0] = GregTechAPI.registerMetaTileEntity(2183, new TileEntityFusionReactor(id("fusion_reactor.iv"), 5));
        //FUSION_REACTOR[1] = GregTechAPI.registerMetaTileEntity(2184, new TileEntityFusionReactor(id("fusion_reactor.uv"), 6));

	
	}	
	
	public static class MTERecipes {
		public static final RecipeMap<SimpleRecipeBuilder> MASS_FABRICATOR_RECIPES;
		public static final RecipeMap<SimpleRecipeBuilder> REPLICATOR_RECIPES;
		
		static {
			MASS_FABRICATOR_RECIPES = (new RecipeMap("mass_fabricator", 0, 1, 0, 0, 0, 1, 1, 2, 1, new SimpleRecipeBuilder())).setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, ProgressWidget.MoveType.HORIZONTAL);
			REPLICATOR_RECIPES = (new RecipeMap("replicator", 0, 1, 0, 1, 1, 3, 0, 1, 1, new SimpleRecipeBuilder())).setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, ProgressWidget.MoveType.HORIZONTAL);
		}
	}
	
	public static class MTETextures {
		public static OrientedOverlayRenderer MASS_FABRICATOR_OVERLAY;
		public static OrientedOverlayRenderer REPLICATOR_OVERLAY;
		public static OrientedOverlayRenderer FUSION_REACTOR_OVERLAY;
		
		public static SimpleCubeRenderer FUSION_TEXTURE;
		
		static {
			MASS_FABRICATOR_OVERLAY = new OrientedOverlayRenderer("machines/mass_fab", new OverlayFace[]{OverlayFace.FRONT});
			REPLICATOR_OVERLAY = new OrientedOverlayRenderer("machines/replicator", new OverlayFace[]{OverlayFace.FRONT});
			FUSION_REACTOR_OVERLAY = new OrientedOverlayRenderer("machines/fusion_reactor", new OverlayFace[] {OverlayFace.FRONT});
		}
	}
	
	private static ResourceLocation id(String name) {
        return new ResourceLocation(TGILibrary.MODID, name);
    }
	
}
