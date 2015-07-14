package com.gendeathrow.skills.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.network.PacketDispatcher;
import com.gendeathrow.skills.network.AbstractMessage.AbstractServerMessage;
import com.gendeathrow.skills.skill_tree.magical.Enchanting;

public class sendEnchantSkill  extends AbstractServerMessage<sendEnchantSkill>
{
	public boolean activateSkill;

	public sendEnchantSkill()
	{
		this.activateSkill = true;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		
		this.activateSkill = buffer.readBoolean();
		
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		// TODO Auto-generated method stub
		buffer.writeBoolean(this.activateSkill);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		if(player == null) System.out.println("player null");
		if(this.activateSkill == true && player != null && side == Side.SERVER)
		{
			System.out.println("increase skill");
			((Enchanting)SkillTrackerData.get(player).GetSkillByID(Enchanting.id)).useSkill(player);
			
			PacketDispatcher.sendTo(new SyncPlayersSkillPropsMessage((EntityPlayer) player), (EntityPlayerMP) player);
		}
		
	}

}
