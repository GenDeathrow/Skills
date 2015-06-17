package com.gendeathrow.skills.skill_tree.helper;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.init.Items;

import com.gendeathrow.skills.skill_tree.combat.Archery;
import com.gendeathrow.skills.skill_tree.combat.HandtoHandSkill;
import com.gendeathrow.skills.skill_tree.combat.ParrySkill;
import com.gendeathrow.skills.skill_tree.combat.SwordSkill;
import com.gendeathrow.skills.skill_tree.combat.TacticsSkill;
import com.gendeathrow.skills.skill_tree.crafting.BlackSmiting;
import com.gendeathrow.skills.skill_tree.resource_gathering.FarmingSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.FishingSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.LumberJackSkill;
import com.gendeathrow.skills.skill_tree.resource_gathering.MiningSkillTree;
import com.gendeathrow.skills.utils.RecipeHelper;

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
		
		//Crafting Skills
		this.SkillList.put(BlackSmiting.id, BlackSmiting.class);
		
		
		//Register recipes
		RegisterCraftingRecipes();
	}
	
	public void RegisterSkill(String SkillName, String Category, Class<?> className)
	{	
		this.SkillList.put(SkillName, className);
		
		this.CategoryList.add(Category);
	}
	
	public static void RegisterCraftingRecipes()
	{
		//BlackSmith Recipes
		RecipeHelper.RegisterWrappedRecipe(Items.leather_boots).setDifficulty(0).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.leather_chestplate).setDifficulty(0).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.leather_helmet).setDifficulty(0).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.leather_leggings).setDifficulty(0).setSkill(BlackSmiting.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.golden_boots).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_chestplate).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_helmet).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_leggings).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_sword).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_axe).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_shovel).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_hoe).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_pickaxe).setDifficulty(BlackSmiting.goldDiff).setSkill(BlackSmiting.id);
	
		RecipeHelper.RegisterWrappedRecipe(Items.iron_boots).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_chestplate).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_helmet).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_leggings).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_axe).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_shovel).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_hoe).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_pickaxe).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_sword).setDifficulty(BlackSmiting.ironDiff).setSkill(BlackSmiting.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_boots).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_chestplate).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_helmet).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_leggings).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_sword).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_axe).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_shovel).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_hoe).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_pickaxe).setDifficulty(BlackSmiting.diamondDiff).setSkill(BlackSmiting.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.bucket).setDifficulty(5).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.minecart).setDifficulty(20).setSkill(BlackSmiting.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.stone_axe).setDifficulty(1).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.stone_shovel).setDifficulty(0).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.stone_hoe).setDifficulty(0).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.stone_pickaxe).setDifficulty(0).setSkill(BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.stone_sword).setDifficulty(0).setSkill(BlackSmiting.id);
		
		
		//LumberJacking
	}
}
