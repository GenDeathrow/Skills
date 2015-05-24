package com.gendeathrow.skills.skill_tree.resource_gathering;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.SkillDifficulty;
import com.gendeathrow.skills.common.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class FarmingSkill extends SkillTreeBase
{

	private static final SkillDifficulty useHoe= new SkillDifficulty("useHoe").setDifficulty(0).setSkill("farming");
	private static final SkillDifficulty bonemeal= new SkillDifficulty("bonemeal").setDifficulty(10).setSkill("farming");
	private static final SkillDifficulty harvest= new SkillDifficulty("harvest").setDifficulty(10).setSkill("farming");

	private BlockPos lastBlock;
	
	@SideOnly(Side.CLIENT)
	private ArrayList hoeUsed = new ArrayList();
	
	public FarmingSkill(SkillTrackerData tracker)
	{
		super(tracker);
		this.lastGain = 0;
	}
	
	@Override
	public String getLocName() 
	{
		return StatCollector.translateToLocal("skill.farming.name");
	}

	@Override
	public String getULN() 
	{
		return "farming";
	}

	@Override
	public String getCat() 
	{
		return StatCollector.translateToLocal("skill.cat.resources");
	}

	@Override
	public String getDescription() 
	{
		return "null";
	}

	@Override
	public void onEvent(Object event) 
	{
		
		if(event instanceof UseHoeEvent)
		{
			UseHoeEvent newEvent = (UseHoeEvent) event;
			
			IBlockState block = newEvent.world.getBlockState(newEvent.pos);
			
			if(block.getBlock() == Blocks.grass) 
			{
				this.useHoe(newEvent);
			}
			else if(block.getBlock() == Blocks.dirt)
			{
				this.useHoe(newEvent);
			}
						
		
		}
		else if(event instanceof BonemealEvent)
		{
			BonemealEvent newEvent = (BonemealEvent) event;
			IBlockState block = newEvent.world.getBlockState(newEvent.pos);

			if(block instanceof BlockCrops)
			{
				BlockCrops crop = (BlockCrops)block.getBlock();
				
				if(crop.canUseBonemeal(null, null, null, null))
				{
					useBonemeal(newEvent);		
				}
			}
			
		}else if(event instanceof PlayerInteractEvent){}
		else if(event instanceof BreakEvent)
		{
			BreakEvent newEvent = (BreakEvent) event;
			if(newEvent.pos == null) return;
			
			IBlockState block = newEvent.world.getBlockState(newEvent.pos);
	
			if(block.getBlock() instanceof BlockCrops)
			{
				BlockCrops crop = (BlockCrops)block.getBlock();
				if(!crop.canGrow(null, null, block, false))
				{
					if(randomKillPlant(harvest))
					{
						System.out.println("Kill Plant");
						newEvent.setCanceled(true);
						newEvent.world.setBlockState(newEvent.pos, Blocks.deadbush.getDefaultState());
						newEvent.world.markBlockForUpdate(newEvent.pos);
					}
					else
					{
						onHarvest(newEvent);
						this.lastBlock = newEvent.pos;
					}

				}
				
			}	
			
		}else if(event instanceof HarvestDropsEvent)
		{
			HarvestDropsEvent newEvent = (HarvestDropsEvent) event;
			System.out.println("Harvest Drop event");
			if(newEvent.pos != this.lastBlock) return;
			 
			if(this.success == 0)
			{
				newEvent.dropChance = .5F;
			}
			else{
				this.doBonusDrops(newEvent, .5f);	
			}
		}else if(event instanceof PlayerEvent.HarvestCheck)
		{
			PlayerEvent.HarvestCheck newEvent= (PlayerEvent.HarvestCheck)event; 
		}
		
		
	}
	
	private boolean randomKillPlant(SkillDifficulty skdiff)
	{
		Random rand = new Random();
		
		double chance = this.getChance(skdiff) * .1;
		
		if(rand.nextDouble() <= chance)
		{
			return true;
		}
		//float chance = this.current 
		
		return false;
	}
	
	private void useHoe(UseHoeEvent event)
	{
		
		if(!this.hasMinLvl(useHoe)) return;
		
		
		this.calculateGain(event.entityPlayer, useHoe);
		
		if(this.success == 0)
		{
			event.setCanceled(true);
		}

	}
	
	private void useBonemeal(BonemealEvent event)
	{
		if(!this.hasMinLvl(bonemeal)) return;

		this.calculateGain(event.entityPlayer, bonemeal);
		
		if(this.success == 0)
		{
			event.setCanceled(true);
		}
	}
	
	private void onHarvest(BreakEvent event)
	{
		
		if(!this.hasMinLvl(harvest)) return;
		
		this.calculateGain(event.getPlayer(), harvest);
		
		if(this.success == 0)
		{
			event.setCanceled(true);
		}
	}

}
