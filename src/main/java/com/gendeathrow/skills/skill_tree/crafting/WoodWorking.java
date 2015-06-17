package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.item.Item;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class WoodWorking extends CraftingBase implements ISkill{

	public WoodWorking(SkillTrackerData tracker) {
		super(tracker);
	}

	@Override
	public String LocalizedName()  {
		return "skill.woodworking.name";
	}

	@Override
	public String ULN() {
		return "woodworking";
	}

	@Override
	public String Description() {
		return "";
	}

	@Override
	public EnumStats PrimaryStat() {
		return EnumStats.Dexterity;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumStats.Intelligence;
	}

	@Override
	public void registerRecipes(Item item) {
		
	}

}
