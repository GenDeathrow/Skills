package com.gendeathrow.skills.common.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnchantingInterruptor extends ContainerEnchantment{

    @SideOnly(Side.CLIENT)
    public EnchantingInterruptor(InventoryPlayer playerInv, World worldIn)
    {
        this(playerInv, worldIn, BlockPos.ORIGIN);
    }
    
	public EnchantingInterruptor(InventoryPlayer playerInv, World worldIn, BlockPos pos) 
	{
		super(playerInv, worldIn, pos);
	}

}
