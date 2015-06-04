package com.gendeathrow.skills.skill_tree.helper;

import java.util.ArrayList;
import java.util.HashMap;

import com.gendeathrow.skills.skill_tree.combat.Archery;
import com.gendeathrow.skills.skill_tree.combat.HandtoHandSkill;
import com.gendeathrow.skills.skill_tree.combat.ParrySkill;
import com.gendeathrow.skills.skill_tree.combat.SwordSkill;
import com.gendeathrow.skills.skill_tree.combat.TacticsSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.FarmingSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.FishingSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.LumberJackSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.MiningSkillTree;

public class SkillTree_Manager 
{

	public final static SkillTree_Manager instance = new SkillTree_Manager();
	
	//TODO adding custom tools to be used for specific skills
	public final static HashMap<String, ArrayList> SkillTree_CustomTools = new HashMap<String, ArrayList>();
	
	public HashMap<String,Class<?>> SkillList;
	
	public ArrayList CategoryList = new ArrayList();
	
	public SkillTree_Manager()
	{
		this.SkillList = new HashMap<String,Class<?>>();
		this.SkillList.put("mining", MiningSkillTree.class);
		this.SkillList.put("lumberjacking", LumberJackSkill.class);
		this.SkillList.put("fishing", FishingSkill.class);
		this.SkillList.put("farming", FarmingSkill.class);
		
		//Combat Skills
		this.SkillList.put("swords", SwordSkill.class);
		this.SkillList.put("tactics", TacticsSkill.class);
		this.SkillList.put("parry", ParrySkill.class);
		this.SkillList.put("archery", Archery.class);
		this.SkillList.put("melee", HandtoHandSkill.class);
	}
	
	public void RegisterSkill(String SkillName, String Category, Class<?> className)
	{	
		this.SkillList.put(SkillName, className);
		
		this.CategoryList.add(Category);
	}
	
}
