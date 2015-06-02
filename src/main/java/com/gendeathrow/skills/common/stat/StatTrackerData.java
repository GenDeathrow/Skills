package com.gendeathrow.skills.common.stat;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.entity.projectile.SK_FishHook;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class StatTrackerData implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "StatTracker";


	public EntityPlayer trackedEntity;
	public int Strength;
	public int intelligent;
	public int Dexterity;
	public int Wisdom;
	public int Constitution;
	
	public StatTrackerData(EntityPlayer entity)   
	{
		this.trackedEntity = entity;
		
		this.Strength = 0;
		this.intelligent = 0;
		this.Dexterity = 0;
		this.Wisdom = 0;
		this.Constitution = 0;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{

		NBTTagCompound nbt = new NBTTagCompound();
		
		System.out.println("Saving Stats:"+ this.trackedEntity.getName());
		
		NBTTagCompound skillzTag = new NBTTagCompound();
		
		nbt.setInteger("Strength", this.Strength);
		nbt.setInteger("Intelligent", this.intelligent);
		nbt.setInteger("Dexterity", this.Dexterity);
		nbt.setInteger("Wisdom", this.Wisdom);
		nbt.setInteger("Constitution", this.Constitution);
		
		compound.setTag(EXT_PROP_NAME, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		
		this.Strength = nbt.getInteger("Strength");
		this.intelligent = nbt.getInteger("Intelligent");
		this.Dexterity = nbt.getInteger("Dexterity");
		this.Wisdom = nbt.getInteger("Wisdom");
		this.Constitution = nbt.getInteger("Constitution");	
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub
		
	}
	
	public static void register(EntityPlayer player) throws InstantiationException, ReflectiveOperationException, Exception, Throwable
	{
		player.registerExtendedProperties(StatTrackerData.EXT_PROP_NAME, new StatTrackerData(player));
	}
	

	/**
	 * Returns ExtendedPlayer properties for player
	 */
	public static final StatTrackerData get(EntityPlayer player) 
	{
		return (StatTrackerData) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	/**
	 * Copies additional player data from the given ExtendedPlayer instance
	 * Avoids NBT disk I/O overhead when cloning a player after respawn
	 */
	
	public void copy(StatTrackerData stats) 
	{
		this.Strength = stats.Strength;
		this.intelligent = stats.intelligent;
		this.Dexterity = stats.Dexterity;
		this.Wisdom = stats.Wisdom;
		this.Constitution = stats.Constitution;
	}
	
}
