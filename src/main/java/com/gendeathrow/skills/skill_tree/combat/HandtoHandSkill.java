package com.gendeathrow.skills.skill_tree.combat;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.utils.EnumHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class HandtoHandSkill extends SkillTreeBase implements ISkill
{

	public HandtoHandSkill(SkillTrackerData tracker) {
		super(tracker);
	}

	@Override
	public String LocalizedName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ULN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String Description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onEvent(Object event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumStats PrimaryStat() {
		return EnumHelper.EnumStats.Dexterity;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumHelper.EnumStats.Strength;
	}

}
