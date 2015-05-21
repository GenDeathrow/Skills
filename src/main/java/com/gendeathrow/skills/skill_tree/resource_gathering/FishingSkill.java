package com.gendeathrow.skills.skill_tree.resource_gathering;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.FishingHooks.FishableCategory;

import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class FishingSkill extends SkillTreeBase
{

	/*
	 * Alot of Code is in Sk_FishHook & SK_FishingHook  
	 * 
	 * 
	 */
	
	
	public FishingSkill()
	{
		super();
		this.setGain(.2);
	}

	@Override
	public String getLocName() 
	{
		return StatCollector.translateToLocal("skill.fishing.name");
	}

	@Override
	public String getULN() 
	{
		return "fishing";
	}

	@Override
	public String getCat() 
	{
		return StatCollector.translateToLocal("skill.cat.resource");
	}

	@Override
	public String getDescription() 
	{
		return "null";
	}

	@Override
	public void onEvent(Object event) 
	{}

	@Override
	public double calculateGain(EntityPlayer player, int success)
	{

		return super.calculateGain(player, success);
	}
	
	public void CatchEvent(EntityPlayer player, FishableCategory cat)
	{
		switch (cat)
		{
			case FISH:
				this.setGain(.2);
				this.calculateGain(player, 1);
				break;
			case JUNK:
				this.setGain(.1);
				this.calculateGain(player, 0);
				break;
			case TREASURE:
				this.setGain(.4);
				this.calculateGain(player, 1);
				break;
			default:
				break;
		}
	}
	
}
