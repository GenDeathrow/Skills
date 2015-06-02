package com.gendeathrow.skills.common.skill;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import com.gendeathrow.skills.core.SKSettings;

public class SkillDifficulty 
{

	public final static HashMap<String, SkillDifficulty> itemRegistry = new HashMap<String, SkillDifficulty>();

	public final static HashMap<String, SkillDifficulty> plantRegistry = new HashMap<String, SkillDifficulty>();

	public String id;
	public double difficulty;
	public boolean alwaysDrop;
	public int maxSkillGain;
	public String skillType;
	public int minLvl;

	public int meta;

	public SkillDifficulty(String id)
	{
		new SkillDifficulty(id, (int) Short.MAX_VALUE);
	}	

	public SkillDifficulty(String id, int meta)
	{
		this.id = id;
		this.meta = meta;
		this.difficulty = 0;
		this.alwaysDrop = false;
		this.maxSkillGain = -1;
		this.skillType = null;		
	}
	
	public SkillDifficulty setDifficulty(double difficulty)
	{
		if(difficulty < 0) difficulty = 0;
		this.difficulty = difficulty;
		return this;
	}
	
	public SkillDifficulty setMaxSkillGain(int skillLevel)
	{
		if (skillLevel < -1) skillLevel = -1;
		this.maxSkillGain = skillLevel;
		return this;
	}
	public SkillDifficulty setAlwaysDrop(boolean bool)
	{
		this.alwaysDrop = true;
		return this;
	}
	
	public SkillDifficulty setSkill(String skillType)
	{
		this.skillType = skillType;
		return this;
	}
	
	public static void registerBlock(Block block, SkillDifficulty skdiff)
	{
		registerBlock(block, (int) Short.MAX_VALUE, skdiff);
	}
	public static void registerBlock(Block block,int meta, SkillDifficulty skdiff)
	{
		String id = block.getUnlocalizedName();
		
		if(meta != Short.MAX_VALUE)
		{
			id = id +":"+ meta;
		}
		
		SKSettings.blockRegistry.put(id, skdiff);
		
		System.out.println("Added "+ id);
	}
	
	public static void registerBlocks()
	{
		System.out.println("Regsitering Blocks");

//		registerBlock(Blocks.grass.getUnlocalizedName(), (new SkillDifficulty(Blocks.grass.getUnlocalizedName()).setDifficulty(0D).setAlwaysDrop(true).setMaxSkillGain(10).setSkill("farming")));
//		registerBlock(Blocks.dirt.getUnlocalizedName(), (new SkillDifficulty(Blocks.dirt.getUnlocalizedName()).setDifficulty(0D).setAlwaysDrop(true).setMaxSkillGain(10).setSkill("farming")));

		//lumberjack
		registerBlock(Blocks.log, (new SkillDifficulty(Blocks.log.getUnlocalizedName()).setSkill("lumberjacking")));
		registerBlock(Blocks.log2, (new SkillDifficulty(Blocks.log2.getUnlocalizedName()).setSkill("lumberjacking")));
		
//		registerBlock(Blocks.sand.getUnlocalizedName(), (new SkillDifficulty(Blocks.sand.getUnlocalizedName()).setDifficulty(0D).setAlwaysDrop(true).setMaxSkillGain(12).setSkill("mining")));

		//Ore (mining)
		registerBlock(Blocks.coal_block, (new SkillDifficulty(Blocks.coal_block.getUnlocalizedName()).setDifficulty(1D).setSkill("mining")));
		registerBlock(Blocks.iron_ore, (new SkillDifficulty(Blocks.iron_ore.getUnlocalizedName()).setDifficulty(10D).setSkill("mining")));
		registerBlock(Blocks.gold_ore, (new SkillDifficulty(Blocks.gold_ore.getUnlocalizedName()).setDifficulty(30D).setSkill("mining")));
		registerBlock(Blocks.redstone_ore, (new SkillDifficulty(Blocks.redstone_ore.getUnlocalizedName()).setDifficulty(50D).setSkill("mining")));
		registerBlock(Blocks.diamond_ore, (new SkillDifficulty(Blocks.diamond_ore.getUnlocalizedName()).setDifficulty(60D).setSkill("mining")));		
		registerBlock(Blocks.emerald_ore, (new SkillDifficulty(Blocks.emerald_ore.getUnlocalizedName()).setDifficulty(50D).setSkill("mining")));
		registerBlock(Blocks.lapis_ore, (new SkillDifficulty(Blocks.lapis_ore.getUnlocalizedName()).setDifficulty(20D).setSkill("mining")));
		registerBlock(Blocks.quartz_ore, (new SkillDifficulty(Blocks.quartz_ore.getUnlocalizedName()).setDifficulty(35D).setSkill("mining")));

		//mining
		registerBlock(Blocks.gravel, (new SkillDifficulty(Blocks.gravel.getUnlocalizedName()).setDifficulty(0D).setAlwaysDrop(true).setSkill("mining")));
		registerBlock(Blocks.stone, (new SkillDifficulty(Blocks.stone.getUnlocalizedName()).setDifficulty(0D).setSkill("mining")));
		registerBlock(Blocks.clay, (new SkillDifficulty(Blocks.clay.getUnlocalizedName()).setDifficulty(15D).setSkill("mining")));
		registerBlock(Blocks.netherrack, (new SkillDifficulty(Blocks.netherrack.getUnlocalizedName()).setDifficulty(25D).setSkill("mining")));
		registerBlock(Blocks.mossy_cobblestone, (new SkillDifficulty(Blocks.mossy_cobblestone.getUnlocalizedName()).setDifficulty(0D).setSkill("mining")));
		registerBlock(Blocks.cobblestone, (new SkillDifficulty(Blocks.cobblestone.getUnlocalizedName()).setDifficulty(0D).setSkill("mining")));
		registerBlock(Blocks.obsidian, (new SkillDifficulty(Blocks.obsidian.getUnlocalizedName()).setDifficulty(70D).setSkill("mining")));
		registerBlock(Blocks.sandstone, (new SkillDifficulty(Blocks.sandstone.getUnlocalizedName()).setDifficulty(18D).setSkill("mining")));
		registerBlock(Blocks.red_sandstone, (new SkillDifficulty(Blocks.red_sandstone.getUnlocalizedName()).setDifficulty(18D).setSkill("mining")));
		
		
		
		System.out.println(SKSettings.blockRegistry.size() +" Block Difficulty/Skills set");
	}
	
	public static boolean hasBlockDifficulty(IBlockState state)
	{
		String id = state.getBlock().getUnlocalizedName();
		Object meta = state.getBlock().getMetaFromState(state);
		
		if(SKSettings.blockRegistry.containsKey(id+":"+meta)) return true;
		else if(SKSettings.blockRegistry.containsKey(id)) return true;

		return false;
	}
	
	public static boolean hasBlockDifficulty(IBlockState state, String type)
	{
		String id = state.getBlock().getUnlocalizedName();
		Object meta = state.getBlock().getMetaFromState(state);

		if(SKSettings.blockRegistry.containsKey(id+":"+meta) && SKSettings.blockRegistry.get(id+":"+meta).skillType == type) 
		{
			return true;
		}
		else if(SKSettings.blockRegistry.containsKey(id) && SKSettings.blockRegistry.get(id).skillType == type) 
		{
			return true;
		}
		return false;
	}
	
	public static SkillDifficulty getBlockDifficulty(IBlockState state)
	{
		String id = state.getBlock().getUnlocalizedName();
		Object meta = state.getBlock().getMetaFromState(state);
		
		if(SKSettings.blockRegistry.containsKey(id+":"+meta))
		{
			return SKSettings.blockRegistry.get(id+":"+meta);
		}
		else if(SKSettings.blockRegistry.containsKey(id)) 
		{
			return SKSettings.blockRegistry.get(id);
		}
		return null;
	}	
	

	
}


