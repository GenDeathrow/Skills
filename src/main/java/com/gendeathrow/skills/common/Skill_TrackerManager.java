package com.gendeathrow.skills.common;

import java.util.HashMap;
import java.util.Iterator;

import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class Skill_TrackerManager 
{

	public static HashMap<String,SkillTrackerData> skillTrackerList = new HashMap<String, SkillTrackerData>();
	
	
	public void updateTracker(EntityPlayer entity)
	{
		

		 
	}
	
	public void syncPlayerSkills()
	{
		
	}

	public static SkillTrackerData lookupTracker(EntityPlayer entity) 
	{
		if(skillTrackerList.containsKey(entity.getName()))
		{
			return skillTrackerList.get(entity.getName());	
		}
		return null;
	}

	public static void addToManager(SkillTrackerData skillTrack) 
	{
		skillTrackerList.put(skillTrack.trackedEntity.getName(), skillTrack);
	}

	public static void syncMultiplayerTracker(SkillTrackerData skillTrack) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void saveTracker(SkillTrackerData tracker)
	{
		System.out.println("Saving Tracker:"+ tracker.trackedEntity.getName());
		NBTTagCompound nbt = tracker.trackedEntity.getEntityData();
		
		NBTTagCompound skillzTag = new NBTTagCompound();
		
		Iterator<SkillTreeBase> it = tracker.PlayerSkills.iterator();
		
		while(it.hasNext())
		{
			SkillTreeBase skill = it.next();
		
			NBTTagCompound skillNBT = new NBTTagCompound();
			
			skill.writeNBT(skillNBT);
			
			skillzTag.setTag(skill.getULN(), skillNBT);
		}
		
		nbt.setTag("SkillTree", skillzTag);
	}
	

}
