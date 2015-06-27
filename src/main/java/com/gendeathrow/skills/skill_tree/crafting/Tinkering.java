package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.RecipeHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class Tinkering extends CraftingBase implements ISkill
{
	public static String id = "tinkering";
	
	public Tinkering(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String LocalizedName() {
		return "skill.tinkering.name";
	}

	@Override
	public String ULN() {
		return Tinkering.id;
	}

	@Override
	public String Description() {
		return "";
	}

	@Override
	public EnumStats PrimaryStat() {
		return EnumStats.Intelligence;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumStats.Wisdom;
	}

	
	public static void RegisterRecipe()
	{
		RecipeHelper.RegisterWrappedRecipe(Items.banner, 5, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Items.clock, 40, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Items.compass, 35, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.jukebox, 65, Tinkering.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.fire_charge, 80, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Items.fireworks, 60, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.tnt, 40, Tinkering.id);

		RecipeHelper.RegisterWrappedRecipe(Items.shears, 10, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Items.dye, 0, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Items.lead, 20, Tinkering.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.comparator, 40, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Items.repeater, 10, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.hopper, 50, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.redstone_lamp, 25, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.redstone_torch, 0, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.redstone_block, 0, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.daylight_detector, 60, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.piston, 30, Tinkering.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.activator_rail, 30, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.rail, 20, Tinkering.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.dispenser, 55, Tinkering.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.tripwire, 38, Tinkering.id);
	
	}
}
