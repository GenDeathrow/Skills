package com.gendeathrow.skills.skill_tree.magical;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class Enchanting extends MagicalBase implements ISkill
{
	public static String id = "enchanting";
	
	public Enchanting(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String LocalizedName() 
	{
		return "skill.enchanting.name";
	}

	@Override
	public String ULN() 
	{
		return this.id;
	}

	@Override
	public String Description() 
	{
		return null;
	}

	@Override
	public EnumStats PrimaryStat() 
	{
		return EnumStats.Intelligence;
	}

	@Override
	public EnumStats SecondaryStat() 
	{
		return EnumStats.Wisdom;
	}
	
	@Override
	public void onEvent(Object event) 
	{
	
	}

}
