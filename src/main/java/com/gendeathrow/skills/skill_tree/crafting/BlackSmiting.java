package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;
import com.gendeathrow.skills.utils.RecipeHelper;

public class BlackSmiting extends CraftingBase implements ISkill{

	public static String id="blacksmiting";
	
	public static int ironDiff = 10;
	public static int goldDiff = 30;
	public static int diamondDiff = 60;
	
	
	public BlackSmiting(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String LocalizedName() {
		return "skill.blacksmiting.name";
	}

	@Override
	public String ULN() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String Description() {
		return "black desc";
	}

	@Override
	public EnumStats PrimaryStat() {
		return EnumStats.Strength;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumStats.Intelligence;
	}

	@Override
	public void registerRecipes(Item item) 
	{
	}

}
