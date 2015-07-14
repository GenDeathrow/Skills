package com.gendeathrow.skills.inventory;

import java.util.List;
import java.util.Random;

import com.gendeathrow.skills.common.stat.StatTrackerData;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SK_ContainerEnchantment extends ContainerEnchantment
{

	public SK_ContainerEnchantment(InventoryPlayer playerInv, World worldIn, BlockPos pos) 
	{
		super(playerInv, worldIn, pos);
	}
	
    @SideOnly(Side.CLIENT)
    public SK_ContainerEnchantment(InventoryPlayer playerInv, World worldIn)
    {
        this(playerInv, worldIn, BlockPos.ORIGIN);
    }
    
    @Override
    public boolean enchantItem(EntityPlayer playerIn, int id)
    {
    	StatTrackerData tracker = StatTrackerData.get(playerIn);
    	
    	System.out.println(id);
    	
    	return false;
    }
  
}
