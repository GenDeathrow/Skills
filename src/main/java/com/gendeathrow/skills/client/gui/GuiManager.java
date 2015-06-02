package com.gendeathrow.skills.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;
import com.gendeathrow.skills.utils.RenderAssist;

@SideOnly(Side.CLIENT)
public class GuiManager 
{
	public static int currentCatDisplay; 
	
	public static ArrayList CatList = new ArrayList();

	public GuiManager()
	{
		currentCatDisplay = 0;
		CatList.add("resource_gathering");
		CatList.add("combat_skill");
	}
	
	public static void changeCat()
	{
		
		currentCatDisplay += 1;
		
		System.out.println(CatList.size() + "  current:" + currentCatDisplay);
		if(currentCatDisplay > CatList.size()-1)
		{
			currentCatDisplay = 0;
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiRender(RenderGameOverlayEvent.Post event)
	{
		if(event.type != ElementType.HELMET || event.isCancelable())
		{
			return;
		}
		
		//SkillTrackerData tracker = Skill_TrackerManager.lookupTracker(Minecraft.getMinecraft().thePlayer);
		SkillTrackerData tracker = SkillTrackerData.get(Minecraft.getMinecraft().thePlayer);
		
		if(tracker == null) return;

		ArrayList DrawArray = new ArrayList();
		
		Iterator<String> it = SkillTree_Manager.instance.SkillList.keySet().iterator();
		while (it.hasNext())
		{
			String skillTree = it.next();

			SkillTreeBase skill = tracker.GetSkillByID(skillTree);
		
			if(skill.getCatULN() == CatList.get(currentCatDisplay))
			{
				DrawArray.add(skill);
			}
		}		
		
		int boxheight = ((DrawArray.size()+1)* 10)+15 + 15 + 15;
		RenderAssist.drawRect(5, 5, 120, boxheight, RenderAssist.getColorFromRGBA(173, 173, 173, 145));
		
		ScaledResolution res = event.resolution;

		Iterator it2 = DrawArray.iterator();
		int startY = 10;
		
		Minecraft.getMinecraft().fontRendererObj.drawString(((SkillTreeBase) DrawArray.get(0)).getCatLocalizedName(), 10, startY, Color.green.getRGB());
		
		startY += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 4;
		while(it2.hasNext())
		{
			SkillTreeBase skill = (SkillTreeBase) it2.next();
			
		
			Minecraft.getMinecraft().fontRendererObj.drawString( skill.getLocName() +": "+ skill.getSkillLevel(), 10, startY, Color.black.getRGB());
			startY += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 4;
		}
		
		Minecraft.getMinecraft().fontRendererObj.drawString( "Switch Cat 'N'", 10, startY, Color.yellow.getRGB());

		
//		Iterator<String> it = SkillTree_Manager.instance.SkillList.keySet().iterator();
//		int startY = 10;
//		while (it.hasNext())
//		{
//			String skillTree = it.next();
//			
//			SkillTreeBase skill = tracker.GetSkillByID(skillTree);
//		
//			ScaledResolution res = event.resolution;
//		
//			Minecraft.getMinecraft().fontRendererObj.drawString( skill.getLocName() +": "+ skill.getSkillLevel(), 10, startY, Color.black.getRGB());
//			startY += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 4;
//		}
		
		DebugHud.drawSkillDiff(event);
		
	}
}
