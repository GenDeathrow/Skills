package com.gendeathrow.skills.skill_tree.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.gendeathrow.skills.common.SkillDifficulty;
import com.gendeathrow.skills.common.SkillTrackerData;
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
	protected EntityPlayer trackedEntity;

	public SkillTreeBase() {

		this.current = 0;
		this.max = 100;
		this.lock = false;
		this.unlearn = false;
		this.gain = 0.1;
		this.success = 0;
		this.markSave = false;
		this.suspendGain = false;
		//this.trackedEntity = trackedEntity;
	}

	public abstract String getLocName();

	public abstract String getULN();
	
	public abstract String getCat();

	public abstract String getDescription();

	/**
	 * will return true if marked to save, but will reset markedSave to false
	 * @return
	 */
	public boolean markedDirty()
	{
		boolean bool = this.markSave;
		this.markSave = false;
		return bool;
	}
	
	/**
	 * Mark this for saving
	 */
	private void markDirty(){this.markSave = true;}

	/** 
	 * returns current skill level
	 * @return
	 */
	public float getSkillLevel(){return this.current;}

	public void increaseSkill(double value) 
	{
		this.current += value;
		if (this.current > this.max)
		{
			this.current = this.max;
		}
		this.markSave = true;
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
	
	/** 
	 * Returns if Skill is locked
	 * @return
	 */
	public boolean isLocked()
	{
		return this.lock;
	}
	
	/**
	 * This will calculate if the player gains a skill point based off random chance with success and chance
	 * 
	 * @param player
	 * @param success
	 * @param chance
	 * @return
	 */
	public double calculateGainNoDiff(EntityPlayer player, int success)
	{
		return calculateGainNoDiff(player, success , .5f);
	}
	/**
	 * This will calculate if the player gains a skill point based off random chance with success and chance
	 * 
	 * @param player
	 * @param success
	 * @param chance
	 * @return
	 */
	public double calculateGainNoDiff(EntityPlayer player, int success , float chance)
	{
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
			System.out.println("Gained Skill point:"+ this.current);
			if(!this.suspendGain) this.increaseSkill(this.gain);
			else {System.out.println("Suspended to change skill"); this.suspendGain = true;}
		}
		
		this.current = MathHelper.round(this.current, 2);
		return formula;
	}
	/**
 	 * This will calculate if the player gains a skill point based off random chance with success and chance
	 * @param player
	 * @param difficulty (Skill Difficulty)
	 * @return
	 */
	public double calculateGain(EntityPlayer player,SkillDifficulty difficulty)
	{		
		if(this.lock || this.current >= 100 || this.unlearn) return 0;
		
		double chance = getChance(difficulty);
		System.out.println("Chance to Mine:"+ (chance*100)+"%");
	
		this.success = this.getSuccess(difficulty, chance);
		
		
		int totalcap = SKSettings.totalSkillCap;
		float gainfactor = SKSettings.gainFactor;
		float failurefactor = SKSettings.failure_factor;
		float total = SkillTrackerData.getTotalSkillPoints(player);
		
		
		System.out.println("totalcap:"+ totalcap + " totalskillpoints:" + total +" gainfactor:"+ gainfactor + " failure:"+ failurefactor);
		System.out.println("maxskill:"+ this.max + " curentskill:"+ this.current);
		
		double formula = ((totalcap-total) / totalcap / 4 + (this.max - this.current) / this.max / 4 + ((1.0 - chance) * (success +(1-success) * failurefactor)) / 2) * gainfactor;
		
		if(formula < 0 ) formula = 0;
		else if( formula > 1) formula = 1;
		
		Random randomGenerator = new Random();
		double number = randomGenerator.nextDouble();
		System.out.println("Attempting to gain skill:"+ formula +" > " + number);
		if(formula > number)
		{
			System.out.println("Gained Skill point:"+ this.current);
			if(!this.suspendGain) this.increaseSkill(this.gain);
			else {System.out.println("Suspended to change skill"); this.suspendGain = true;}
		}
		
		this.current = MathHelper.round(this.current, 2);
		return formula;
	}
	// This is only for 
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
	public int getSuccess(SkillDifficulty skdiff, double chance)
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
		return success;
	}
	
	/**
	 * Returns min lvl to use skill
	 * @param skdiff
	 * @param bonus
	 * @return
	 */	
	public boolean getMinLvl(SkillDifficulty skdiff)
	{
		return getMinLvl(skdiff,0);
	}
	/**
	 * Returns min lvl to use skill
	 * @param skdiff
	 * @param bonus
	 * @return
	 */
	public boolean getMinLvl(SkillDifficulty skdiff,int bonus)
	{
		//Bonus will be armors.
		//int bonus = 0;
		
		int minlevel = (int) (skdiff.difficulty - bonus);
		
		return minlevel <= this.current ? true : false;
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
	
	public boolean isCorrectSkill(Block block)
	{
		return SkillDifficulty.hasBlockDifficulty(block.getUnlocalizedName(), this.getULN()); 
	}
	
	public void doBonusDrops(HarvestDropsEvent event, float dropChance)
	{
		
		List<ItemStack> dropList = new ArrayList();
		System.out.println("DropListB:"+ event.drops.size());
		
		Iterator<ItemStack> it = event.drops.iterator();
		Random rand = new Random();
		ItemStack item;
		while(it.hasNext())
		{
			item = it.next();
			System.out.println("increase items:"+ item.getDisplayName());
			if(rand.nextFloat() < dropChance)
			{
				dropList.add(item.copy());
			}
		}
		event.drops.addAll(dropList);
		System.out.println("DropListA:"+ event.drops.size());

	}

	public float getBonusFactor(int startAt, int forEach, double gainAmt)
	{
		return (float) (((this.current - startAt)/forEach)*gainAmt);
	}
	public abstract void onEvent(Object event);

}
