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

public class WoodWorking extends CraftingBase implements ISkill{

	public static String id = "woodworking";
	
	public WoodWorking(SkillTrackerData tracker) {
		super(tracker);
	}

	@Override
	public String LocalizedName()  {
		return "skill.woodworking.name";
	}

	@Override
	public String ULN() {
		return WoodWorking.id;
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
		RecipeHelper.RegisterWrappedRecipe(Items.boat, 20, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.bowl, 5, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.bed, 15, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.item_frame, 30, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.paper, 40, WoodWorking.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.ladder, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.jukebox, 50, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.crafting_table, 0, WoodWorking.id);
	
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_axe, 0, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_hoe, 0, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_pickaxe, 0, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_shovel, 0, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.wooden_sword, 0, WoodWorking.id);
		
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.acacia_fence, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.acacia_fence_gate, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.acacia_stairs, 15, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.acacia_door, 5, WoodWorking.id);
	
		RecipeHelper.RegisterWrappedRecipe(Blocks.birch_fence, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.birch_fence_gate, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.birch_stairs, 15, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.birch_door, 5, WoodWorking.id);
	
		RecipeHelper.RegisterWrappedRecipe(Blocks.oak_fence, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.oak_fence_gate, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.oak_stairs, 15, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.oak_door, 5, WoodWorking.id);
	
		RecipeHelper.RegisterWrappedRecipe(Blocks.dark_oak_fence, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.dark_oak_fence_gate, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.dark_oak_stairs, 15, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.dark_oak_door, 5, WoodWorking.id);
	
		RecipeHelper.RegisterWrappedRecipe(Blocks.jungle_fence, 25, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.jungle_fence_gate, 25, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.jungle_stairs, 30, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.jungle_door, 20, WoodWorking.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.spruce_fence, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.spruce_fence_gate, 10, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.spruce_stairs, 15, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Items.spruce_door, 5, WoodWorking.id);
		
		RecipeHelper.RegisterWrappedRecipe(Blocks.chest, 12, WoodWorking.id);		
		RecipeHelper.RegisterWrappedRecipe(Blocks.trapped_chest, 35, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.wooden_button, 20, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.wooden_pressure_plate, 25, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.planks, 0, WoodWorking.id);
		RecipeHelper.RegisterWrappedRecipe(Blocks.wooden_slab, 0, WoodWorking.id);
	}
}
