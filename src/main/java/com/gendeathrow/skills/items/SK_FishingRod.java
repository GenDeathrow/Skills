package com.gendeathrow.skills.items;

import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.entity.projectile.SK_FishHook;

public class SK_FishingRod extends Item
{
    private static final String __OBFID = "CL_00000034";
  
    public static Item fishingrod;
    public static ModelResourceLocation castModel;
    public static ModelResourceLocation uncastModel;
    
    public SK_FishingRod()
    {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    public static void init()
    {
    	 fishingrod = new SK_FishingRod().setUnlocalizedName("sk_fishing_rod");
    	
    	 if(!Skillz.proxy.isClient()) return;
    	 castModel = new ModelResourceLocation(Skillz.MODID+ ":sk_fishing_rod_cast", "inventory");
    	 System.out.println("Regsiter CastModel"+ castModel.getResourcePath() +"-"+ castModel.getResourceDomain() +"-"+ castModel.getVariant());
    	 uncastModel = new ModelResourceLocation(Skillz.MODID+ ":sk_fishing_rod", "inventory");
    }
    
    public static void register()
    {
    	GameRegistry.registerItem(fishingrod, fishingrod.getUnlocalizedName().substring(5));
    	
    	removeVanillaRecipe();
    	
        GameRegistry.addRecipe(new ItemStack(fishingrod, 1), new Object[] {"  #", " #X", "# X", '#', Items.stick, 'X', Items.string});

    	Items.fishing_rod.setCreativeTab(null);
    }

    
    private static void removeVanillaRecipe()
    {
    	Iterator craftingList = CraftingManager.getInstance().getRecipeList().iterator();
    	int index = 0;
    	while(craftingList.hasNext())
    	{
    		Object recipe = craftingList.next();
    		
    		if(recipe instanceof ShapedRecipes)
    		{
    			ShapedRecipes shapedRecipe = (ShapedRecipes)recipe;
    			
    			if(shapedRecipe.getRecipeOutput().getItem() == Items.fishing_rod)
    			{
    				
    				CraftingManager.getInstance().getRecipeList().remove(index);
    				break;
    			}
    			
    			
    		}
    		index++;
    	}
    	

    }
    public static void registerRenders()
    {
    	registerRender(fishingrod);
    }
    
        
    public static void registerRender(Item item)
    {
    	ModelBakery.addVariantName(fishingrod, new String[] {Skillz.MODID + ":sk_fishing_rod", Skillz.MODID + ":sk_fishing_rod_cast"});
    	//Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(fishingrod, 0, castModel);
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(fishingrod, 0, uncastModel);
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
        SkillTrackerData tracker =  SkillTrackerData.get(playerIn);
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

	@Override
	public ModelResourceLocation getModel(ItemStack itemstack, EntityPlayer playerIn, int useRemaining)
	{

		if(playerIn instanceof EntityPlayer)
		{
			SkillTrackerData tracker =  SkillTrackerData.get(playerIn);
			
			if(tracker != null)
			{
				//System.out.println("tracker not null");
				if(tracker.fishingEntity != null)
				{
					//System.out.println("Cast Model");
					return new ModelResourceLocation(Skillz.MODID+ ":sk_fishing_rod_cast", "inventory");
				}
			}
		}
		return null;
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