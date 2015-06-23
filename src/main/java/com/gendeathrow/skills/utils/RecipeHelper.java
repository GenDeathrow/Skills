package com.gendeathrow.skills.utils;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import org.apache.logging.log4j.Level;

import com.gendeathrow.skills.common.crafting.RecipeWrapper;
import com.gendeathrow.skills.core.Skillz;

public class RecipeHelper 
{

	public static RecipeWrapper getWrappedRecipefromItemStack(ItemStack crafting)
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
				if(!wrapped.recipe.getRecipeOutput().equals(null))
				{
					if(wrapped.recipe.getRecipeOutput().getItem() == crafting.getItem())
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
		
//		Skillz.logger.log(Level.INFO, "This item isn't wrapped");
		
		return null;
	}

	public static void RegisterWrappedRecipe(Block block, int difficulty, String id)
	{
		RegisterWrappedRecipe(new ItemStack(block).getItem(), difficulty, id);
	}
	
	public static void RegisterWrappedRecipe(Item item, int difficulty, String id)
	{
    	Iterator craftingList = CraftingManager.getInstance().getRecipeList().iterator();
    	int index = 0;
    	while(craftingList.hasNext())
    	{
    		Object originalRecipe = craftingList.next();
    		

    		if(((IRecipe) originalRecipe) == null || ((IRecipe) originalRecipe).getRecipeOutput() == null) continue;
 
    		if(((IRecipe) originalRecipe).getRecipeOutput().getItem() == item)
    		{
    			if(((IRecipe) originalRecipe) instanceof RecipeWrapper) 
    			{
    				Skillz.logger.log(Level.WARN, ((IRecipe) originalRecipe).getRecipeOutput().getDisplayName() +" is already Registered.");
    				continue;
    			}
    			RecipeWrapper wrappedRecipe = new RecipeWrapper(originalRecipe).setDifficulty(difficulty).setSkill(id);
    			
    			// Replace old recipe with new Recipe
    			CraftingManager.getInstance().getRecipeList().set(index, wrappedRecipe);
    			
    			System.out.println("Recipe wrapped:"+ wrappedRecipe.getRecipeOutput().getDisplayName());
    		}
    		//RecipeManager.WrappedRecipes.add(new RecipeWrapper(originalRecipe));
       		index++;
    	}
    	
    	//System.out.println("Error replaceing "+ item.getUnlocalizedName());
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
