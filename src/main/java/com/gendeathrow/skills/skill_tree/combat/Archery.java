package com.gendeathrow.skills.skill_tree.combat;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.ChatHelper;
import com.gendeathrow.skills.utils.EnumHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class Archery extends CombatBase implements ISkill
{
	
	public Archery(SkillTrackerData tracker) 
	{
		super(tracker);	
	}
	
	@Override
	public String LocalizedName() 
	{
		return "skill.archery.name";
	}

	@Override
	public String ULN() 
	{
		return "archery";
	}

	@Override
	public String Description() {
		// TODO Auto-generated method stub
		return "null";
	}

	@Override
	public void onEvent(Object event) 
	{
		// if player is attacked
		if(event instanceof LivingAttackEvent)
		{
			System.out.println("Entity Attacked");
			LivingAttackEvent newEvent = (LivingAttackEvent)event;
			
			if(!(newEvent.source.getSourceOfDamage() instanceof EntityArrow)) return;
			
			ItemStack inHand = this.tracker.trackedEntity.getHeldItem();
			
			if(inHand == null ) return;
			
			if(this.CorrectWeapon(this.tracker.trackedEntity))
			{
				//TODO Change to Dynamic
				float chance = (float) ((((this.current-this.getParrySkill(newEvent.entity)) +100)/2)*.01);
				
				this.lastHit = newEvent.entity;
				this.calculateGain((this.tracker.trackedEntity), this.getSuccess(chance), chance);
				
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
				
				//Dex shouldn't add dmg, new arrows should
				//float addDEX = (float) (playerStats.Dexterity * 0.025);
				
				//TODO Tactics
				float TacticModifier = (this.tracker.GetSkillByID("tactics").getBonusFactor(0, 1, 1.7) + 50) / 100; // static 100%
				
				float randomRoll = rand.nextFloat();
				//TODO Replace 0 with added arrow dmg
				float damage = Math.round(((((newEvent.ammount / 100)*randomRoll)*100) + 0) * TacticModifier);
				
				damage = Math.round(damage) == 0 ? 1 : Math.round(damage);
				
				ChatHelper.instance.CombatChat(player, this.lastHit, (int) damage, (int)newEvent.ammount);
				//TODO Replace 0 with added arrow dmg
				System.out.println( "WeaponRoll:"+(((newEvent.ammount / 100)*randomRoll)*100) + " + (extraDmg)"+ 0 + "* (TacticsModifier)" + TacticModifier);
				
				System.out.println("New Damage:" + Math.round(damage) +" - Old Damage:"+ newEvent.ammount + " mod:"+ TacticModifier);

				newEvent.ammount = damage;
			}
		}
		
	super.onEvent(event);
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
	public void registerWeapons() 
	{
		this.registerWeapon(ItemBow.class);
	}

}
