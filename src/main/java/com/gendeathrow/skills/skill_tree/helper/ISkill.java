package com.gendeathrow.skills.skill_tree.helper;

import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public interface ISkill 
{

	public abstract String LocalizedName();

	public abstract String ULN();

	public abstract String Description();
	
	public abstract EnumStats PrimaryStat();
	
	public abstract EnumStats SecondaryStat();
	
	
}
