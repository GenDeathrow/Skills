package com.gendeathrow.skills.client.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.SkillTrackerData;
import com.gendeathrow.skills.common.Skill_TrackerManager;
import com.google.common.eventbus.Subscribe;

@SideOnly(Side.CLIENT)
public class RenderHandler 
{

	@Subscribe
	public static void RenderPlayer(RenderPlayerEvent event)
	{
		

	}
	
}
