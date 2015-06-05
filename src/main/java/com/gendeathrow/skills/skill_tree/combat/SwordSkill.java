package com.gendeathrow.skills.skill_tree.combat;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.ChatHelper;
import com.gendeathrow.skills.utils.EnumHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class SwordSkill extends CombatBase implements ISkill
{

	public SwordSkill(SkillTrackerData tracker) 
	{
		super(tracker);
	}



	@Override
	public String LocalizedName() 
	{
		return "skill.swords.name";
	}

	@Override
	public String ULN() 
	{
		return "swords";
	}

	@Override
	public String Description() 
	{
		return "null";
	}

	@Override
	public EnumStats PrimaryStat() {
		return EnumHelper.EnumStats.Strength;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumHelper.EnumStats.Dexterity;
	}

	
	@Override
	public void onEvent(Object event) 
	{
		// if player is attacked
		if(event instanceof AttackEntityEvent)
		{
			AttackEntityEvent newEvent = (AttackEntityEvent)event;
			
			ItemStack inHand = newEvent.entityPlayer.getHeldItem();
			
			if(inHand == null) return;
			
			if(this.CorrectWeapon(newEvent.entityPlayer))
			{
				//TODO Change to Dynamic
				float chance = (float) ((((this.current-this.getParrySkill(newEvent.target)) +100)/2)*.01);
				
				this.lastHit = newEvent.target;
				this.calculateGain(newEvent.entityPlayer, this.getSuccess(chance), chance);
				
				if(this.success == 1) this.tracker.GetSkillByID("tactics").calculateGain(this.tracker.trackedEntity, this.success);
		
			}
			
		}
		
		// Set dynamic Damage
		if(event instanceof LivingHurtEvent)
		{
			LivingHurtEvent newEvent = (LivingHurtEvent) event;
			//System.out.println("LastHit"+ this.lastHit != null ? this.lastHit.getName() : "null");
			if(this.lastHit != null && this.lastHit == newEvent.entity)
			{
				EntityPlayer player = (EntityPlayer) newEvent.source.getEntity();
				ItemStack weapon = player.getHeldItem();
				
				//Get random Damage
				Random rand = new Random();
				
				StatTrackerData playerStats = StatTrackerData.get(player);
				
				float addStr = (float) (playerStats.Strength.getValue() * 0.025);
				
				//TODO Tactics
				float TacticModifier = (this.tracker.GetSkillByID("tactics").getBonusFactor(0, 1, 1.7) + 50) / 100; // static 100%
				
				float randomRoll = rand.nextFloat();
				
				float damage = Math.round(((((newEvent.ammount / 100)*randomRoll)*100) + addStr) * TacticModifier);
				
				damage = Math.round(damage) == 0 ? 1 : Math.round(damage);
				
				ChatHelper.instance.CombatChat(player, this.lastHit, (int) damage, (int)newEvent.ammount);
				
				System.out.println( "WeaponRoll:"+(((newEvent.ammount / 100)*randomRoll)*100) + " + (Str)"+ addStr + "* (TacticsModifier)" + TacticModifier);
				
				System.out.println("New Damage:" + Math.round(damage) +" - Old Damage:"+ newEvent.ammount + " mod:"+ TacticModifier);

				newEvent.ammount = damage;
			}
		}
			
		super.onEvent(event);
	}
	
	

	@Override
	public void registerWeapons() 
	{
		this.registerWeapon(ItemSword.class);
	}




}
