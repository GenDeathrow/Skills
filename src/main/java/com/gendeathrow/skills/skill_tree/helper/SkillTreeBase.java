package com.gendeathrow.skills.skill_tree.helper;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.gendeathrow.skills.common.skill.SkillDifficulty;
import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData.PlayerStat;
import com.gendeathrow.skills.core.SKSettings;
import com.gendeathrow.skills.utils.MathHelper;

public abstract class SkillTreeBase 
{
	protected float current;
	protected float max;
	protected boolean lock;
	protected boolean unlearn;
	private double gain;
	protected int success;
	public boolean markSave;
	protected boolean suspendGain;
	public SkillTrackerData tracker;
	protected long lastGain;
	protected double preGain;
	/**time in ms */
	protected int waitGain;

	public SkillTreeBase(SkillTrackerData tracker) 
	{
		this.current = 0;
		this.max = 100;
		this.lock = false;
		this.unlearn = false;
		this.gain = 0.1;
		this.success = 0;
		this.markSave = false;
		this.suspendGain = false;
		this.tracker = tracker;
		this.lastGain = 0;
		this.waitGain = 5000;
	}

	/**
	 * Get Localized Name of Skill from ISkill
	 * @return
	 */
	public String getLocName()
	{
		return StatCollector.translateToLocal(((ISkill)this).LocalizedName());
	}

	/**
	 * Get UnLocalized Name of Skill from ISkill
	 * @return
	 */
	public String getULN()
	{
		return ((ISkill)this).ULN();
	}

	/**
	 * Get Description of Skill from ISkill
	 * @return
	 */
	public String getDescription()
	{
		return((ISkill)this).Description();
	}

	/**
	 * Get Localized Name of Category from ISkillCat
	 * @return
	 */
	public String getCatLocalizedName()
	{
		return StatCollector.translateToLocal(((ISkillCat)this).getCatLocName());
	}

	public String getCatULN()
	{
		return ((ISkillCat)this).getCatULN();
	}

	
	/**
	 * Get Description from Category from ISkillCat
	 * @return
	 */
	public String getCatDescription()
	{
		return((ISkillCat)this).getCatDescription();
	}
	
	public PlayerStat getPrimarySkill()
	{
		return StatTrackerData.get(this.tracker.trackedEntity).getStatbyEnum(((ISkill)this).PrimaryStat());
	}

	public PlayerStat getSecondarySkill()
	{
		return StatTrackerData.get(this.tracker.trackedEntity).getStatbyEnum(((ISkill)this).SecondaryStat());
	}

	/**
	 * Get a Random skill point number for gaining a skill point
	 * 
	 * 	@param rangeMin
	 * @param rangeMax
	 * @return
	 */
	public double randomGain(double rangeMin, double rangeMax)
	{
		double value;
		
		Random randomGenerator = new Random();
		value = rangeMin + (rangeMax - rangeMin) * randomGenerator.nextDouble();

		return value;
	}

	/**
	 * will return true if marked to save, but will reset markedSave to false
	 * @return
	 */
	public boolean isDirty()
	{
		boolean bool = this.markSave;
		this.markSave = false;
		return bool;
	}
	
	/**
	 * Mark this for saving
	 */
	private void markDirty(){ this.markSave = true; }

	/** 
	 * returns current skill level
	 * @return
	 */
	public float getSkillLevel(){ return this.current; }

	public void increaseSkill(double value) 
	{
		this.current += value;
		if (this.current > this.max)
		{
			this.current = this.max;
		}
		this.markSave = true;
		
		if(this.hasLvlUp(this.current - value))
		{
			this.tracker.trackedEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "("+ this.getLocName() +") "+ "+1 Leveled Up to "+ ((long)this.getSkillLevel())));
		}
	}

	public void decreaseSkill(double value) 
	{
		this.current -= value;
		if (this.current < 0) 
		{
			this.current = 0;
		}
		this.markSave = true;
	}
	
	public void setGain(double newGain)
	{
		this.gain = newGain; 
	}
	
	/**
	 * Sets the time delay that a player can gain a skill
	 * no matter how fast a player uses the skill.
	 * 
	 * Default is 5 sec's
	 * @param newDelay
	 */
	public void setSkillGainDelay(int newDelay)
	{
		this.waitGain = newDelay;
	}
	
	/**
	 * Will check check time of last gain and compare to the wait limit
	 * of the current skill. and will set suspendGain.
	 * 
	 * @return Boolean
	 */
	public boolean checkSkillGainDelay()
	{
		if(MinecraftServer.getCurrentTimeMillis() >= (this.waitGain + this.lastGain)) 
		{
			this.suspendGain = false;
		}else{
			this.suspendGain = true;
		}
		
		return this.suspendGain;
	}
	
	public boolean isLocked(){ return this.lock; }
	
	/**
	 * This will calculate if the player gains a skill point based off random chance with success and chance
	 * 
	 * @param player
	 * @param success
	 * @param chance
	 * @return
	 */
	public double calculateGain(EntityPlayer player, int success)
	{
		return calculateGain(player, success , .5D);
	}

	/**
 	 * This will calculate if the player gains a skill point based off random chance with success and chance
	 * @param player
	 * @param difficulty (Skill Difficulty)
	 * @return
	 */
	public double calculateGain(EntityPlayer player, SkillDifficulty difficulty)
	{		
		double chance = getChance(difficulty);
		this.success = this.getSuccess(chance);
		return calculateGain(player, success, chance);
	}
	/**
	 * This will calculate if the player gains a skill point based off random chance with success and chance
	 * 
	 * @param player
	 * @param success
	 * @param chance
	 * @return
	 */
	public double calculateGain(EntityPlayer player, int success , double chance)
	{
		if (!player.worldObj.isRemote) 
		{
		if(this.lock || this.current >= this.max || this.unlearn) return 0;
				
		int totalcap = SKSettings.totalSkillCap;
		float gainfactor = SKSettings.gainFactor;
		float failurefactor = SKSettings.failure_factor;
		float total = SkillTrackerData.getTotalSkillPoints(player);
		
		double formula = ((totalcap-total) / totalcap / 4 + (this.max - this.current) / this.max / 4 + ((1.0 - chance) * (success +(1-success) * failurefactor)) / 2) * gainfactor;
		
		if(formula < 0 ) formula = 0;
		else if( formula > 1) formula = 1;
		
		Random randomGenerator = new Random();
		double number = randomGenerator.nextDouble();
		System.out.println("Attempting to gain skill:"+ formula +" > " + number);
		if(formula > number)
		{
			if(!this.checkSkillGainDelay()) 
			{
				System.out.println("Gained Skill point:"+ this.current);
				this.lastGain = MinecraftServer.getCurrentTimeMillis();
				this.increaseSkill(this.gain);
			}
			else {System.out.println("Suspended to change skill"); this.suspendGain = true;}
		}
		
		System.out.println("Attempting to Gain Stat");
		
		StatTrackerData playerStats = StatTrackerData.get(this.tracker.trackedEntity);
		playerStats.AttemptStatGain(this.getPrimarySkill(), this.getSecondarySkill());
		
		this.current = MathHelper.round(this.current, 2);
		return formula;
		}
		return 0;
	}

	// This is only for my debuging Gui
	public double DebugFormula(EntityPlayer player,double chance, SkillDifficulty difficulty , int success)
	{
		
		int totalcap = SKSettings.totalSkillCap;
		float gainfactor = SKSettings.gainFactor;
		float failurefactor = SKSettings.failure_factor;
		float total = SkillTrackerData.getTotalSkillPoints(player);
			
		double formula = ((totalcap-total) / totalcap / 4 + (this.max - this.current) / this.max / 4 + ((1.0 - chance) * (success +(1-success) * failurefactor)) / 2) * gainfactor;
		
		if(formula < 0 ) formula = 0;
		else if( formula > 1) formula = 1;
		

		return formula;
	}
	
	/**
	 * Will get a chance to mine based on skill Difficulty
	 * @param skdiff
	 * @return
	 */
	public double getChance(SkillDifficulty skdiff)
	{		
		int bonus = 100;
		double offset = .1;
		double chance = ((this.getSkillLevel() - (skdiff.difficulty - (bonus/2)))/bonus);
		
		if(chance > 1) chance =1;
		else if (chance < 0) chance = 0;
	
		return chance;
	}
	
	/**
	 * Will give a success or fail on using current skill based of a chance and difficulty
	 * @param skdiff
	 * @param chance
	 * @return
	 */
	public int getSuccess(double chance)
	{
		
		int success = 0;
		
		Random randomGenerator = new Random();
		double number = randomGenerator.nextDouble();
		System.out.println("Check Mining Success:"+ Math.round(chance*100) +" > " + Math.round(number*100));
		
		if(chance > number)
		{
			System.out.println("SuccessFully Mined");
			success = 1;
		}
		else System.out.println("Lost Materials");
		this.success = success;
		return success;
	}
	
	/**
	 * Returns min lvl to use skill
	 * @param skdiff
	 * @param bonus
	 * @return
	 */	
	public boolean hasMinLvl(SkillDifficulty skdiff)
	{
		return hasMinLvl(skdiff,0);
	}
	/**
	 * Returns min lvl to use skill
	 * @param skdiff
	 * @param bonus
	 * @return
	 */
	public int getMinLvl(SkillDifficulty skdiff,int bonus)
	{
		int minlevel = (int) (skdiff.difficulty - bonus);
		return minlevel;
	}
	
	public boolean hasMinLvl(SkillDifficulty skdiff,int bonus)
	{
		return (getMinLvl(skdiff, bonus) <= this.current);
	}
	
	
	public void readNBT(NBTTagCompound nbt)
	{
		this.current = nbt.getFloat("skillLevel");
		this.lock =nbt.getBoolean("skillLock");
		this.unlearn = nbt.getBoolean("unlearn");
	}

	public void writeNBT(NBTTagCompound nbt)
	{
		nbt.setFloat("skillLevel", this.getSkillLevel());
		nbt.setBoolean("skillLock", this.isLocked());
		nbt.setBoolean("unlearn", this.unlearn);
	}
	
	public boolean isCorrectSkill(IBlockState state, String skillULN)
	{
		return SkillDifficulty.hasBlockDifficulty(state, skillULN); 
	}
	

	/**
	 * Get a bonus factor depending on start lvl, per each lvl.
	 * 
	 * @param startAt
	 * @param forEach
	 * @param gainAmt
	 * @return
	 */
	public float getBonusFactor(int startAt, int forEach, double gainAmt)
	{
		return (float) (((this.current - startAt)/forEach)*gainAmt);
	}
	
	public abstract void onEvent(Object event);

	/**
	 * Check to see if the player has leveled up their skill a whole number
	 * 
	 * @param prelvl
	 * @return
	 */
	public boolean hasLvlUp(double prelvl) 
	{
		long preParti = (long) prelvl + 1;
		System.out.println("haslvled:"+ this.current +">="+ preParti +"="+ (this.current >= preParti));
		return this.current >= preParti;
	}

}
