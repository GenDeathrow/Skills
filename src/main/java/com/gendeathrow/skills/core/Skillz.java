package com.gendeathrow.skills.core;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.gendeathrow.skills.client.render.renderFishing;
import com.gendeathrow.skills.core.proxies.CommonProxy;
import com.gendeathrow.skills.entity.SK_FishHook;
import com.gendeathrow.skills.items.SK_FishingRod;

import org.apache.logging.log4j.Logger;

@Mod(modid = Skillz.MODID, version = Skillz.VERSION, name = Skillz.Name)
public class Skillz
{
	    public static final String MODID = "skillz";
	    public static final String VERSION = "0.0.7";
	    public static final String Name = "Skillz";
	    public static final String Proxy = "com.gendeathrow.skills.core.proxies";
	    public static final String Channel = "SZ_GenD";
	    
	    public static  Logger log;
	    
	    @Instance(Skillz.MODID)
	    public static Skillz instance;
	    
		@SidedProxy(clientSide = Skillz.Proxy + ".ClientProxy", serverSide = Skillz.Proxy + ".CommonProxy")
		public static CommonProxy proxy;

		public SimpleNetworkWrapper network;
		
		
		@EventHandler
		public void preInit(FMLPreInitializationEvent event)
		{
			log = event.getModLog();
			
			proxy.preInit(event);
			
			SK_FishingRod.init();
			SK_FishingRod.register();
		}
	    @EventHandler
	    public void init(FMLInitializationEvent event)
	    {
			EntityRegistry.registerModEntity(SK_FishHook.class, "skfishinghook", 10, Skillz.instance, 64, 1, true);

	    	proxy.init(event);
	    }
	    @EventHandler
		public void postInit(FMLPostInitializationEvent event)
		{
			proxy.postInit(event);
		}
		
		@EventHandler
		public void serverStart(FMLServerStartingEvent event)
		{
			//MinecraftServer server = MinecraftServer.getServer();
			//ICommandManager command = server.getCommandManager();
			//ServerCommandManager manager = (ServerCommandManager) command;
			
//			manager.registerCommand(new CommandPhysics());

		}

}
