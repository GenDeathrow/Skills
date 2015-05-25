package com.gendeathrow.skills.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.apache.logging.log4j.Level;

import com.gendeathrow.skills.common.SkillTrackerData;
import com.gendeathrow.skills.common.Skill_TrackerManager;
import com.gendeathrow.skills.core.Skillz;

public class PacketSkillz implements IMessage
{
	private NBTTagCompound tags;
	
	public PacketSkillz(){}
	
	public PacketSkillz(NBTTagCompound _tags)
	{
		this.tags = _tags;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tags = ByteBufUtils.readTag(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, this.tags);
	}
	
	public static class HandlerServer implements IMessageHandler<PacketSkillz,IMessage>
	{

		@Override
		public IMessage onMessage(PacketSkillz packet, MessageContext ctx) 
		{
		int id = packet.tags.hasKey("id")? packet.tags.getInteger("id") : -1;
			
			if(id == 1)
			{
				// do somthing
			} else
			{
				Skillz.logger.log(Level.ERROR, "Received invalid packet on serverside!");
			}
			return null; //Reply		}
		}
	}
	
	public static class HandlerClient implements IMessageHandler<PacketSkillz,IMessage>
	{
		@Override
		public IMessage onMessage(PacketSkillz packet, MessageContext ctx)
		{
			int id = packet.tags.hasKey("id")? packet.tags.getInteger("id") : -1;
			System.out.println("Packet Recieved:"+ id);
			
			if(id == 0)
			{
				System.out.println("HandleClient id 0");
				//this.trackerSync(packet.tags);
			} else
			{
				Skillz.logger.log(Level.ERROR, "Received invalid packet on clientside!");
			}
			return null; //Reply
		}
		

		private void trackerSync(NBTTagCompound tags)
		{
			
			SkillTrackerData tracker = Skill_TrackerManager.lookupTrackerFromUsername(tags.getString("player"));
			
			if(tracker != null)
			{
				
				Skill_TrackerManager.saveTracker(tracker);
			}
		}
	}
}
