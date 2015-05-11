package com.gendeathrow.skills.skill_tree.helper;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

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

	public SkillTreeBase() {

		this.current = 0;
		this.max = 100;
		this.lock = false;
		this.unlearn = false;
		this.gain = 0.1;
		this.success = 0;
		this.markSave = false;
		this.suspendGain = false;
	}

	public abstract String getLocName();

	public abstract String getULN();
	
	public abstract String getCat();

	public abstract String getDescription();
	
	public boolean markedDirty()
	{
		boolean bool = this.markSave;
		this.markSave = false;
		return bool;
	}
	
	private void markDirty()
	{
		this.markSave = true;
	}

	public float getSkillLevel() {
		return this.current;
	}

	public void increaseSkill(double value) {
		this.current += value;
		

		if (this.current > 100)
		{
			this.current = 100;
		}
		this.markSave = true;
	}

	public void decreaseSkill(double value) {
		this.current -= value;
		if (this.current < 0) 
		{
			this.current = 0;
		}
		this.markSave = true;
	}
	
	public boolean isLocked()
	{
		return this.lock;
	}
	
	public void calcuateGain(EntityPlayer player,SkillDifficulty difficulty)
	{		
		if(this.lock || this.current >= 100 || this.unlearn) return;
		
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
			else {System.out.println("Suspended to change skill");}
		}
		
		this.current = MathHelper.round(this.current, 2);
	}
	
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
	
	public double getChance(SkillDifficulty skdiff)
	{
		
		int bonus = 100;
		double offset = .1;
		double chance = ((this.getSkillLevel() - (skdiff.difficulty - (bonus/2)))/bonus);
		
		if(chance > 1) chance =1;
		else if (chance < 0) chance = 0;
	
		return chance;
	}
	
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

	public abstract void onEvent(Object event);
	/*
	public void onAnvilRepair(AnvilRepairEvent event) {
	}

	public void onBoneMealUse(BonemealEvent event) {
	}

	public void onAttackEntity(AttackEntityEvent event) {
	}

	public void onInteractWitEntity(EntityInteractEvent event) {
	}

	public void onHarvest(PlayerEvent.HarvestCheck event) {
	}

	public void BreakSpeed(PlayerEvent.BreakSpeed event) {
	}

	public void onInteract(PlayerInteractEvent event) {
	}

	public void onItemUse(PlayerUseItemEvent event) {
	}

	public void onUseHoe(UseHoeEvent event) {
	}

	public void onLivingAttack(LivingAttackEvent event) {
	}

	public void onFall(LivingFallEvent event) {
	}

	public void onPotionBrewPre(PotionBrewEvent.Pre event) {
	}

	public void onPotionBrewPost(PotionBrewEvent.Post event) {
	}

	public void onBlockBreak(BlockEvent.BreakEvent event) {
	}

	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
	}

	public void onBlockPlaced(BlockEvent.PlaceEvent event) {
	}
	*/
}
