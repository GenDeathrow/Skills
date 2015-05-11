package com.gendeathrow.skills.skill_tree.resource_gathering;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import com.gendeathrow.skills.common.SkillDifficulty;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class LumberJackSkill extends SkillTreeBase
{

	BlockPos lastblock;
	
	@Override
	public String getLocName() 
	{
		return StatCollector.translateToLocal("skill.lumberjacking.name");
	}

	@Override
	public String getULN() 
	{
		return "lumberjacking";
	}

	@Override
	public String getCat() 
	{
		return StatCollector.translateToLocal("skill.cat.resources");
	}

	@Override
	public String getDescription() 
	{
		return "Null";
	}

	@Override
	public void onEvent(Object event) 
	{
		if(event instanceof PlayerEvent.BreakSpeed)
		{
			PlayerEvent.BreakSpeed newevent = (PlayerEvent.BreakSpeed)event;
			if(!this.isCorrectSkill(newevent.state.getBlock())) return;
			
			float bonusSpeed = (float) (((this.current - 50)/10)*.05);
			bonusSpeed = bonusSpeed < 0 ? 0 : bonusSpeed;
			newevent.newSpeed += bonusSpeed;		
		}else if(event instanceof BlockEvent.BreakEvent)
		{
			BlockEvent.BreakEvent newevent = (BlockEvent.BreakEvent)event;
			if(!this.isCorrectSkill(newevent.state.getBlock())) return;
			
			this.lastblock = newevent.pos;
			this.doBlockBreak(newevent);
		
		}
		else if(event instanceof BlockEvent.HarvestDropsEvent)
		{
			BlockEvent.HarvestDropsEvent newevent = (BlockEvent.HarvestDropsEvent)event;
			if(!this.isCorrectSkill(newevent.state.getBlock())) return;
		
			BlockPos pos = newevent.pos;
			if(this.success == 0 && pos == this.lastblock)
			{
				newevent.dropChance = 0f;
			}
			
			float bonusDrops = (float) (((this.current - 70)/10)*.05);
			bonusDrops = bonusDrops < 0 ? 0 : bonusDrops;
		}
	}

	private void doBlockBreak(BreakEvent event)
	{
		String id = event.state.getBlock().getUnlocalizedName();
		System.out.println("Block Name:"+ id);
		SkillDifficulty difficulty = SkillDifficulty.getBlockDifficulty(id);
		if(difficulty == null) return;		
		System.out.println("Block Difficulty:"+ difficulty.difficulty);
	
		this.calcuateGain(event.getPlayer(), difficulty);
	}
}
