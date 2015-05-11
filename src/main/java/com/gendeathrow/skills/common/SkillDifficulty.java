package com.gendeathrow.skills.common;

import java.util.HashMap;

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

	public int meta;

	public SkillDifficulty(String id)
	{
		new SkillDifficulty(id, (int) Short.MAX_VALUE);
	}	

	public SkillDifficulty(String id, int meta)
	{
		
		this.id = id;
		this.meta = meta;
		this.difficulty = 5;
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
	
	public static void registerBlock(String id, SkillDifficulty skdiff)
	{
		SKSettings.blockRegistry.put(id, skdiff);
		
		System.out.println("Added "+ id);
	}
	
	public static void registerBlocks()
	{
		System.out.println("Regsitering Blocks");

		registerBlock(Blocks.grass.getUnlocalizedName(), (new SkillDifficulty(Blocks.grass.getUnlocalizedName()).setDifficulty(0D).setAlwaysDrop(true).setMaxSkillGain(10).setSkill("farming")));
		registerBlock(Blocks.dirt.getUnlocalizedName(), (new SkillDifficulty(Blocks.dirt.getUnlocalizedName()).setDifficulty(0D).setAlwaysDrop(true).setMaxSkillGain(10).setSkill("farming")));

		registerBlock(Blocks.log.getUnlocalizedName(), (new SkillDifficulty(Blocks.log.getUnlocalizedName()).setDifficulty(0D).setSkill("lumberjacking")));
		registerBlock(Blocks.log2.getUnlocalizedName(), (new SkillDifficulty(Blocks.log2.getUnlocalizedName()).setDifficulty(0D).setSkill("lumberjacking")));
		
		registerBlock(Blocks.sand.getUnlocalizedName(), (new SkillDifficulty(Blocks.sand.getUnlocalizedName()).setDifficulty(2D).setAlwaysDrop(true).setMaxSkillGain(12).setSkill("mining")));
		registerBlock(Blocks.gravel.getUnlocalizedName(), (new SkillDifficulty(Blocks.gravel.getUnlocalizedName()).setDifficulty(3D).setAlwaysDrop(true).setSkill("mining")));
		registerBlock(Blocks.stone.getUnlocalizedName(), (new SkillDifficulty(Blocks.stone.getUnlocalizedName()).setDifficulty(2D).setSkill("mining")));
		registerBlock(Blocks.coal_block.getUnlocalizedName(), (new SkillDifficulty(Blocks.coal_block.getUnlocalizedName()).setDifficulty(5D).setSkill("mining")));
		registerBlock(Blocks.iron_ore.getUnlocalizedName(), (new SkillDifficulty(Blocks.iron_ore.getUnlocalizedName()).setDifficulty(10D).setSkill("mining")));
		registerBlock(Blocks.gold_ore.getUnlocalizedName(), (new SkillDifficulty(Blocks.gold_ore.getUnlocalizedName()).setDifficulty(20D).setSkill("mining")));
		registerBlock(Blocks.redstone_ore.getUnlocalizedName(), (new SkillDifficulty(Blocks.redstone_ore.getUnlocalizedName()).setDifficulty(30D).setSkill("mining")));
		registerBlock(Blocks.diamond_ore.getUnlocalizedName(), (new SkillDifficulty(Blocks.diamond_ore.getUnlocalizedName()).setDifficulty(40D).setSkill("mining")));		
		registerBlock(Blocks.clay.getUnlocalizedName(), (new SkillDifficulty(Blocks.clay.getUnlocalizedName()).setDifficulty(15D).setSkill("mining")));
		registerBlock(Blocks.netherrack.getUnlocalizedName(), (new SkillDifficulty(Blocks.netherrack.getUnlocalizedName()).setDifficulty(15D).setSkill("mining")));
		registerBlock(Blocks.mossy_cobblestone.getUnlocalizedName(), (new SkillDifficulty(Blocks.mossy_cobblestone.getUnlocalizedName()).setDifficulty(1D).setSkill("mining")));
		registerBlock(Blocks.cobblestone.getUnlocalizedName(), (new SkillDifficulty(Blocks.cobblestone.getUnlocalizedName()).setDifficulty(1D).setSkill("mining")));
		registerBlock(Blocks.lapis_ore.getUnlocalizedName(), (new SkillDifficulty(Blocks.lapis_ore.getUnlocalizedName()).setDifficulty(20D).setSkill("mining")));
		registerBlock(Blocks.emerald_ore.getUnlocalizedName(), (new SkillDifficulty(Blocks.emerald_ore.getUnlocalizedName()).setDifficulty(25D).setSkill("mining")));
		registerBlock(Blocks.obsidian.getUnlocalizedName(), (new SkillDifficulty(Blocks.obsidian.getUnlocalizedName()).setDifficulty(50D).setSkill("mining")));
		registerBlock(Blocks.quartz_ore.getUnlocalizedName(), (new SkillDifficulty(Blocks.quartz_ore.getUnlocalizedName()).setDifficulty(28D).setSkill("mining")));
		registerBlock(Blocks.sandstone.getUnlocalizedName(), (new SkillDifficulty(Blocks.sandstone.getUnlocalizedName()).setDifficulty(18D).setSkill("mining")));
		registerBlock(Blocks.red_sandstone.getUnlocalizedName(), (new SkillDifficulty(Blocks.red_sandstone.getUnlocalizedName()).setDifficulty(18D).setSkill("mining")));
		
		System.out.println(SKSettings.blockRegistry.size() +" Block Difficulty/Skills set");
	}
	
	public static SkillDifficulty getBlockDifficulty(String id)
	{
		if(SKSettings.blockRegistry.containsKey(id)) {}// System.out.println(id+ " Exist");
		else 
		{
			
			//System.out.println(id+ " does not Exist");
			
		   //try and get Default
			//getDefaultSkillDifficulty();
		}
		
		return SKSettings.blockRegistry.containsKey(id) ? SKSettings.blockRegistry.get(id) : null;
	}
	

	//TODO based off type of material the block is. 
	public static boolean hasBlockDifficulty(String id)
	{
		return SKSettings.blockRegistry.containsKey(id); 
	}
	
	//TODO based off type of material the block is. 
	public static boolean hasBlockDifficulty(String id, String type)
	{
		return SKSettings.blockRegistry.containsKey(id) && SKSettings.blockRegistry.get(id).skillType == type; 
	}

	
}


