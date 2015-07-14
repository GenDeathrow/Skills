package com.gendeathrow.skills.client.gui;

import net.minecraft.client.gui.GuiEnchantment;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.gendeathrow.skills.client.gui.inventory.SK_GuiEnchantment;

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
		
		GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
	        //GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1F, 1F, 1F, 1F);
				DebugHud.instance.DrawDebugHud(event);
			GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	      //  GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	
	
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
			if (event.gui instanceof GuiEnchantment && !(event.gui instanceof SK_GuiEnchantment)) 
			{
				event.gui = new SK_GuiEnchantment((GuiEnchantment) event.gui);
			}

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
