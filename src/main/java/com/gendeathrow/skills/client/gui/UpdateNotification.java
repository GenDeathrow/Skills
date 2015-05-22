package com.gendeathrow.skills.client.gui;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Level;

import com.gendeathrow.skills.core.SKSettings;
import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.utils.Utils;

@SideOnly(Side.CLIENT)
public class UpdateNotification 
{
	
	boolean hasChecked = false;
	public static String version;
	public static String lastSeen;
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if(!Skillz.proxy.isClient() || hasChecked)
		{
			return;
		}
		
		hasChecked = true;
		
		loadConfigLog();
		
		displayUpdateCheck(event);
		
	}
	
	private void loadConfigLog()
	{
//		try {
//			WordPressPost.changeLog = getUrl("https://drone.io/github.com/Funwayguy/EnviroMine-1.7/files/build/libs/full_changelog.txt", true);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			EnviroMine.logger.log(Level.WARN, "Failed to get ChangeLog file!");
//		}

	}
	
	@SuppressWarnings("unused")
	private void displayUpdateCheck(PlayerLoggedInEvent event)
	{
		
		// File link: http://bit.ly/1r4JJt3;
		// DO NOT CHANGE THIS!
		if(Skillz.VERSION == "GD_" + "SK" + "_VER")
		{
			event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "THIS COPY OF RPG SKILLZ IS NOT FOR PUBLIC USE!"));
			return;
		}
		
		try
		{
			String page = getUrl("http://bit.ly/1R8HQWy", true);
						
			String[] data = page.split("\\n");
			
			String[] rawVer = data[0].trim().split("\\.");
			version = rawVer[0] + "." + rawVer[1] + "." + rawVer[2];
			
			if(!SKSettings.updateCheck)
			{
				return;
			}
						
			String http = data[0].trim();
			
			int verStat = Utils.compareVersions(Skillz.VERSION, version);
			
			if(verStat == -1)
			{
				event.player.addChatMessage(new ChatComponentTranslation("updatemsg.skillz.available", version).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
				event.player.addChatMessage(new ChatComponentTranslation("updatemsg.skillz.download"));
				event.player.addChatMessage(new ChatComponentText("https://github.com/GenDeathrow/Skillz/wiki/Downloads").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.BLUE).setUnderlined(true)));
				for(int i = 3; i < data.length; i++)
				{
					if(i > 6)
					{
						event.player.addChatMessage(new ChatComponentText("" + (data.length - 7) + " more..."));
						break;
					} else
					{
						event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RESET + "" + data[i].trim()));
					}
				}
			} else if(verStat == 0)
			{
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + StatCollector.translateToLocalFormatted("updatemsg.skillz.uptodate", Skillz.VERSION)));
			} else if(verStat == 1)
			{
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("updatemsg.skillz.debug", Skillz.VERSION)));
			} else if(verStat == -2)
			{
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("updatemsg.skillz.error")));
			}
			
		} catch(IOException e)
		{
			if(SKSettings.updateCheck)
			{
				Skillz.log.log(Level.WARN, "Failed to get/read versions file!");
			}
		}
	}
	
	/**
	 * Grabs http webpage and returns data
	 * @param link
	 * @param doRedirect
	 * @return
	 * @throws IOException
	 */
	public static String getUrl(String link, boolean doRedirect) throws IOException
	{
		URL url = new URL(link);
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setDoOutput(false);
		con.setReadTimeout(20000);
		con.setRequestProperty("Connection", "keep-alive");
		
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
		((HttpURLConnection)con).setRequestMethod("GET");
		con.setConnectTimeout(5000);
		BufferedInputStream in = new BufferedInputStream(con.getInputStream());
		int responseCode = con.getResponseCode();
		if(responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_MOVED_PERM)
		{
			Skillz.log.log(Level.WARN, "Update request returned response code: " + responseCode + " " + con.getResponseMessage());
		} else if(responseCode == HttpURLConnection.HTTP_MOVED_PERM)
		{
			if(doRedirect)
			{
				try
				{
					return getUrl(con.getHeaderField("location"), false);
				} catch(IOException e)
				{
					throw e;
				}
			} else
			{
				throw new IOException();
			}
		}
		StringBuffer buffer = new StringBuffer();
		int chars_read;
		//	int total = 0;
		while((chars_read = in.read()) != -1)
		{
			char g = (char)chars_read;
			buffer.append(g);
		}
		final String page = buffer.toString();
		
		return page;
	}
}
