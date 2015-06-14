package com.gendeathrow.skills.common.crafting;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RecipeManager 
{

	public final static List WrappedRecipes = Lists.newArrayList();
	
	
    public static void WrapVanillaRecipes()
    {
    	Iterator craftingList = CraftingManager.getInstance().getRecipeList().iterator();
    	int index = 0;
    	while(craftingList.hasNext())
    	{
    		Object originalRecipe = craftingList.next();		
    		WrappedRecipes.add(new RecipeWrapper(originalRecipe));
       		index++;
    	}
    	
    	CraftingManager.getInstance().getRecipeList().removeAll(CraftingManager.getInstance().getRecipeList());
    	CraftingManager.getInstance().getRecipeList().addAll(WrappedRecipes);

    }
    
}
