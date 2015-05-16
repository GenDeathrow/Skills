package com.gendeathrow.skills.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;

public class ChatHelper 
{
	private String lastSkill;
	private long lastTime;
	
	public static ChatHelper instance = new ChatHelper();
	
	public ChatHelper()
	{
		this.lastTime = Minecraft.getSystemTime();
	}
	
	public void trySend(EntityPlayer player, SkillTreeBase skill, String msg)
	{
		long curTime = Minecraft.getSystemTime();
		
		if(((curTime - 3000) >= lastTime) || this.lastSkill != skill.getULN())
		{
			this.lastTime = curTime;
			this.lastSkill = skill.getULN();
			sendupdate(player, skill, msg);
		}
	}
	
	public void sendupdate(EntityPlayer player, SkillTreeBase skill, String msg)
	{
		player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "("+ skill.getLocName() +") "+ msg));	
	}
}
