package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.init.Items;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.gendeathrow.skills.common.crafting.RecipeWrapper;
import com.gendeathrow.skills.common.skill.SkillDifficulty;
import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;
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
	public void onEvent(Object event) 
	{
		
		if(event instanceof ItemCraftedEvent)
		{
			ItemCraftedEvent newEvent = (ItemCraftedEvent) event;
			
			RecipeWrapper recipe = RecipeHelper.getWrappedRecipefromItemStack((newEvent.crafting));
			
			if(recipe!= null && recipe.getSkill() == this.id)
			{
				this.calculateGain(this.tracker.trackedEntity, new SkillDifficulty(this.id).setDifficulty(recipe.getDifficulty()));
			}
			}else if(event instanceof ItemSmeltedEvent)
			{
				ItemSmeltedEvent newEvent = (ItemSmeltedEvent) event;
				int[] oredic = OreDictionary.getOreIDs(newEvent.smelting);
				if(oredic != null)
				{
					for(int id : oredic)
					{
						String name = OreDictionary.getOreName(id);
						if(name.startsWith("ingot"))
						{
							this.calculateGain(this.tracker.trackedEntity, 1);
							break;
						}

					}
				}
			}	
	}
	
	public static void RegisterRecipe()
	{
		RecipeHelper.RegisterWrappedRecipe(Items.leather_boots, 0, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.leather_chestplate, 0, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.leather_helmet, 0, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.leather_leggings, 0, BlackSmiting.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.golden_boots, BlackSmiting.goldDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_chestplate, BlackSmiting.goldDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_helmet, BlackSmiting.goldDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_leggings, BlackSmiting.goldDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_sword, BlackSmiting.goldDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_axe, BlackSmiting.goldDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_shovel, BlackSmiting.goldDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_hoe, BlackSmiting.goldDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.golden_pickaxe, BlackSmiting.goldDiff, BlackSmiting.id);
	
		RecipeHelper.RegisterWrappedRecipe(Items.iron_boots, BlackSmiting.ironDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_chestplate, BlackSmiting.ironDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_helmet, BlackSmiting.ironDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_leggings, BlackSmiting.ironDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_axe, BlackSmiting.ironDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_shovel, BlackSmiting.ironDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_hoe, BlackSmiting.ironDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_pickaxe, BlackSmiting.ironDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.iron_sword, BlackSmiting.ironDiff, BlackSmiting.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_boots, BlackSmiting.diamondDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_chestplate, BlackSmiting.diamondDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_helmet, BlackSmiting.diamondDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_leggings, BlackSmiting.diamondDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_sword, BlackSmiting.diamondDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_axe, BlackSmiting.diamondDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_shovel, BlackSmiting.diamondDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_hoe, BlackSmiting.diamondDiff, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.diamond_pickaxe, BlackSmiting.diamondDiff, BlackSmiting.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.bucket, 5, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.minecart, 20, BlackSmiting.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.stone_axe, 0, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.stone_shovel, 0, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.stone_hoe, 0, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.stone_pickaxe, 0, BlackSmiting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.stone_sword, 0, BlackSmiting.id);
	}

}
