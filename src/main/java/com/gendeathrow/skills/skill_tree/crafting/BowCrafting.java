package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import com.gendeathrow.skills.common.crafting.RecipeWrapper;
import com.gendeathrow.skills.common.skill.SkillDifficulty;
import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.RecipeHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class BowCrafting extends CraftingBase implements ISkill{

	public static String id = "bowcrafting";
	
	public BowCrafting(SkillTrackerData tracker) {
		super(tracker);
	}

	@Override
	public String LocalizedName() {
		return "skill.bowcrafting.name";
	}

	@Override
	public String ULN() {
		return this.id;
	}

	@Override
	public String Description() {
		return null;
	}

	@Override
	public EnumStats PrimaryStat() {
		return EnumStats.Dexterity;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumStats.Strength;
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

		}
	}
	
	public static void RegisterRecipe()
	{
		RecipeHelper.RegisterWrappedRecipe(Items.bow, 0, BowCrafting.id);
		RecipeHelper.RegisterWrappedRecipe(Items.arrow, 0, BowCrafting.id);	
	}
	
}
