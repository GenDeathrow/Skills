package com.gendeathrow.skills.client.gui;

import java.awt.Color;
import java.util.Iterator;

import com.gendeathrow.skills.common.SkillTrackerData;
import com.gendeathrow.skills.common.Skill_TrackerManager;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;
import com.gendeathrow.skills.skill_tree.resource_gathering.MiningSkillTree;
import com.gendeathrow.skills.utils.RenderAssist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiManager 
{

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiRender(RenderGameOverlayEvent.Post event)
	{
		if(event.type != ElementType.HELMET || event.isCancelable())
		{
			return;
		}
		
		SkillTrackerData tracker = Skill_TrackerManager.lookupTracker(Minecraft.getMinecraft().thePlayer);
		
		if(tracker == null) return;
	
		int boxheight = ((SkillTree_Manager.instance.SkillList.size()+1)* 10)+15;
		RenderAssist.drawRect(5, 5, 120, boxheight, RenderAssist.getColorFromRGBA(173, 173, 173, 145));
		
		Iterator<String> it = SkillTree_Manager.instance.SkillList.keySet().iterator();
		int startY = 10;
		while (it.hasNext())
		{
			String skillTree = it.next();
			
			SkillTreeBase skill = tracker.GetSkillByID(skillTree);
		
			ScaledResolution res = event.resolution;
		
			Minecraft.getMinecraft().fontRendererObj.drawString( skill.getLocName() +": "+ skill.getSkillLevel(), 10, startY, Color.black.getRGB());
			startY += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 4;
		}
		
		DebugHud.drawSkillDiff(event);
		
	}
}
