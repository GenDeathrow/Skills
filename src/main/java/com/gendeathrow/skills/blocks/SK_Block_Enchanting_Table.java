package com.gendeathrow.skills.blocks;

import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gendeathrow.skills.core.Skillz;

public class SK_Block_Enchanting_Table extends BlockEnchantmentTable
{
	public static SK_Block_Enchanting_Table enchantTable;

	public SK_Block_Enchanting_Table()
	{
		super();
		this.setUnlocalizedName("sk_enchanting_table");
		this.setCreativeTab(CreativeTabs.tabDecorations);

	}

	public static void init()
	{
		enchantTable = new SK_Block_Enchanting_Table();
	}
	
	public static void Register()
	{
		GameRegistry.registerBlock(enchantTable, "sk_enchanting_table");
		if(Skillz.proxy.isClient())
	     {
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
			Item test = Item.getItemFromBlock(enchantTable);
			System.out.println(test.getUnlocalizedName());
			ModelResourceLocation test2 = new ModelResourceLocation("minecraft:enchanting_table", "inventory");
			System.out.println(test2.getResourcePath());
			renderItem.getItemModelMesher().register(test, 0, test2);
			
		    //renderItem.getItemModelMesher().register(Item.getItemFromBlock(enchantTable), 0, new ModelResourceLocation(Skillz.MODID + ":sk_enchanting_table", "inventory"));

	     }
	    
	     //blocks
	  
	}
	

}
