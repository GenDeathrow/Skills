package com.gendeathrow.skills.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerEnchantmentWrapper extends ContainerEnchantment{

	public ContainerEnchantmentWrapper(InventoryPlayer playerInv,World worldIn, BlockPos pos) 
	{
		super(playerInv, worldIn, pos);
	}


}
