package com.gendeathrow.skills.core.proxies;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.gendeathrow.skills.client.gui.UpdateNotification;
import com.gendeathrow.skills.common.EventHandler;
import com.gendeathrow.skills.common.SkillDifficulty;

public class CommonProxy {

	public boolean isClient()
	{
		return false;
	}
	
	public boolean isOpenToLAN()
	{
		return false;
	}
	
	public void preInit(FMLPreInitializationEvent event) 
	{

		
	}

	public void init(FMLInitializationEvent event) 
	{
		registerEventHandlers();
		registerTickHandlers();

//		//ObjectHandler.initItems();
//		ObjectHandler.initBlocks();
//		ObjectHandler.registerBlocks();
//		ObjectHandler.registerEntities();
//		ObjectHandler.registerItems();
//		ObjectHandler.registerRecipes();
		
		SkillDifficulty.registerBlocks();
	}

	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void registerTickHandlers() 
	{
		//FMLCommonHandler.instance().bus().register(new ServerTick());
			
	}

	public void registerEventHandlers() 
	{
		EventHandler eventManager = new EventHandler();
		MinecraftForge.EVENT_BUS.register(eventManager);
		FMLCommonHandler.instance().bus().register(eventManager);
		
		UpdateNotification updateManager = new UpdateNotification();
		MinecraftForge.EVENT_BUS.register(updateManager);
		FMLCommonHandler.instance().bus().register(updateManager);
	}
	
	public void registerRenders()
	{
		
	}

}
