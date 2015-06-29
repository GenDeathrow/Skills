package com.gendeathrow.skills.skill_tree.magical;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkillCat;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class MagicalBase extends SkillTreeBase implements ISkillCat
{

	public MagicalBase(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String getCatLocName() 
	{
		return "skill.cat.magical";
	}
	
	@Override
	public String getCatULN() 
	{
		return "magical_skill";
	}
	@Override
	public void onEvent(Object event) 
	{
		
	}

}
