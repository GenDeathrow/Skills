package com.gendeathrow.skills.skill_tree.helper;

import java.util.HashMap;

import com.gendeathrow.skills.skill_tree.resource_gathering.FarmingSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.FishingSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.LumberJackSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.MiningSkillTree;

public class SkillTree_Manager 
{

	public final static SkillTree_Manager instance = new SkillTree_Manager();
	
	public HashMap<String,Class<?>> SkillList;
	
	
	public SkillTree_Manager()
	{
		this.SkillList = new HashMap<String,Class<?>>();
		this.SkillList.put("mining", MiningSkillTree.class);
		this.SkillList.put("lumberjacking", LumberJackSkill.class);
		this.SkillList.put("fishing", FishingSkill.class);
		this.SkillList.put("farming", FarmingSkill.class);
	}
	
	public void RegisterSkill(Class<?> className, String SkillName)
	{	
		this.SkillList.put(SkillName, className);
	}
	
}
