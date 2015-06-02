package com.gendeathrow.skills.skill_tree.combat;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.entity.extended.EntityAnimalExt;
import com.gendeathrow.skills.entity.extended.EntityMobsExt;
import com.gendeathrow.skills.skill_tree.helper.ISkillCat;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class CombatBase extends SkillTreeBase implements ISkillCat
{
	private ArrayList weapon_List;
	private static final HashMap<Class, HashMap> entityDifficulty = new HashMap<Class, HashMap>();
	protected Entity lastHit;

	public CombatBase(SkillTrackerData tracker) 
	{
		super(tracker);
		weapon_List = new ArrayList();
		registerWeapons();
	}
	
	@Override
	public String getCatLocName() 
	{
		return "skill.cat.combat";
	}

	@Override
	public String getCatULN() 
	{
		return "combat_skill";
	}

	@Override
	public String getCatDescription() 
	{
		return "null";
	}

	public ArrayList getWeaponList()
	{
		return this.weapon_List;
	}
	
	public void registerWeapon(Class clazz)
	{
		this.weapon_List.add(clazz);
	}
	
	public void registerWeapons()
	{
		
	}
	
	public Boolean CorrectWeapon(EntityPlayer player)
	{
		ItemStack heldItem = player.getHeldItem();
		if(heldItem != null && this.weapon_List.contains(heldItem.getItem().getClass())) return true;
		
		return false;
	}
	
	public float getParrySkill(Entity target)
	{
		if(target instanceof EntityPlayer)
		{
			SkillTreeBase parry = SkillTrackerData.get((EntityPlayer) target).GetSkillByID("parry");
			return parry != null ? parry.getSkillLevel() : 0;
		}
		else if (target instanceof EntityMob)
		{
			//TODO Give Mobs a Defense
			return EntityMobsExt.get((EntityMob)target).parry;
		}
		else if(target instanceof EntityAnimal)
		{
			return EntityAnimalExt.get((EntityAnimal)target).parry;
		}
		
		return 0;
	}

	@Override
	public void onEvent(Object event) 
	{
		

	}
	
	private float bonusXP()
	{
		
		return 0F;
	}
	

}
