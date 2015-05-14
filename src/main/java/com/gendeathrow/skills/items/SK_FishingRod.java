package com.gendeathrow.skills.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.SkillTrackerData;
import com.gendeathrow.skills.common.Skill_TrackerManager;
import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.entity.SK_FishHook;

public class SK_FishingRod extends Item
{
    private static final String __OBFID = "CL_00000034";
  
    public static Item fishingrod;
    
    public SK_FishingRod()
    {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    public static void init()
    {
    	fishingrod = new SK_FishingRod().setUnlocalizedName("skfishingrod");
    }
    
    public static void register()
    {
    	System.out.println(fishingrod.getUnlocalizedName().substring(5) +" -WHAT");
    	GameRegistry.registerItem(fishingrod, fishingrod.getUnlocalizedName().substring(5));
    }
    
    public static void registerRenders()
    {
    	registerRender(fishingrod);
    }
        
    public static void registerRender(Item item)
    {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(fishingrod, 0, new ModelResourceLocation(Skillz.MODID+ ":"+ fishingrod.getUnlocalizedName().substring(5), "inventory"));
    }
    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
     * hands.
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        SkillTrackerData tracker = Skill_TrackerManager.lookupTracker(playerIn);
        if(tracker == null) return itemStackIn;
        
        if (tracker.fishingEntity != null)
        {
            int i = tracker.fishingEntity.handleHookRetraction();
            itemStackIn.damageItem(i, playerIn);
            playerIn.swingItem();
        }
        else
        {
            worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote)
            {
            	System.out.println(playerIn.getName() + "<<<<<<<<<<<<<<<<<");
                worldIn.spawnEntityInWorld(new SK_FishHook(worldIn, playerIn));
            }

            playerIn.swingItem();
            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        }

        return itemStackIn;
    }

    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean isItemTool(ItemStack stack)
    {
        return super.isItemTool(stack);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 1;
    }
}