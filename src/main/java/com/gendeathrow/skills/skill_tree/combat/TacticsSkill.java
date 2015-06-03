package com.gendeathrow.skills.skill_tree.combat;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.EnumHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class TacticsSkill extends CombatBase  implements ISkill
{

	public TacticsSkill(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String LocalizedName() 
	{
		return "skill.tactics.name";
	}

	@Override
	public String ULN() {
		// TODO Auto-generated method stub
		return "tactics";
	}

	@Override
	public String Description() {
		// TODO Auto-generated method stub
		return "null";
	}
	
	@Override
	public EnumStats PrimaryStat() {
		return EnumHelper.EnumStats.Intelligence;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumHelper.EnumStats.Wisdom;
	}
	
	public float getCombatMod()
	{		
		return (float) ((this.current < 1 ? 1 : this.current ) * 1.7) / 100;
	}



}
