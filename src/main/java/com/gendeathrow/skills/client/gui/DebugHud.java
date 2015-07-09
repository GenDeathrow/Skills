package com.gendeathrow.skills.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.skill.SkillDifficulty;
import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData;
import com.gendeathrow.skills.common.stat.StatTrackerData.PlayerStat;
import com.gendeathrow.skills.core.SKSettings;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.helper.SkillTree_Manager;
import com.gendeathrow.skills.utils.MathHelper;
import com.gendeathrow.skills.utils.RenderAssist;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

@SideOnly(Side.CLIENT)
public class DebugHud 
{
	public static IBlockState lookingAt;

	public static int currentCatDisplay; 
	
	public static ArrayList CatList = new ArrayList();
	
	public static DebugHud instance = new DebugHud();

	public DebugHud()
	{
		currentCatDisplay = 0;
		CatList.add("resource_gathering");
		CatList.add("combat_skill");
		CatList.add("crafting_skill");
		CatList.add("magical_skill");
	}
	
	public static void DrawDebugHud(Post event)
	{
		if(SKSettings.showDebug == false) return;
		DebugHud.instance.DrawStat(event);
		DebugHud.instance.DrawSkill(event);
		DebugHud.instance.drawSkillDiff(event);
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
	
	public static void DrawSkill(Post event)
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
	
	public static void drawSkillDiff(Post event)
	{
		Vec3 look = Minecraft.getMinecraft().thePlayer.getLookVec();
		
		 lookingAt = null;
		
		BlockPos blockLoc = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
		try{
			lookingAt = Minecraft.getMinecraft().theWorld.getBlockState(blockLoc);	
							
		}catch(NullPointerException e)
		{
		}
		
		if(lookingAt != null)
		{
			lookingAt = Minecraft.getMinecraft().theWorld.getBlockState(blockLoc);
			if(lookingAt.getBlock() == Blocks.air) return;
			
			ScaledResolution res = event.resolution;			
			SkillTrackerData tracker = SkillTrackerData.get(Minecraft.getMinecraft().thePlayer);
			
			if(SkillDifficulty.hasBlockDifficulty(lookingAt) && tracker != null)
			{
				SkillDifficulty skdiff = SkillDifficulty.getBlockDifficulty(lookingAt);
				if(skdiff == null) return;
				
				SkillTreeBase skill = tracker.GetSkillByID(skdiff.skillType);
				if(skill == null) return;
				
				double chance = skill.getChance(skdiff);
				double gainS = skill.DebugFormula(Minecraft.getMinecraft().thePlayer, chance, skdiff,1);
				double gainF = skill.DebugFormula(Minecraft.getMinecraft().thePlayer, chance, skdiff,0);
				Minecraft.getMinecraft().fontRendererObj.drawString( lookingAt.getBlock().getLocalizedName(), res.getScaledWidth() - 130 , res.getScaledHeight()/2 + 20, Color.WHITE.getRGB());
				Minecraft.getMinecraft().fontRendererObj.drawString( "Skill Type: "+ skdiff.skillType, res.getScaledWidth() - 130 , res.getScaledHeight()/2 + 35, Color.WHITE.getRGB());
				Minecraft.getMinecraft().fontRendererObj.drawString( "Success Chance: "+ MathHelper.round((float)(chance*100),0) +"%" , res.getScaledWidth() - 130 , res.getScaledHeight()/2 + 50, Color.WHITE.getRGB());
				Minecraft.getMinecraft().fontRendererObj.drawString(  "Gain Chance S/F: ", res.getScaledWidth() - 130 , res.getScaledHeight()/2 + 65, Color.WHITE.getRGB());
				Minecraft.getMinecraft().fontRendererObj.drawString(  MathHelper.round((float) (gainS*100),0) +"% / "+ MathHelper.round((float) (gainF*100),0) +"%" , res.getScaledWidth() - 130 , res.getScaledHeight()/2 + 80, Color.WHITE.getRGB());
			}
			

			
			//System.out.println(lookingAt.getBlock().getLocalizedName()+ ">>><<<<");
		}
	}

	public static void DrawStat(RenderGameOverlayEvent.Post event)
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
}
