package com.gendeathrow.skills.core;

import java.util.HashMap;

import com.gendeathrow.skills.common.SkillDifficulty;

public class SKSettings 
{

	public static int totalSkillCap = 400;
	public static float gainFactor = 1;
	public static float failure_factor = 0.2F;
	
	public static boolean updateCheck = true;
	
	public final static HashMap<String, SkillDifficulty> blockRegistry = new HashMap<String, SkillDifficulty>();

}
