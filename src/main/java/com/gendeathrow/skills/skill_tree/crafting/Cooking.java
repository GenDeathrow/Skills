package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class Cooking extends CraftingBase implements ISkill{

	public static String id = "cooking";
	
	public Cooking(SkillTrackerData tracker) {
		super(tracker);
	}

	@Override
	public String LocalizedName() {
		return "skill.cooking.name";
	}

	@Override
	public String ULN() 
	{
		return Cooking.id;
	}

	@Override
	public String Description() {
		return null;
	}

	@Override
	public EnumStats PrimaryStat() {
		return EnumStats.Wisdom;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumStats.Intelligence;
	}
	
	@Override
	public void onEvent(Object event) 
	{
		if(event instanceof ItemSmeltedEvent)
		{
			ItemSmeltedEvent newEvent = (ItemSmeltedEvent) event;
			
			if(newEvent.smelting.getItem() instanceof ItemFood)
			{
				this.calculateGain(this.tracker.trackedEntity, 1);
			}
		}
		
	}

}
