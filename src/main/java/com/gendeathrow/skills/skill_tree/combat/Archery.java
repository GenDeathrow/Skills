package com.gendeathrow.skills.skill_tree.combat;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;

public class Archery extends CombatBase implements ISkill
{
	
	public Archery(SkillTrackerData tracker) 
	{
		super(tracker);	
	}
	
	@Override
	public String LocalizedName() 
	{
		return null;
	}

	@Override
	public String ULN() 
	{
		return "archery";
	}

	@Override
	public String Description() {
		// TODO Auto-generated method stub
		return "null";
	}

	@Override
	public void onEvent(Object event) 
	{
		
	}

}
