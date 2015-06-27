package com.gendeathrow.skills.skill_tree.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

import com.gendeathrow.skills.common.crafting.RecipeWrapper;
import com.gendeathrow.skills.common.skill.SkillDifficulty;
import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.skill_tree.helper.ISkill;
import com.gendeathrow.skills.utils.RecipeHelper;
import com.gendeathrow.skills.utils.EnumHelper.EnumStats;

public class Carpentry extends CraftingBase implements ISkill{

	public static String id = "carpentry";
	
	public Carpentry(SkillTrackerData tracker) {
		super(tracker);
	}

	@Override
	public String LocalizedName()  {
		return "skill.carpentry.name";
	}

	@Override
	public String ULN() {
		return Carpentry.id;
	}

	@Override
	public String Description() {
		return "";
	}

	@Override
	public EnumStats PrimaryStat() {
		return EnumStats.Dexterity;
	}

	@Override
	public EnumStats SecondaryStat() {
		return EnumStats.Intelligence;
	}
	
	@Override
	public void onEvent(Object event) 
	{
		
		if(event instanceof ItemCraftedEvent)
		{
			ItemCraftedEvent newEvent = (ItemCraftedEvent) event;
			RecipeWrapper recipe = RecipeHelper.getWrappedRecipefromItemStack((newEvent.crafting));
			
			if(recipe!= null && recipe.getSkill() == this.id)
			{
				this.calculateGain(this.tracker.trackedEntity, new SkillDifficulty("woodworking").setDifficulty(recipe.getDifficulty()));
			}
		}else if(event instanceof ItemSmeltedEvent)
		{
			
		}	
	}

	public static void RegisterRecipe()
	{
		RecipeHelper.RegisterWrappedRecipe(Items.boat, 20, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.bowl, 5, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.bed, 15, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.item_frame, 30, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.paper, 40, Carpentry.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.ladder, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.crafting_table, 0, Carpentry.id);
	
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_axe, 0, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_hoe, 0, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_pickaxe, 0, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_shovel, 0, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_sword, 0, Carpentry.id);
		
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.acacia_fence, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.acacia_fence_gate, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.acacia_stairs, 15, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.acacia_door, 5, Carpentry.id);
	
		RecipeHelper.RegisterWrappedRecipe(Blocks.birch_fence, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.birch_fence_gate, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.birch_stairs, 15, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.birch_door, 5, Carpentry.id);
	
		RecipeHelper.RegisterWrappedRecipe(Blocks.oak_fence, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.oak_fence_gate, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.oak_stairs, 15, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.oak_door, 5, Carpentry.id);
	
		RecipeHelper.RegisterWrappedRecipe(Blocks.dark_oak_fence, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.dark_oak_fence_gate, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.dark_oak_stairs, 15, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.dark_oak_door, 5, Carpentry.id);
	
		RecipeHelper.RegisterWrappedRecipe(Blocks.jungle_fence, 25, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.jungle_fence_gate, 25, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.jungle_stairs, 30, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.jungle_door, 20, Carpentry.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.spruce_fence, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.spruce_fence_gate, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.spruce_stairs, 15, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.spruce_door, 5, Carpentry.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.chest, 12, Carpentry.id);		
		RecipeHelper.RegisterWrappedRecipe(Blocks.trapped_chest, 35, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.wooden_button, 20, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.wooden_pressure_plate, 25, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.planks, 0, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.wooden_slab, 0, Carpentry.id);
		
		//Add in Masonary blocks
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.clay, 10, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.hardened_clay, 10, Carpentry.id);
		
		RecipeHelper.RegisterWrappedRecipe(Items.flower_pot, 10, Carpentry.id);

		RecipeHelper.RegisterWrappedRecipe(Blocks.stone_slab, 40, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.stone_slab2, 40, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.cobblestone_wall, 40, Carpentry.id);

		RecipeHelper.RegisterWrappedRecipe(Blocks.sandstone, 45, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.sandstone_stairs, 45, Carpentry.id);

		RecipeHelper.RegisterWrappedRecipe(Blocks.brick_block, 75, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.brick_stairs, 75, Carpentry.id);
				
		RecipeHelper.RegisterWrappedRecipe(Blocks.stone_brick_stairs, 65, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.stonebrick, 65, Carpentry.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.nether_brick_fence, 95, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.nether_brick_stairs, 95, Carpentry.id);
		RecipeHelper.RegisterWrappedRecipe(Items.netherbrick, 95, Carpentry.id);
		
	}
}
