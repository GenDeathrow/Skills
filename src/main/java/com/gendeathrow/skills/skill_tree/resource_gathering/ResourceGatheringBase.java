package com.gendeathrow.skills.skill_tree.resource_gathering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkillCat;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class ResourceGatheringBase extends SkillTreeBase implements ISkillCat
{

	public ResourceGatheringBase(SkillTrackerData tracker) 
	{
		super(tracker);
	}
	
	@Override
	public String getCatLocName() 
	{
		return "skill.cat.resources";
	}

	@Override
	public String getCatDescription() 
	{
		return "null";
	}
	
	@Override
	public String getCatULN() 
	{
		return "resource_gathering";
	}
	
	@Override
	public void onEvent(Object event) 
	{
	
	}

	/**
	 * Gives a chance for bonus drops to happen depending on dropChance
	 * 
	 * @param event
	 * @param dropChance
	 */
	public void doBonusDrops(HarvestDropsEvent event, float dropChance)
	{
		
		List<ItemStack> dropList = new ArrayList();
		System.out.println("DropListB:"+ event.drops.size());
		
		Iterator<ItemStack> it = event.drops.iterator();
		Random rand = new Random();
		ItemStack item;
		while(it.hasNext())
		{
			item = it.next();
			System.out.println("increase items:"+ item.getDisplayName());
			if(rand.nextFloat() < dropChance)
			{
				dropList.add(item.copy());
			}
		}
		event.drops.addAll(dropList);
		System.out.println("DropListA:"+ event.drops.size());

	}

}
