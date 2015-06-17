package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.item.Item;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class Cooking extends CraftingBase implements ISkill{

	public Cooking(SkillTrackerData tracker) {
		super(tracker);
	}

	@Override
	public String LocalizedName() {
		return null;
	}

	@Override
	public String ULN() {
		return null;
	}

	@Override
	public String Description() {
		return null;
	}

	@Override
	public EnumStats PrimaryStat() {
		return null;
	}

	@Override
	public EnumStats SecondaryStat() {
		return null;
	}

	@Override
	public void registerRecipes(Item item) {
		
	}

}
