package com.gendeathrow.skills.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData.PlayerStat;
import com.gendeathrow.skills.core.SKSettings;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;
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
		CatList.add("crafting_skill");
		CatList.add("magical_skill");
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
		
		if(SKSettings.showDebug == false) return;
		DrawStat(event);
		DrawSkill(event);
		
		DebugHud.drawSkillDiff(event);
		
	}
	
	private void DrawStat(RenderGameOverlayEvent.Post event)
	{
		StatTrackerData tracker = StatTrackerData.get(Minecraft.getMinecraft().thePlayer);
		
		if(tracker == null) return;
		ArrayList DrawArray = new ArrayList();
		
		Iterator<Entry<EnumStats, PlayerStat>> playerStats = tracker.PlayerStats.entrySet().iterator();
		while (playerStats.hasNext())
		{
			 PlayerStat stat = playerStats.next().getValue();
			 DrawArray.add(stat);
		}		
		
		int boxheight = ((DrawArray.size()+1)* 10)+15 + 15 + 15;
		//TODO fix the box
		int boxX = event.resolution.getScaledWidth() - 80;
		RenderAssist.drawRect(boxX, 5, event.resolution.getScaledWidth() - 5, boxheight, RenderAssist.getColorFromRGBA(173, 173, 173, 145));
		
		ScaledResolution res = event.resolution;

		Iterator it2 = DrawArray.iterator();
		int startY = 10;
			
		startY += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 4;
		while(it2.hasNext())
		{
			PlayerStat stat = (PlayerStat) it2.next();
			
		
			Minecraft.getMinecraft().fontRendererObj.drawString( stat.getLocolizedName() +": "+ stat.getValue(), boxX + 5, startY, Color.black.getRGB());
			startY += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 4;
		}
		
	
	}
	
	private void DrawSkill(RenderGameOverlayEvent.Post event)
	{
		SkillTrackerData tracker = SkillTrackerData.get(Minecraft.getMinecraft().thePlayer);
		
		if(tracker == null) return;


		ArrayList DrawArray = new ArrayList();
		
		Iterator<String> it = SkillTree_Manager.instance.SkillList.keySet().iterator();
		while (it.hasNext())
		{
			String skillTree = it.next();

			SkillTreeBase skill = tracker.GetSkillByID(skillTree);
		try{
			if(skill.getCatULN() == CatList.get(currentCatDisplay))
			{
				DrawArray.add(skill);
			}
		}catch(NullPointerException e) {}
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
		
		String top = "Debug Screen 'M' to Toggle";
		Minecraft.getMinecraft().fontRendererObj.drawString(top, res.getScaledWidth()/2 - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(top)/2), 10, Color.yellow.getRGB());
		
		Minecraft.getMinecraft().fontRendererObj.drawString( "Switch Cat 'N'", 10, startY, Color.yellow.getRGB());

	}
}
