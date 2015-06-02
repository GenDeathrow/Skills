package com.gendeathrow.skills.core.proxies;
 
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.gendeathrow.skills.client.gui.GuiManager;
import com.gendeathrow.skills.client.gui.UpdateNotification;
import com.gendeathrow.skills.client.keybinds.SkillzKeybinds;
import com.gendeathrow.skills.client.render.renderFishing;
import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.entity.projectile.SK_FishHook;
import com.gendeathrow.skills.items.SK_FishingRod;

public class ClientProxy extends CommonProxy {

	private final Minecraft mc = Minecraft.getMinecraft();

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
	
		UpdateNotification updateManager = new UpdateNotification();
		MinecraftForge.EVENT_BUS.register(updateManager);
		FMLCommonHandler.instance().bus().register(updateManager);
		
		SkillzKeybinds keybindManager = new SkillzKeybinds();
		keybindManager.register();
		
		MinecraftForge.EVENT_BUS.register(keybindManager);
		FMLCommonHandler.instance().bus().register(keybindManager);


		
		super.registerEventHandlers();
	}
	
	@Override
	public void registerRenders()
	{

	}
	
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work as expected because you will be getting a
		// client player even when you are on the server!
		// Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		Skillz.logger.info("Retrieving player from ClientProxy for message on side " + ctx.side);
		return (ctx.side.isClient() ? mc.thePlayer : super.getPlayerEntity(ctx));
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return (ctx.side.isClient() ? mc : super.getThreadFromContext(ctx));
	}
	
}
