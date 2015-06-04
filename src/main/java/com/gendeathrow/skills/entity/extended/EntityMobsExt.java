package com.gendeathrow.skills.entity.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.gendeathrow.skills.common.stat.StatTrackerData;

public class EntityMobsExt implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "RPG_Mob_Tracker";
	
	public int parry;
	public int attack;
	
	public EntityMobsExt(EntityMob mob)
	{
//		mob.getAge();
//		mob.getMaxHealth();
		this.parry = 25;
		this.attack = 25;
	}
	
	public static void register(EntityMob mob)
	{
		mob.registerExtendedProperties(EntityMobsExt.EXT_PROP_NAME, new EntityMobsExt(mob));
	}
	
	/**
	 * Returns ExtendedPlayer properties for player
	 */
	public static final EntityMobsExt get(EntityMob mob) 
	{
		return (EntityMobsExt) mob.getExtendedProperties(EXT_PROP_NAME);
	}
	
	/**
	 * Copies additional player data from the given ExtendedPlayer instance
	 * Avoids NBT disk I/O overhead when cloning a player after respawn
	 */
	
	public void copy(EntityMobsExt stats) 
	{
		
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{

	}

	@Override
	public void init(Entity entity, World world) 
	{
		
	}

}
