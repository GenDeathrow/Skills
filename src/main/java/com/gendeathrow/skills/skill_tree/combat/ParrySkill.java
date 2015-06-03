package com.gendeathrow.skills.skill_tree.combat;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.utils.EnumHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class ParrySkill extends CombatBase implements ISkill
{

	public ParrySkill(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String LocalizedName()
	{
		return "skill.parry.name";
	}

	@Override
	public String ULN() {
		return "parry";
	}

	@Override
	public String Description() 
	{
		return "";
	}
	
	@Override
	public EnumStats PrimaryStat() {
		return EnumHelper.EnumStats.Dexterity;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumHelper.EnumStats.Strength;
	}

	@Override
	public void onEvent(Object event) 
	{
		
	}

}
