package com.gendeathrow.skills.utils;

public class EnumHelper 
{

	
	public enum EnumStats
	{
		Strength("Strength"),
		Dexterity("Dexterity"), 
		Intelligence("Itelligence"),
		Wisdom("Wisdom"),
		Constitution("Constitiution");
		
		private String id;
		EnumStats(String name)
		{
			this.id = name;
		}
		
		public String getID()
		{
			return this.id;
		}
		
		
	}
}
