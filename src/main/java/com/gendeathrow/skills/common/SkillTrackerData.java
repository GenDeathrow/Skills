package com.gendeathrow.skills.common;

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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import org.apache.logging.log4j.Level;

import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.entity.projectile.SK_FishHook;
import com.gendeathrow.skills.network.PacketDispatcher;
import com.gendeathrow.skills.network.client.SyncPlayersSkillPropsMessage;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;


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
		Iterator<SkillTreeBase> ti = this.PlayerSkills.iterator();
		
		while(ti.hasNext())
		{
			SkillTreeBase skill = ti.next();
			
			System.out.println(skill.getLocName() +" - '"+ skill.getULN() +"' - "+ skill.getDescription());
			
		}
		
	}

	public void sendEvent(Entity entity, Object event)
	{
		
		Iterator<SkillTreeBase> ti = this.PlayerSkills.iterator();
		
		boolean markedForSave = false;
		
		while(ti.hasNext())
		{
			SkillTreeBase skill = ti.next();
			double prelvl = skill.getSkillLevel();
			
			skill.onEvent(event);

			if(skill.hasLvlUp(prelvl))
			{
				this.trackedEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "("+ skill.getLocName() +") "+ "+1 Leveled Up to "+ ((long)skill.getSkillLevel())));
			}
			
			if(skill.markedDirty()) 
			{
				markedForSave = true;
				//Skill_TrackerManager.updateTracker(this);
			}
		}
		
		if (entity instanceof EntityPlayerMP && markedForSave) 
		{
			Skillz.logger.info("Data Changed Updating Client");
			PacketDispatcher.sendTo(new SyncPlayersSkillPropsMessage((EntityPlayer) entity), (EntityPlayerMP) entity);
		}	
/*
		if(markedForSave) Skill_TrackerManager.saveTracker(this);
		*/
	}
	
	public static float getTotalSkillPoints(EntityPlayer player)
	{
		//SkillTrackerData tracker = Skill_TrackerManager.lookupTracker(player);
		
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
	
	/*
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
	*/
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
	public void saveNBTData(NBTTagCompound compound) {

		NBTTagCompound nbt = new NBTTagCompound();
		
		System.out.println("Saving Tracker:"+ this.trackedEntity.getName());
		//NBTTagCompound nbt = tracker.trackedEntity.getEntityData();
		
		NBTTagCompound skillzTag = new NBTTagCompound();
		
		Iterator<SkillTreeBase> it = this.PlayerSkills.iterator();
		
		while(it.hasNext())
		{
			SkillTreeBase skill = it.next();
		
			NBTTagCompound skillNBT = new NBTTagCompound();
			
			skill.writeNBT(skillNBT);
			
			skillzTag.setTag(skill.getULN(), skillNBT);
		}
		
		nbt.setTag("SkillTree", skillzTag);	
		
		compound.setTag(EXT_PROP_NAME, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		NBTTagCompound skillTreeNBT = nbt.getCompoundTag("SkillTree");
		Iterator<SkillTreeBase> it = this.PlayerSkills.iterator();
		
		while(it.hasNext())
		{
			SkillTreeBase skill = it.next();
			
			NBTTagCompound skillNBT = skillTreeNBT.getCompoundTag(skill.getULN());
			
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
//		inventory.copy(props.inventory);
//		player.getDataWatcher().updateObject(MANA_WATCHER, props.getCurrentMana());
//		maxMana = props.maxMana;
//		manaRegenTimer = props.manaRegenTimer;
	}
}
