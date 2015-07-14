package com.gendeathrow.skills.core.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.gendeathrow.skills.client.gui.GuiHandler;
import com.gendeathrow.skills.common.EventHandler;
import com.gendeathrow.skills.common.skill.SkillDifficulty;
import com.gendeathrow.skills.core.Skillz;

public class CommonProxy {

	public GuiHandler guiHandler = new GuiHandler();
	
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

		NetworkRegistry.INSTANCE.registerGuiHandler(Skillz.instance, guiHandler);

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
		

	}
	
	public void registerRenders()
	{
		
	}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		Skillz.logger.info("Retrieving player from CommonProxy for message on side " + ctx.side);
		return ctx.getServerHandler().playerEntity;
	}

	/**
	 * Returns the current thread based on side during message handling,
	 * used for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getServerForPlayer();
	}

}
