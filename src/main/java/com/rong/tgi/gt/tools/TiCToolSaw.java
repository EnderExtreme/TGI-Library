package com.rong.tgi.gt.tools;

import java.util.List;

import com.rong.tgi.CommonProxy;

import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.AoeToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.tools.TinkerTools;

public class TiCToolSaw extends AoeToolCore {
	
	//TODO: Giving a powered version via battery(?)
	
	public TiCToolSaw() {
	    this(PartMaterialType.handle(TinkerTools.toolRod),
	         PartMaterialType.head(CommonProxy.sawHead));
	  }
	
	protected TiCToolSaw(PartMaterialType... requiredComponents) {
	    super(requiredComponents);

	    addCategory(Category.TOOL);
	    addCategory(Category.WEAPON);
	}

	@Override
	protected ToolNBT buildTagData(List<Material> materials) {
		HandleMaterialStats handle = materials.get(0).getStatsOrUnknown(MaterialTypes.HANDLE);
		HeadMaterialStats head = materials.get(1).getStatsOrUnknown(MaterialTypes.HEAD);

		ToolNBT data = new ToolNBT();
		data.head(head);
		data.handle(handle);

		data.harvestLevel = head.harvestLevel;

		return data;
	}

	@Override
	public double attackSpeed() {
		return 1.0F;
	}

	@Override
	public float damagePotential() {
		return 1.0F;
	}

}
