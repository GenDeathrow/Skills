package com.gendeathrow.skills.skill_tree.resource_gathering;

import net.minecraft.util.StatCollector;

import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class FishingSkill extends SkillTreeBase
{

	@Override
	public String getLocName() {
		return StatCollector.translateToLocal("skill.fishing.name");
	}

	@Override
	public String getULN() {
		return "fishing";
	}

	@Override
	public String getCat() {
		return StatCollector.translateToLocal("skill.cat.resource");
	}

	@Override
	public String getDescription() {
		return "null";
	}

	@Override
	public void onEvent(Object event) 
	{
		
	}

}
