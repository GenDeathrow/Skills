package com.gendeathrow.skills.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class BlueprintHelper 
{

	/**
	 * Shortcut to get persistent NBT data contained within the player
	 * @param player
	 * @return
	 */
	public static NBTTagCompound getPersistentNBT(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
	}
	
	/**
	 * Sets the persistent data tag of the given player.
	 * WARNING: Incorrect use could delete important player data. Always use an edited version of the original to ensure important data is maintained
	 * @param player
	 * @param newTags
	 */
	public static void setPersistentNBT(EntityPlayer player, NBTTagCompound newTags)
	{
		player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, newTags);
	}
	
}
