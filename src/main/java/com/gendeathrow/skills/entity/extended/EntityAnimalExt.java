package com.gendeathrow.skills.entity.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityAnimalExt implements IExtendedEntityProperties
{

	public final static String EXT_PROP_NAME = "RPG_Mob_Tracker";
	
	public int parry;

	public EntityAnimalExt(EntityAnimal animal)
	{
//		animal.getAge();
//		animal.getMaxHealth();
		this.parry = 5;
	}
	
	public static void register(EntityAnimal animal)
	{
		animal.registerExtendedProperties(EntityAnimalExt.EXT_PROP_NAME, new EntityAnimalExt(animal));
	}
	
	/**
	 * Returns ExtendedPlayer properties for player
	 */
	public static final EntityAnimalExt get(EntityAnimal animal) 
	{
		return (EntityAnimalExt) animal.getExtendedProperties(EXT_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		
	}

	@Override
	public void init(Entity entity, World world) {
		
	}

}
