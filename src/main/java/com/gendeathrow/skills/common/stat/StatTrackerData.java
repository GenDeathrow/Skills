package com.gendeathrow.skills.common.stat;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.gendeathrow.skills.utils.ChatHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class StatTrackerData implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "StatTracker";


	public EntityPlayer trackedEntity;
	public PlayerStat Strength;
	public PlayerStat intelligent;
	public PlayerStat Dexterity;
	public PlayerStat Wisdom;
	public PlayerStat Constitution;
	
    public HashMap<EnumStats, PlayerStat> PlayerStats;
	
	public StatTrackerData(EntityPlayer entity)   
	{
		this.trackedEntity = entity;
		this.PlayerStats  = new HashMap<EnumStats, PlayerStat>(); 
		
		this.Strength = new PlayerStat("stat.str.name");
		this.intelligent = new PlayerStat("stat.int.name");
		this.Dexterity = new PlayerStat("stat.dex.name");
		this.Wisdom = new PlayerStat("stat.wis.name");
		this.Constitution = new PlayerStat("stat.con.name");
		
		PlayerStats.put(EnumStats.Strength, this.Strength);
		PlayerStats.put(EnumStats.Intelligence, this.intelligent);
		PlayerStats.put(EnumStats.Dexterity, this.Dexterity);
		PlayerStats.put(EnumStats.Wisdom, this.Wisdom);
		//PlayerStats.put(EnumStats.Strength, this.Strength);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{

		NBTTagCompound nbt = new NBTTagCompound();
		
		System.out.println("Saving Stats:"+ this.trackedEntity.getName());
		
		NBTTagCompound skillzTag = new NBTTagCompound();
		
		nbt.setInteger("Strength", this.Strength.getValue());
		nbt.setInteger("Intelligent", this.intelligent.getValue());
		nbt.setInteger("Dexterity", this.Dexterity.getValue());
		nbt.setInteger("Wisdom", this.Wisdom.getValue());
		nbt.setInteger("Constitution", this.Constitution.getValue());
		
		compound.setTag(EXT_PROP_NAME, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		
		this.Strength.setMax(nbt.getInteger("Strength"));
		this.intelligent.setMax(nbt.getInteger("Intelligent"));
		this.Dexterity.setMax(nbt.getInteger("Dexterity"));
		this.Wisdom.setMax(nbt.getInteger("Wisdom"));
		this.Constitution.setMax(nbt.getInteger("Constitution"));	
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
		Strength = stats.Strength;
		intelligent = stats.intelligent;
		Dexterity = stats.Dexterity;
		Wisdom = stats.Wisdom;
		Constitution = stats.Constitution;
	}
	
	public PlayerStat getStatbyEnum(EnumStats statType)
	{
		return null;
	}
	
	public void AttemptStatGain(PlayerStat primary, PlayerStat secondary)
	{
		// If both skills are locked or max out skip
		//if((primary.isLocked && secondary.isLocked) || (primary.value >= primary.max && secondary.value >= secondary.max)) return;
		
		Random rand = new Random();
		
		int roll = rand.nextInt(19) + 1;
	
		System.out.println("Rolling for Stat: "+ roll);
		
		if(roll == 1) // 1/20 chance to gain a skill point
		{
			int chooseStat = (int) (rand.nextFloat() * 100);
			
			System.out.println("Gaining Stat point");
			if(chooseStat <= 75) // 75% chance to gain a stat point
			{
				System.out.println("Increasing Primary Stat" + primary.isLocked +" "+ primary.value +" "+ primary.max);
				if(!primary.isLocked && primary.value < primary.max)
				{
					primary.value += 1;
					ChatHelper.instance.sendMSG(this.trackedEntity, new ChatComponentText(primary.getLocolizedName() +" increased +1 point to "+ primary.value));
				}
				else if(!secondary.isLocked && secondary.value < secondary.max)
				{
					secondary.value += 1;
					ChatHelper.instance.sendMSG(this.trackedEntity, new ChatComponentText(secondary.getLocolizedName() +" increased +1 point to "+ secondary.value));
				}
				
			}
			else
			{
				System.out.println("Increasing Secondary Stat");
				if(!secondary.isLocked && secondary.value < secondary.max)
				{
					secondary.value += 1;
					ChatHelper.instance.sendMSG(this.trackedEntity, new ChatComponentText(secondary.getLocolizedName() +" increased +1 point to "+ secondary.value));
				}
				else if(!primary.isLocked && primary.value < primary.max)
				{
					primary.value += 1;
					ChatHelper.instance.sendMSG(this.trackedEntity, new ChatComponentText(primary.getLocolizedName() +" increased +1 point to "+ primary.value));
				}

			}
		}
	}
	
	
	public class PlayerStat
	{
		private int value;
		private boolean isLocked;
		private String unlocalized;
		private int max;
			
		public PlayerStat(String unLocName)
		{
			this.value = 0;
			this.isLocked = false;
			this.unlocalized = unLocName;
			this.max = 240;
		}
		
		public String getLocolizedName()
		{
			return StatCollector.translateToLocal(this.unlocalized);
		}
		
		public String getULName()
		{
			return this.unlocalized;
		}
		
		public void setMax(int newValue)
		{
			this.max = newValue;
		}
		
		public void increaseStat(int add)
		{
			this.value = this.value + add <= this.max ? this.value + add : this.max;
		}
		
		public void decreaseStat(int subtract)
		{
			this.value = this.value - subtract >= 0 ? this.value - subtract : 0;
		}
		
		public void setValue(int newValue)
		{
			this.value = newValue <= this.max ? newValue >= 0 ? newValue : 0 : this.max ;
		}
		
		public int getValue()
		{
			return this.value;
		}
		
		public int getMax()
		{
			return this.max;
		}
		
	}
}
