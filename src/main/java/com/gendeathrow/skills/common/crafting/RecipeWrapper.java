package com.gendeathrow.skills.common.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class RecipeWrapper implements IRecipe
{
	public IRecipe recipe;
	private EnumStats stat;
	private int difficulty;
	
	RecipeWrapper(Object originalRecipe)
	{
		this.recipe = (IRecipe) originalRecipe;
		this.stat = EnumStats.Wisdom;
		this.difficulty = 0;
	}
	
	public RecipeWrapper setStat(EnumStats stat)
	{
		this.stat = stat;
		return this;
	}
	
	public RecipeWrapper setDifficulty(int diff)
	{
		this.difficulty = diff;
		return this;
	}

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World worldIn) {
		// TODO Auto-generated method stub
		return recipe.matches(p_77569_1_, worldIn);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
		// TODO Auto-generated method stub
		//System.out.println(recipe.getCraftingResult(p_77572_1_).getItem().getUnlocalizedName());
		System.out.println(recipe.getRecipeOutput().getItem().getUnlocalizedName() +" Stat:"+ this.stat.toString() +"; Diff:"+ this.difficulty);
		return recipe.getCraftingResult(p_77572_1_);
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return recipe.getRecipeSize();
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return recipe.getRecipeOutput();
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting p_179532_1_) {
		// TODO Auto-generated method stub
		return recipe.getRemainingItems(p_179532_1_);
	}

}
