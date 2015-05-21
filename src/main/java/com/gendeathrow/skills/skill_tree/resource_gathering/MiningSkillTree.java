package com.gendeathrow.skills.skill_tree.resource_gathering;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.gendeathrow.skills.common.SkillDifficulty;
import com.gendeathrow.skills.common.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.utils.ChatHelper;

public class MiningSkillTree extends SkillTreeBase
{
	
	public MiningSkillTree(SkillTrackerData tracker)
	{
		super(tracker);
	}


	BlockPos lastblock;

	private boolean noDrops;

	@Override
	public String getLocName() {
		return StatCollector.translateToLocal("skill.mining.name");
	}

	@Override
	public String getULN() {
		return "mining";
	}

	@Override
	public String getDescription() {
		return "Null";
	}


	@Override
	public String getCat() {
		return StatCollector.translateToLocal("skill.cat.resources");
	}

	@Override
	public void onEvent(Object event) 
	{
		if(event instanceof PlayerEvent.BreakSpeed)
		{
			PlayerEvent.BreakSpeed newevent = (PlayerEvent.BreakSpeed)event;
			if(!this.isCorrectSkill(newevent.state)) return;
						
			float bonusSpeed = this.getBonusFactor(50, 10, .05);
			bonusSpeed = bonusSpeed < 0 ? 0 : bonusSpeed;
			
			SkillDifficulty difficulty = SkillDifficulty.getBlockDifficulty(newevent.state);
			if(difficulty == null) return;	
			
			int miningBonus = 0;

			if(this.hasMinLvl(difficulty , miningBonus))
			{
				newevent.newSpeed += bonusSpeed;	
			}
			else
			{
				ChatHelper.instance.trySend(newevent.entityPlayer, this, "Your Skill needs to be "+ this.getMinLvl(difficulty , miningBonus));
				newevent.newSpeed = .25f;
			}
			
		}else if(event instanceof BlockEvent.BreakEvent)
		{
			BlockEvent.BreakEvent newevent = (BlockEvent.BreakEvent)event;
			if(!this.isCorrectSkill(newevent.state)) return;

			SkillDifficulty difficulty = SkillDifficulty.getBlockDifficulty(newevent.state);
			if(difficulty == null) return;	

			int miningBonus = 0;
			
			if(this.hasMinLvl(difficulty , miningBonus))
			{
				this.lastblock = newevent.pos;
				this.doBlockBreak(newevent);
			}
			else
			{
				newevent.setExpToDrop(0);
				this.noDrops = true;
			}
		}
		else if(event instanceof BlockEvent.HarvestDropsEvent)
		{
			BlockEvent.HarvestDropsEvent newevent = (BlockEvent.HarvestDropsEvent)event;
			if(!this.isCorrectSkill(newevent.state)) return;
			
			BlockPos pos = newevent.pos;
			if((this.success == 0 && pos == this.lastblock) || this.noDrops == true)
			{
				newevent.dropChance = 0f;
				this.noDrops = false;
			}
			
			// Make sure we only give bonus drops for ores
			 int[] ores = OreDictionary.getOreIDs(new ItemStack(newevent.state.getBlock()));
			 boolean flag = false;
			 for (int id : ores)
			 {
				 String name = OreDictionary.getOreName(id);
				 if(name.startsWith("ore")) 
				 {
					 System.out.println("This is an ore");
					 flag = true; 
				 }
			 }
			
			if(flag && this.getSkillLevel() >= 70)
			{
				float bonusDropChance = this.getBonusFactor(70, 10, .05);
				bonusDropChance = bonusDropChance < 0 ? 0 : bonusDropChance;
				this.doBonusDrops(newevent, bonusDropChance);
			}
		}
	}


	private void doBlockBreak(BreakEvent event)
	{
		SkillDifficulty difficulty = SkillDifficulty.getBlockDifficulty(event.state);
		if(difficulty == null) return;		
		//TODO match meta data
		int meta = event.state.getBlock().getMetaFromState(event.state);
	
		this.calculateGain(event.getPlayer(), difficulty);
	}
}
