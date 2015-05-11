package com.gendeathrow.skills.skill_tree.resource_gathering;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.SkillDifficulty;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class FarmingSkill extends SkillTreeBase
{

	private static final SkillDifficulty useHoe= new SkillDifficulty("useHoe").setDifficulty(0).setSkill("farming");
	private static final SkillDifficulty bonemeal= new SkillDifficulty("bonemeal").setDifficulty(10).setSkill("farming");

	private int hoeWait;
	private boolean canGain;
	private long lastGain;
	
	@SideOnly(Side.CLIENT)
	private ArrayList hoeUsed = new ArrayList();
	
	public FarmingSkill()
	{
		super();
		this.lastGain = 0;
		this.hoeWait = 1000;
		this.canGain = true;
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
			
			System.out.println(block.getBlock().getLocalizedName());
			
			System.out.println("does -> "+ (block.getBlock() != Blocks.grass || block.getBlock() != Blocks.dirt));
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
			
			this.calcuateGain(newEvent.entityPlayer, bonemeal);
			
			if(this.success == 0)
			{
				newEvent.setCanceled(true);
			}
		}else if(event instanceof PlayerInteractEvent)
		{
			PlayerInteractEvent newEvent = (PlayerInteractEvent) event;
			
			if(newEvent.pos == null) return;
			IBlockState block = newEvent.world.getBlockState(newEvent.pos);
			
			if(block.getBlock() instanceof IPlantable)
			{
				
				//System.out.println("found plant:"+ block.getBlock().getLocalizedName());
				
				
			}
		}
		
		
	}
	
	
	private void useHoe(UseHoeEvent event)
	{
		
		if(this.lastGain == 0) this.lastGain = Minecraft.getSystemTime() - this.hoeWait;
		
		System.out.println("Minecraft Time:"+ Minecraft.getSystemTime());
		
		System.out.println("Last Gain:"+ this.lastGain);
		long flag = (this.lastGain + this.hoeWait);
		
		System.out.println("Flag:"+ flag +"bool:"+ (Minecraft.getSystemTime() >= flag) );
		
		
		if(Minecraft.getSystemTime() >= flag)
		{
			this.suspendGain = false;
			this.lastGain = Minecraft.getSystemTime();
		}else{
			this.suspendGain = true;
		}
		
		this.calcuateGain(event.entityPlayer, useHoe);
		
		if(this.success == 0)
		{
			event.setCanceled(true);
		}

	}

}
