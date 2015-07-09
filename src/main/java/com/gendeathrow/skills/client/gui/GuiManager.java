package com.gendeathrow.skills.client.gui;

import net.minecraft.client.gui.GuiEnchantment;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiManager 
{

	public GuiManager()
	{

	}
	

	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiRender(RenderGameOverlayEvent.Post event)
	{
		if(event.type != ElementType.HELMET || event.isCancelable())
		{
			return;
		}

		DebugHud.instance.DrawDebugHud(event);		
	}

	
	
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		if(event.gui != null && event.gui.getClass() == GuiEnchantment.class)
		{
			GuiEnchantment gui = (GuiEnchantment) event.gui;			
		}
//		if(event.gui != null && event.gui.getClass() == GuiInventory.class && !(event.gui instanceof GuiBigInventory))
//		{
//			event.gui = new EnchantingInterruptor(Minecraft.getMinecraft().thePlayer);
//		} else if(event.gui == null && Minecraft.getMinecraft().thePlayer.inventoryContainer instanceof BigContainerPlayer)
//		{
//			// Reset scroll and inventory slot positioning to make sure it doesn't screw up later
//			((EnchantingInterruptor)Minecraft.getMinecraft().thePlayer.inventoryContainer).scrollPos = 0;
//			((EnchantingInterruptor)Minecraft.getMinecraft().thePlayer.inventoryContainer).UpdateScroll();
//		}
	}
	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiPostInit(InitGuiEvent.Post event)
	{
//		Blocks.enchanting_table.s
//		if(event.gui instanceof EnchantingInterruptor)
//		{
//			((EnchantingInterruptor)event.gui).redoButtons = true;
//		} else if(event.gui instanceof GuiContainer)
//		{
//			GuiContainer gui = (GuiContainer)event.gui;
//			Container container = gui.inventorySlots;
//			
//			event.buttonList.add(new InvoScrollBar(256, 0, 0, 1, 1, "", container, gui));
//			
//			if(event.gui instanceof GuiInventory)
//			{
//				final ScaledResolution scaledresolution = new ScaledResolution(event.gui.mc, event.gui.mc.displayWidth, event.gui.mc.displayHeight);
//                int i = scaledresolution.getScaledWidth();
//                int j = scaledresolution.getScaledHeight();
//				event.buttonList.add(new GuiButtonUnlockSlot(event.buttonList.size(), i/2 - 50, j - 40, 100, 20, event.gui.mc.thePlayer));
//			}
//		}
	}
}
