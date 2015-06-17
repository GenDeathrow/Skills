package com.gendeathrow.skills.utils;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import org.apache.logging.log4j.Level;

import com.gendeathrow.skills.common.crafting.RecipeWrapper;
import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.skill_tree.crafting.BlackSmiting;

public class RecipeHelper 
{

	public static RecipeWrapper getWrappedRecipefromItemStack(Item itemIn)
	{
		Iterator list = CraftingManager.getInstance().getRecipeList().iterator();
		
		while(list.hasNext())
		{
			Object obj = list.next();
			
			if(obj !=null && obj instanceof RecipeWrapper)
			{
				RecipeWrapper wrapped = (RecipeWrapper)obj;
				
				try
				{
				if(!wrapped.recipe.getRecipeOutput().getItem().equals(null))
				{
					if(wrapped.recipe.getRecipeOutput().getItem() == itemIn)
					{
						return (RecipeWrapper) obj;
					}
				}
				}catch(NullPointerException e)
				{
					//Skillz.logger.log(Level.ERROR, "Null Error: "+ e);
				}
			}
		}
		
		Skillz.logger.log(Level.INFO, "This item isn't wrapped");
		
		return null;
	}
	
	public static RecipeWrapper RegisterWrappedRecipe(Item item)
	{
    	Iterator craftingList = CraftingManager.getInstance().getRecipeList().iterator();
    	int index = 0;
    	while(craftingList.hasNext())
    	{
    		Object originalRecipe = craftingList.next();
    		if(((IRecipe) originalRecipe).getRecipeOutput().getItem() == item)
    		{
    			RecipeWrapper wrappedRecipe = new RecipeWrapper(originalRecipe);
    			// Replace old recipe with new Recipe
    			CraftingManager.getInstance().getRecipeList().set(index, wrappedRecipe);
    			
    			System.out.println("Recipe wrapped:"+ wrappedRecipe.getRecipeOutput().getDisplayName());
    			return wrappedRecipe;
    		}
    		//RecipeManager.WrappedRecipes.add(new RecipeWrapper(originalRecipe));
       		index++;
    	}
    	
    	System.out.println("Error replaceing "+ item.getUnlocalizedName());
		return null;

	}
    /**
     * Force get container instance of the given crafting inventory
     * @param invo
     * @return
     */
    public static Container getContainer(InventoryCrafting invo)
    {
    	try
    	{
	    	Field conField = InventoryCrafting.class.getDeclaredField("field_70465_c");
	    	conField.setAccessible(true);
			return (Container)conField.get(invo);
    	} catch(Exception e)
    	{
    		try
    		{
    	    	Field conField = InventoryCrafting.class.getDeclaredField("eventHandler");
    	    	conField.setAccessible(true);
    			return (Container)conField.get(invo);
    		} catch(Exception e1)
    		{
        		Skillz.logger.log(Level.ERROR, "Unable to get container for InventoryCrafting", e1);
        		return null;
    		}
    	}
    }
    
    /**
     * Force get list of crafters using this container
     * @param cont
     * @return
     */
    public static ArrayList<?> getCrafters(Container cont)
    {
    	try
    	{
        	Field craftField = Container.class.getDeclaredField("field_75149_d");
        	craftField.setAccessible(true);
        	return (ArrayList<?>)craftField.get(cont);
    	} catch(Exception e)
    	{
    		try
    		{
	        	Field craftField = Container.class.getDeclaredField("crafters");
	        	craftField.setAccessible(true);
	        	return (ArrayList<?>)craftField.get(cont);
    		} catch(Exception e1)
    		{
        		Skillz.logger.log(Level.ERROR, "Unable to get crafters for Container", e1);
        		return null;
    		}
    	}
    }
    
}