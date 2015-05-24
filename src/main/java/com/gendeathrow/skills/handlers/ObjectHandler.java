package com.gendeathrow.skills.handlers;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.entity.projectile.SK_FishHook;
import com.gendeathrow.skills.items.SK_FishingRod;

public class ObjectHandler
{
	public static Item fishingRod;
	public static SK_FishHook hookEntity;
	
	public static void initItems()
	{
		fishingRod = new SK_FishingRod().setUnlocalizedName("skfishingrod");
//		badWaterBottle = new EnviroItemBadWaterBottle().setMaxStackSize(1).setUnlocalizedName("enviromine.badwater").setCreativeTab(EnviroMine.enviroTab);
//		saltWaterBottle = new EnviroItemSaltWaterBottle().setMaxStackSize(1).setUnlocalizedName("enviromine.saltwater").setCreativeTab(EnviroMine.enviroTab);
//		coldWaterBottle = new EnviroItemColdWaterBottle().setMaxStackSize(1).setUnlocalizedName("enviromine.coldwater").setCreativeTab(EnviroMine.enviroTab);
//		airFilter = new Item().setMaxStackSize(16).setUnlocalizedName("enviromine.airfilter").setCreativeTab(EnviroMine.enviroTab).setTextureName("enviromine:air_filter");
	}
	
	public static void registerItems()
	{
		GameRegistry.registerItem(fishingRod, "skFishingRod");
//		GameRegistry.registerItem(badWaterBottle, "badWaterBottle");
//		// Empty Pack
//		ItemStack camelStack1 = new ItemStack(camelPack);
//		NBTTagCompound tag = new NBTTagCompound();
//		tag.setInteger("camelPackFill", 0);
//		tag.setInteger("camelPackMax", 100);
//		tag.setBoolean("isCamelPack", true);
//		tag.setString("camelPath", Item.itemRegistry.getNameForObject(camelPack));
//		camelStack1.setTagCompound(tag);
//		EnviroMine.enviroTab.addRawStack(camelStack1);
	}
	
	public static void initBlocks()
	{
//		gasMat = new MaterialGas(MapColor.airColor);
//		gasBlock = new BlockGas(gasMat).setBlockName("enviromine.gas").setCreativeTab(EnviroMine.enviroTab).setBlockTextureName("enviromine:gas_block");
	}
	
	public static void registerBlocks()
	{
//		GameRegistry.registerBlock(gasBlock, "gas");
//		
//		// Ore Dictionary Stuffs
//		OreDictionary.registerOre("oreCoal", flammableCoal);
	}

	public static void registerEntities()
	{
//		EntityRegistry.registerGlobalEntityID(SK_FishHook.class, "skfishinghook", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(SK_FishHook.class, "skfishinghook", 10, Skillz.instance, 64, 1, true);

		//		int physID = EntityRegistry.findGlobalUniqueEntityId();
//		EntityRegistry.registerGlobalEntityID(EntityPhysicsBlock.class, "EnviroPhysicsBlock", physID);
//		EntityRegistry.registerModEntity(EntityPhysicsBlock.class, "EnviroPhysicsEntity", physID, EnviroMine.instance, 64, 1, true);
//		GameRegistry.registerTileEntity(TileEntityGas.class, "enviromine.tile.gas");
	}
	
	public static void registerRecipes()
	{
//		GameRegistry.addSmelting(badWaterBottle, new ItemStack(Items.potionitem, 1, 0), 0.0F);
//		GameRegistry.addShapelessRecipe(new ItemStack(coldWaterBottle, 1, 0), new ItemStack(Items.potionitem, 1, 0), new ItemStack(Items.snowball, 1));
//		
//		GameRegistry.addRecipe(new ItemStack(Items.slime_ball, 4, 0), " r ", "rwr", " r ", 'w', new ItemStack(spoiledMilk, 1, 0), 'r', new ItemStack(rottenFood, 1));
//
//		ItemStack camelStack = new ItemStack(camelPack);
//		NBTTagCompound tag = new NBTTagCompound();
//		tag.setInteger("camelPackFill", 0);
//		tag.setInteger("camelPackMax", 100);
//		tag.setBoolean("isCamelPack", true);
//		tag.setString("camelPath", Item.itemRegistry.getNameForObject(camelPack));
//		camelStack.setTagCompound(tag);
//		GameRegistry.addRecipe(camelStack, "xxx", "xyx", "xxx", 'x', new ItemStack(Items.leather), 'y', new ItemStack(Items.glass_bottle));
//		
//		ItemStack camelStack2 = camelStack.copy();
//		camelStack2.getTagCompound().setInteger("camelPackFill", 25);
//		GameRegistry.addRecipe(camelStack2, "xxx", "xyx", "xxx", 'x', new ItemStack(Items.leather), 'y', new ItemStack(Items.potionitem, 1, 0));
	}
	
}
