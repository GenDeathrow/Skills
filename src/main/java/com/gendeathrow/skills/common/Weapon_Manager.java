package com.gendeathrow.skills.common;

import java.util.HashMap;

public class Weapon_Manager 
{
	public static final HashMap<String, Weapon_Modifiers> WeaponModifiers= new HashMap<String, Weapon_Modifiers>();
	
	public static final Weapon_Manager Instance = new Weapon_Manager();
	
	public Weapon_Manager()
	{
		//WeaponModifiers.put(arg0, arg1)
	}
	

	public enum damageType
	{
		Slashing,
		Piercing,
		Blunt,
		Crushing
	}
	
	public class Weapon_Modifiers
	{
		
		public Weapon_Modifiers()
		{
			
		}
	}
}

