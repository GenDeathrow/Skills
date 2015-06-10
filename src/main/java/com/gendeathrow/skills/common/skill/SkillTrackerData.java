package com.gendeathrow.skills.common.skill;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import org.apache.logging.log4j.Level;

import com.gendeathrow.skills.common.stat.StatTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData.PlayerStat;
import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.entity.projectile.SK_FishHook;
import com.gendeathrow.skills.network.PacketDispatcher;
import com.gendeathrow.skills.network.client.SyncPlayersSkillPropsMessage;
import com.gendeathrow.skills.network.client.SyncPlayersStatsPropsMessage;
import com.gendeathrow.skills.skill_tree.helper.ISkillCat;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;


public class SkillTrackerData implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "PlayerTracker";


	public EntityPlayer trackedEntity;
	public ArrayList<SkillTreeBase> PlayerSkills;
	public SK_FishHook fishingEntity;

	public SkillTrackerData(EntityPlayer entity)  throws InstantiationException, ReflectiveOperationException, Exception, Throwable   
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
//		Iterator<SkillTreeBase> ti = this.PlayerSkills.iterator();
//		
//		while(ti.hasNext())
//		{
//			SkillTreeBase skill = ti.next();
//			
//			System.out.println(skill.getLocName() +" - '"+ skill.getULN() +"' - "+ skill.getDescription());
//			
//		}
		
	}

	public void sendEvent(Entity entity, Object event)
	{
		
		Iterator<SkillTreeBase> ti = this.PlayerSkills.iterator();
		
		boolean markedForSave = false;
		
		while(ti.hasNext())
		{
			SkillTreeBase skill = ti.next();

			skill.onEvent(event);
			
			if(skill.isDirty()) 
			{
				markedForSave = true;
			}
		}
		
		if (entity instanceof EntityPlayerMP && markedForSave) 
		{
			Skillz.logger.info("Data Changed Updating Client");
			PacketDispatcher.sendTo(new SyncPlayersSkillPropsMessage((EntityPlayer) entity), (EntityPlayerMP) entity);
		}
		
		if (entity instanceof EntityPlayerMP) 
		{
			markedForSave = false;
			Iterator<Entry<EnumStats, PlayerStat>> StatList = StatTrackerData.get(this.trackedEntity).PlayerStats.entrySet().iterator();
			
			while(StatList.hasNext())
			{
				 PlayerStat stat = StatList.next().getValue();
				 
				 if(stat.isDirty()) markedForSave = true;
			}
			if(markedForSave) PacketDispatcher.sendTo(new SyncPlayersStatsPropsMessage((EntityPlayer) entity), (EntityPlayerMP) entity);
		}

	}
	
	public static float getTotalSkillPoints(EntityPlayer player)
	{
		SkillTrackerData tracker = get(player);
		
		float total = 0; 
		Iterator<SkillTreeBase> ti = tracker.PlayerSkills.iterator();
		
		while(ti.hasNext())
		{
			SkillTreeBase skill = ti.next();
			total += skill.getSkillLevel();
		}
		return total;
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

	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
		saveNBTData(compound, false); 
	}
	
	public void saveNBTData(NBTTagCompound compound, Boolean isNetworkMessage) 
	{
		System.out.println("Saving Tracker:"+ this.trackedEntity.getName());

		NBTTagCompound nbt = new NBTTagCompound();
		Iterator<SkillTreeBase> it = this.PlayerSkills.iterator();
		
		while(it.hasNext())
		{
			SkillTreeBase skill = it.next();
			
			// If network is true, and it is not marked for dirty move to next skill
			//if(isNetworkMessage && !skill.isDirty()) continue;
		
			NBTTagCompound skillNBT = new NBTTagCompound();
			
			skill.writeNBT(skillNBT);
			
			nbt.setTag(skill.getULN(), skillNBT);
		}
		
		compound.setTag(EXT_PROP_NAME, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{
		loadNBTData(compound, false);
	}	

	public void loadNBTData(NBTTagCompound compound, Boolean isNetworkMessage) 
	{
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		Iterator<SkillTreeBase> it = this.PlayerSkills.iterator();
		
		while(it.hasNext())
		{
			SkillTreeBase skill = it.next();
			
			//if(skill instanceof ISkillCat) continue;
			// If network is true, and it is not marked for dirty move to next skill
			//if(isNetworkMessage && !skill.markedDirty()) continue;
			System.out.print(skill.getULN());
			NBTTagCompound skillNBT = nbt.getCompoundTag(skill.getULN());			
			skill.readNBT(skillNBT);
		}
		
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub
		
	}
	
	public static void register(EntityPlayer player) throws InstantiationException, ReflectiveOperationException, Exception, Throwable
	{
		player.registerExtendedProperties(SkillTrackerData.EXT_PROP_NAME, new SkillTrackerData(player));
	}
	

	/**
	 * Returns ExtendedPlayer properties for player
	 */
	public static final SkillTrackerData get(EntityPlayer player) 
	{
		return (SkillTrackerData) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	/**
	 * Copies additional player data from the given ExtendedPlayer instance
	 * Avoids NBT disk I/O overhead when cloning a player after respawn
	 */
	
	public void copy(SkillTrackerData props) 
	{
		PlayerSkills = props.PlayerSkills;
	}
}
