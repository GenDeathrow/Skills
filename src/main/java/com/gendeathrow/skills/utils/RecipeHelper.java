package com.gendeathrow.skills.utils;


import java.lang.reflect.Field;
import java.util.Iterator;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;

import org.apache.logging.log4j.Level;

import com.gendeathrow.skills.common.crafting.RecipeWrapper;
import com.gendeathrow.skills.core.Skillz;

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
    
}
