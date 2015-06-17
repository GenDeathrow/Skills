package com.gendeathrow.skills.common.crafting;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;
import com.gendeathrow.skills.utils.RecipeHelper;

public class RecipeWrapper implements IRecipe
{
	public IRecipe recipe;
	private String skill;
	private int difficulty;
	
	public RecipeWrapper(Object originalRecipe)
	{
		this.recipe = (IRecipe) originalRecipe;
		this.skill = "";
		this.difficulty = 0;
	}
		
	public RecipeWrapper setSkill(String skill)
	{
		this.skill = skill;
		return this;
	}
	
	public RecipeWrapper setDifficulty(int diff)
	{
		this.difficulty = diff;
		return this;
	}

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World worldIn) {
		return recipe.matches(p_77569_1_, worldIn);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting invo) {
		System.out.println(recipe.getRecipeOutput().getItem().getUnlocalizedName() +" Stat:"+ this.skill.toString() +"; Diff:"+ this.difficulty);
		
		Container tmpCon = RecipeHelper.getContainer(invo);		
		
		ArrayList<?> crafters = tmpCon == null? null : RecipeHelper.getCrafters(tmpCon);
		
		if(!Skillz.proxy.isClient()) return recipe.getCraftingResult(invo);
		if(crafters != null)
		{
			for(Object obj : crafters)
			{
				if(obj instanceof EntityPlayer)
				{
					SkillTrackerData tracker = SkillTrackerData.get((EntityPlayer)obj);
					
					if(this.skill != "" && tracker.GetSkillByID(this.skill) != null)
					{
						 if(tracker.GetSkillByID(this.skill).getSkillLevel() >= this.difficulty)
						 {
							 System.out.println(tracker.GetSkillByID("mining").getSkillLevel()+ " < Wanna craft something");
							 return recipe.getCraftingResult(invo);
						 }
						 else {System.out.println("crafter skill < difficulty"); return null;}
						
					}else System.out.println("skill or traker null");
				}else System.out.println("craft not player");
			}
		}else System.out.println("crafter null");
		return recipe.getCraftingResult(invo);
	}

	@Override
	public int getRecipeSize() {
		return recipe.getRecipeSize();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return recipe.getRecipeOutput();
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting p_179532_1_) {
		return recipe.getRemainingItems(p_179532_1_);
	}

}
