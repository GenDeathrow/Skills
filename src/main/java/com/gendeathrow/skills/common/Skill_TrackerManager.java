package com.gendeathrow.skills.common;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.network.PacketSkillz;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class Skill_TrackerManager 
{

	public static HashMap<String,SkillTrackerData> skillTrackerList = new HashMap<String, SkillTrackerData>();
	
	
	public static void updateTracker(SkillTrackerData tracker)
	{
		
		if(tracker == null)
		{
			return;
		}
		
		if(Skillz.proxy.isClient() && Minecraft.getMinecraft().isIntegratedServerRunning())
		{
			if(Minecraft.getMinecraft().isGamePaused() && !Skillz.proxy.isOpenToLAN())
			{
				return;
			}
		}
		
		if(!Skillz.proxy.isClient() || Skillz.proxy.isOpenToLAN())
		{
			syncMultiplayerTracker(tracker);
		}
		 
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

	public static void syncMultiplayerTracker(SkillTrackerData tracker) 
	{

		if(!(tracker.trackedEntity instanceof EntityPlayer))
		{
			return;
		}
		
		NBTTagCompound pData = new NBTTagCompound();
		pData.setInteger("id", 0);
		
		//pData.merge(tracker.loadNBTTags());
		
		//Skillz.instance.network.sendToAllAround(new PacketSkillz(pData), new TargetPoint(tracker.trackedEntity.worldObj.provider.getDimensionId(), tracker.trackedEntity.posX, tracker.trackedEntity.posY, tracker.trackedEntity.posZ, 128D));

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

	public static SkillTrackerData lookupTrackerFromUsername(String playerName) 
	{
		return skillTrackerList.get(playerName);
	}
	

}
