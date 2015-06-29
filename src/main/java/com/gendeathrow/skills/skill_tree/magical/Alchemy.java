package com.gendeathrow.skills.skill_tree.magical;

import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent.Post;
import net.minecraftforge.event.brewing.PotionBrewEvent.Pre;
import net.minecraftforge.event.brewing.PotionBrewedEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class Alchemy extends MagicalBase implements ISkill{

	public static String id = "alchemy";
	public Alchemy(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String LocalizedName() 
	{
		return "skills.alchemy.name";
	}

	@Override
	public String ULN() {
		return this.id;
	}

	@Override
	public String Description() 
	{
		return null;
	}

	@Override
	public EnumStats PrimaryStat() 
	{
		return EnumStats.Intelligence;
	}

	@Override
	public EnumStats SecondaryStat() 
	{
		return EnumStats.Wisdom;
	}
	
	@Override
	public void onEvent(Object event) 
	{
		if(event instanceof PotionBrewEvent)
		{
			PotionBrewEvent brewEvent = (PotionBrewEvent) event;
			
			if(brewEvent instanceof PotionBrewEvent.Pre)
			{
				Pre pre = (PotionBrewEvent.Pre) brewEvent;
				
				System.out.println("pre event");
				
			}
			else if(brewEvent instanceof PotionBrewEvent.Post)
			{
				 Post post = (PotionBrewEvent.Post) brewEvent;
				System.out.println("post event");
			}
		}

	}

}
