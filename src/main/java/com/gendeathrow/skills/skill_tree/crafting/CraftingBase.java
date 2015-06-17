package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.item.Item;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkillCat;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public abstract class CraftingBase extends SkillTreeBase implements ISkillCat
{
	
	public abstract void registerRecipes(Item item);
	
	public CraftingBase(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public void onEvent(Object event) 
	{
	
	}

	@Override
	public String getCatLocName() 
	{
		return "skill.cat.crafting";
	}
	

	@Override
	public String getCatULN() 
	{
		return "crafting_skill";
	}

}
