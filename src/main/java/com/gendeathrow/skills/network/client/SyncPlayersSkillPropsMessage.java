package com.gendeathrow.skills.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.network.AbstractMessage.AbstractClientMessage;

public class SyncPlayersSkillPropsMessage  extends AbstractClientMessage<SyncPlayersSkillPropsMessage>
{
	// Previously, we've been writing each field in our properties one at a time,
	// but that is really annoying, and we've already done it in the save and load
	// NBT methods anyway, so here's a slick way to efficiently send all of your
	// extended data, and no matter how much you add or remove, you'll never have
	// to change the packet / synchronization of your data.

	// this will store our ExtendedPlayer data, allowing us to easily read and write
	private NBTTagCompound data;

	// The basic, no-argument constructor MUST be included to use the new automated handling
	public SyncPlayersSkillPropsMessage() {}

	// We need to initialize our data, so provide a suitable constructor:
	public SyncPlayersSkillPropsMessage(EntityPlayer player) 
	{
		// create a new tag compound
		data = new NBTTagCompound();
	
		// and save our player's data into it
		SkillTrackerData.get(player).saveNBTData(data);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		data = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeNBTTagCompoundToBuffer(data);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		// now we can just load the NBTTagCompound data directly; one and done, folks
		Skillz.logger.info("Synchronizing extended Skill properties data on CLIENT");
		SkillTrackerData.get(player).loadNBTData(data);
	}
}
