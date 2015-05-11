package com.gendeathrow.skills.common;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;

public class SkillTrackerData 
{
	public EntityPlayer trackedEntity;
	public ArrayList<SkillTreeBase> PlayerSkills;

	public SkillTrackerData(EntityPlayerMP entity) throws InstantiationException, ReflectiveOperationException, Exception, Throwable 
	{
		this.trackedEntity = entity;
		this.PlayerSkills = new ArrayList<SkillTreeBase>();
		
		HashMap<String, Class<?>> SkillList = SkillTree_Manager.instance.SkillList;

		Iterator<Entry<String, Class<?>>> it = SkillList.entrySet().iterator();
		
		while(it.hasNext())
		{
			Class clazz = it.next().getValue();
			//clzz = SkillList.
			Constructor<?> ctor = clazz.getConstructor();
			SkillTreeBase object = (SkillTreeBase)ctor.newInstance();
			
			this.PlayerSkills.add(object);
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
		Iterator<SkillTreeBase> ti = this.PlayerSkills.iterator();
		
		boolean markedForSave = false;
		
		while(ti.hasNext())
		{
			SkillTreeBase skill = ti.next();			
			skill.onEvent(event);
			if(skill.markedDirty()) markedForSave = true;
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
	public void loadNBTTags() 
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
