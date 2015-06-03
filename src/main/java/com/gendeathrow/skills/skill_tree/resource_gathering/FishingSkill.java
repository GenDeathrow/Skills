package com.gendeathrow.skills.skill_tree.resource_gathering;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.FishingHooks.FishableCategory;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.utils.EnumHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class FishingSkill extends ResourceGatheringBase implements ISkill
{

	/*
	 * Alot of Code is in Sk_FishHook & SK_FishingHook  
	 * 
	 * 
	 */
	
	
	public FishingSkill(SkillTrackerData tracker)
	{
		super(tracker);
		this.setGain(.2);
	}

	@Override
	public String LocalizedName() 
	{
		return StatCollector.translateToLocal("skill.fishing.name");
	}

	@Override
	public String ULN() 
	{
		return "fishing";
	}

	@Override
	public String Description() 
	{
		return "null";
	}

	@Override
	public EnumStats PrimaryStat() {
			return EnumHelper.EnumStats.Dexterity;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumHelper.EnumStats.Wisdom;
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
