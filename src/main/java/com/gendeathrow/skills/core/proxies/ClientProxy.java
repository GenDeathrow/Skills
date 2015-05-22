package com.gendeathrow.skills.core.proxies;
 
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.gendeathrow.skills.client.gui.GuiManager;
import com.gendeathrow.skills.client.render.renderFishing;
import com.gendeathrow.skills.entity.SK_FishHook;
import com.gendeathrow.skills.items.SK_FishingRod;

public class ClientProxy extends CommonProxy {

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public boolean isOpenToLAN() {
		if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
			return Minecraft.getMinecraft().getIntegratedServer().getPublic();
		} else {
			return false;
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		// TODO Auto-generated method stub
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {

		super.init(event);
		
		SK_FishingRod.registerRenders();
		RenderingRegistry.registerEntityRenderingHandler(SK_FishHook.class, new renderFishing(Minecraft.getMinecraft().getRenderManager()));

		//RenderingRegistry.registerEntityRenderingHandler(SK_FishHook.class, new RenderFish(Minecraft.getMinecraft().getRenderManager()));
		
	     //RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
	     
	    // renderItem.getItemModelMesher().register(ObjectHandler.fishingRod, 0, new ModelResourceLocation(Skillz.MODID + ":" +  ObjectHandler.fishingRod.getUnlocalizedName(), "inventory"));
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		super.postInit(event);
		
		

	}

	@Override
	public void registerTickHandlers() 
	{
		super.registerTickHandlers();
		super.registerRenders();

	}

	@Override
	public void registerEventHandlers() {

		MinecraftForge.EVENT_BUS.register(new GuiManager());
	
		super.registerEventHandlers();
	}
	
	@Override
	public void registerRenders()
	{

	}
	
}
