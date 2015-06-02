package com.gendeathrow.skills.client.gui;

import java.awt.Color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.skill.SkillDifficulty;
import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.common.skill.Skill_TrackerManager;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.utils.MathHelper;

@SideOnly(Side.CLIENT)
public class DebugHud 
{
	public static IBlockState lookingAt;

	public static void drawSkills()
	{
		
		
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
	
}
