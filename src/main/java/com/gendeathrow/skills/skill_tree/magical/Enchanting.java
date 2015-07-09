package com.gendeathrow.skills.skill_tree.magical;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class Enchanting extends MagicalBase implements ISkill
{
	public static String id = "enchanting";
	
	public Enchanting(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String LocalizedName() 
	{
		return "skill.enchanting.name";
	}

	@Override
	public String ULN() 
	{
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
	
		if(event instanceof PlayerInteractEvent)
		{
			if(((PlayerInteractEvent) event).action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)
			{
				if(((PlayerInteractEvent) event).world.getBlockState(((PlayerInteractEvent) event).pos).getBlock() == Blocks.enchanting_table)
				{
					System.out.println("open enchant");
				}
			}
		}
		
	}

}
