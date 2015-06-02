package com.gendeathrow.skills.skill_tree.resource_gathering;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class LumberJackSkill extends ResourceGatheringBase implements ISkill
{


	public LumberJackSkill(SkillTrackerData tracker)
	{
		super(tracker);
	}

	Boolean noDrops;
	BlockPos lastblock;
	
	@Override
	public String LocalizedName() 
	{
		return "skill.lumberjacking.name";
	}

	@Override
	public String ULN() 
	{
		return "lumberjacking";
	}

	@Override
	public String Description() 
	{
		return "Null";
	}

	@Override
	public void onEvent(Object event) 
	{
		if(event instanceof PlayerEvent.BreakSpeed)
		{
			PlayerEvent.BreakSpeed newevent = (PlayerEvent.BreakSpeed)event;
			if(!this.isCorrectSkill(newevent.state, this.getULN())) return;
			
			float bonusSpeed = this.getBonusFactor(50, 10, .05);
			bonusSpeed = bonusSpeed < 0 ? 0 : bonusSpeed;
			newevent.newSpeed += bonusSpeed;		
		}else if(event instanceof BlockEvent.BreakEvent)
		{
			BlockEvent.BreakEvent newevent = (BlockEvent.BreakEvent)event;
			if(!this.isCorrectSkill(newevent.state, this.getULN())) return;
			
				this.lastblock = newevent.pos;
				this.doBlockBreak(newevent);

		}
		else if(event instanceof BlockEvent.HarvestDropsEvent)
		{
			BlockEvent.HarvestDropsEvent newevent = (BlockEvent.HarvestDropsEvent)event;
			if(!this.isCorrectSkill(newevent.state, this.getULN())) return;
		
			BlockPos pos = newevent.pos;
			if(this.success == 0 && pos == this.lastblock)
			{
				newevent.dropChance = 0f;
			}
			else if(this.getSkillLevel() >= 70)
			{
				float bonusDrops = this.getBonusFactor(70, 10, .05);
				bonusDrops = bonusDrops < 0 ? 0 : bonusDrops;
				this.doBonusDrops(newevent, bonusDrops);
			}
		}
	}
	

	private double getChanceNoDiff(EntityPlayer player)
	{
		ItemStack inhand = player.getHeldItem();
	
		double toolModifirer = this.getToolModifirer(inhand);
		double skModifier = this.getBonusFactor(50, 2, .01);
		double chance = .5 + skModifier + toolModifirer;
		
		System.out.println("LumberJacking Chace:"+ chance +" (Tool:"+ toolModifirer +")(SkillMod:"+skModifier+")");
		return chance;
	}
	
	// Move to another area
	private double getToolModifirer(ItemStack heldItem)
	{
		if(heldItem == null) return -0.08D;
		else if(heldItem.getItem() == Items.wooden_axe) return -0.06D;
		else if(heldItem.getItem() == Items.stone_axe) return -0.04D;
		else if(heldItem.getItem() == Items.iron_axe) return 0D;
		else if(heldItem.getItem() == Items.golden_axe) return 0.04D;
		else if(heldItem.getItem() == Items.diamond_axe) return 0.06D;
		else return -0.08;
	}

	private void doBlockBreak(BreakEvent event)
	{
		String id = event.state.getBlock().getUnlocalizedName();

		this.getSuccess(getChanceNoDiff(event.getPlayer()));
		this.calculateGain(event.getPlayer(), this.success);
	}
}
