package com.gendeathrow.skills.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.entity.projectile.SK_FishHook;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;

public class SkillTrackerData 
{
	public EntityPlayer trackedEntity;
	public ArrayList<SkillTreeBase> PlayerSkills;
	public SK_FishHook fishingEntity;

	public SkillTrackerData(EntityPlayerMP entity) throws InstantiationException, ReflectiveOperationException, Exception, Throwable 
	{
		this.trackedEntity = entity;
		this.PlayerSkills = new ArrayList<SkillTreeBase>();
		this.fishingEntity = null;
		

		
		HashMap<String, Class<?>> SkillList = SkillTree_Manager.instance.SkillList;

		Iterator<Entry<String, Class<?>>> it = SkillList.entrySet().iterator();
		
		while(it.hasNext())
		{
			try
			{
				
				Class clazz = it.next().getValue();
				Constructor<SkillTreeBase> ctor = clazz.getConstructor(SkillTrackerData.class);
				SkillTreeBase object = (SkillTreeBase)ctor.newInstance(this);
				this.PlayerSkills.add(object);
			} catch (InvocationTargetException ex) 
			{ 
				Skillz.logger.log(Level.ERROR, "oops!" + ex.getCause());
			}
		}

		//TODO DEBUG PART
		Iterator<SkillTreeBase> ti = this.PlayerSkills.iterator();
		
		while(ti.hasNext())
		{
			SkillTreeBase skill = ti.next();
			
			System.out.println(skill.getLocName() + skill.getULN() + skill.getDescription());
			
		}
		
	}

	public void sendEvent(Object event)
	{

		
		if(Skillz.proxy.isClient()) {System.out.println("This is client");}
		if(!Skillz.proxy.isClient()) {System.out.println("This is Server");}
		Iterator<SkillTreeBase> ti = this.PlayerSkills.iterator();
		
		boolean markedForSave = false;
		
		while(ti.hasNext())
		{
			SkillTreeBase skill = ti.next();
			double prelvl = skill.getSkillLevel();
			
			skill.onEvent(event);
			
			if(skill.markedDirty()) 
			{
				markedForSave = true;
				Skill_TrackerManager.updateTracker(this);
			}
			
			if(skill.hasLvlUp(prelvl))
			{
				this.trackedEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "("+ skill.getLocName() +") "+ "+1 Leveled Up to "+ ((long)skill.getSkillLevel())));
			}
		}	
		
		if(markedForSave) Skill_TrackerManager.saveTracker(this);
	}
	
	public static float getTotalSkillPoints(EntityPlayer player)
	{
		SkillTrackerData tracker = Skill_TrackerManager.lookupTracker(player);
		
		float total = 0; 
		Iterator<SkillTreeBase> ti = tracker.PlayerSkills.iterator();
		
		while(ti.hasNext())
		{
			SkillTreeBase skill = ti.next();
			total += skill.getSkillLevel();
		}
		return total;
	}
	public NBTTagCompound loadNBTTags() 
	{
		NBTTagCompound nbt = this.trackedEntity.getEntityData();
		NBTTagCompound skillTreeNBT = nbt.getCompoundTag("SkillTree");
		Iterator<SkillTreeBase> it = this.PlayerSkills.iterator();
		
		while(it.hasNext())
		{
			SkillTreeBase skill = it.next();
			
			NBTTagCompound skillNBT = skillTreeNBT.getCompoundTag(skill.getULN());
			
			skill.readNBT(skillNBT);
			
		}
		
		return nbt;
		
	}
	
	public static void saveNBTTags()
	{
//		NBTTagCompound nbt = this.trackedEntity.getEntityData();
//		
//		Iterator<SkillTreeBase> it = this.PlayerSkills.iterator();
//		
//		while(it.hasNext())
//		{
//			SkillTreeBase skill = it.next();
//			NBTTagCompound skillTag = new NBTTagCompound();
//			
//			skill.WriteNBT(skillTag);
//		
//			nbt.setTag(skill.getULN(), skillTag);
//		}				
	}
	
	
	public SkillTreeBase GetSkillByID(String lookingFor)
	{


		Iterator<SkillTreeBase> it = this.PlayerSkills.iterator();
		
		while(it.hasNext())
		{
			SkillTreeBase skill = it.next();
			
			if(skill.getULN() == lookingFor)
			{
				return skill;
			}
		}
		
		return null;
		
		
	}
}
