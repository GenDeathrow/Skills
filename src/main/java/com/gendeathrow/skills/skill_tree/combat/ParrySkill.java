package com.gendeathrow.skills.skill_tree.combat;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.entity.extended.EntityMobsExt;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.utils.EnumHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class ParrySkill extends CombatBase implements ISkill
{

	public ParrySkill(SkillTrackerData tracker) 
	{
		super(tracker);
	}

	@Override
	public String LocalizedName()
	{
		return "skill.parry.name";
	}

	@Override
	public String ULN() {
		return "parry";
	}

	@Override
	public String Description() 
	{
		return "";
	}
	
	@Override
	public EnumStats PrimaryStat() {
		return EnumHelper.EnumStats.Dexterity;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumHelper.EnumStats.Strength;
	}

	@Override
	public void onEvent(Object event) 
	{
		if(event instanceof LivingAttackEvent)
		{
			LivingAttackEvent newEvent = (LivingAttackEvent) event;
			
			if(newEvent.entity == this.tracker.trackedEntity)
			{
				
				SkillTreeBase parrySkill = this.tracker.GetSkillByID("parry");
				
				if(!(newEvent.source.getEntity() instanceof EntityMob || newEvent.source.getEntity() instanceof EntityAnimal)) return;
				
				EntityMob mob = (EntityMob) newEvent.source.getEntity();
				
				EntityMobsExt mobTracker = EntityMobsExt.get(mob);
				
				float chance = (float) ((((this.getParrySkill(this.tracker.trackedEntity)-mobTracker.attack) +100)/2)*.01);
			
				System.out.println("Take Damage - Parry:((("+ this.getParrySkill(this.tracker.trackedEntity) +" - "+ mobTracker.attack +") + 100) /2 ) *.01) = " + chance);
				
				this.calculateGain(this.tracker.trackedEntity, this.getSuccess(chance), chance);
				
			}
			
		}
	}

}
