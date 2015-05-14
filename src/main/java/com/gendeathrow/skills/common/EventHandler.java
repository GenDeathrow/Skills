package com.gendeathrow.skills.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.gendeathrow.skills.core.Skillz;
import com.gendeathrow.skills.entity.SK_FishHook;

public class EventHandler 
{
	@SubscribeEvent
	public void onPlayerEnterWorld(EntityJoinWorldEvent event) throws InstantiationException, ReflectiveOperationException, Exception, Throwable
	{
		if(event.entity instanceof SK_FishHook)
		{	
			
			if(event.world.isRemote)
			{
				EntityPlayer angler = ((SK_FishHook) event.entity).angler;
				if(angler != null)
				{
					System.out.println(angler.getName() +"<<< created Client");
				}
			}
			else
			{
				EntityPlayer angler = ((SK_FishHook) event.entity).angler;
				if(angler != null)
				{
					System.out.println(angler.getName() +"<<< created Server");
				}
			}
//			
//	        if (!event.world.isRemote)
//	        {
//				if(angler == null) return;
//				
//				System.out.println(angler.getName()+"->angler");
//				//event.world.removeEntity(fishing);
//	        	event.world.spawnEntityInWorld(new SK_FishHook(event.world, angler));
//	        	
//				event.setCanceled(true);

	       // }
		}
		if(event.entity instanceof EntityPlayerMP)
		{
			System.out.println("Player is an Entity Player");
			// Ensure that only one set of trackers are made per Minecraft instance.
			boolean allowTracker = !(event.world.isRemote && Skillz.proxy.isClient() && Minecraft.getMinecraft().isIntegratedServerRunning());
			
				SkillTrackerData tracker = Skill_TrackerManager.lookupTracker((EntityPlayer)event.entity);
				boolean hasOld = tracker != null;
				
				if(!hasOld)
				{
					SkillTrackerData skillTrack = new SkillTrackerData((EntityPlayerMP)event.entity);
					Skill_TrackerManager.addToManager(skillTrack);
					skillTrack.loadNBTTags();
					if(!Skillz.proxy.isClient() || Skillz.proxy.isOpenToLAN())
					{
						//Skill_TrackerManager.syncMultiplayerTracker(skillTrack);
					}
				} else
				{
					tracker.trackedEntity = (EntityPlayer)event.entity;
				}
		}

	}
	
	@SubscribeEvent
	private void onPlayerSave(EntityEvent.EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayerMP)
		{
			
		}
	}
	
	
	private void onEvent(Entity entity, Object event)
	{
		if(entity instanceof EntityPlayer)
		{
			SkillTrackerData tracker = Skill_TrackerManager.lookupTracker((EntityPlayer)entity);
			
			tracker.sendEvent(event);
		}	
	}
	
	@SubscribeEvent
	public void onAnvilRepair(AnvilRepairEvent event)
	{
		
	}
	
	@SubscribeEvent
	public void onBoneMealUse(BonemealEvent event)
	{
		onEvent(event.entityPlayer, event);
	}
	
	@SubscribeEvent
	public void onAttackEntity(AttackEntityEvent event)
	{
		
	}
	
	@SubscribeEvent
	public void onInteractWitEntity(EntityInteractEvent event)
	{
		
	}
	
	@SubscribeEvent
	public void onHarvest(PlayerEvent.HarvestCheck event)
	{
		onEvent(event.entity, event);
	}
	
	@SubscribeEvent
	public void BreakSpeed(PlayerEvent.BreakSpeed event)
	{
		onEvent(event.entity, event);
	}
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event)
	{
		if(event.entityPlayer != null)
		{
			onEvent(event.entityPlayer, event);
		}
	}
	
	@SubscribeEvent
	public void onItemUse(PlayerUseItemEvent.Start event)
	{
		if(event.entityPlayer != null)
		{
			onEvent(event.entityPlayer, event);
		}
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event)
	{
		
	}
	
	@SubscribeEvent
	public void onFall(LivingFallEvent event)
	{
		
	}
	
	@SubscribeEvent
	public void onPotionBrewPre(PotionBrewEvent.Pre event)
	{
		
	}
	
	@SubscribeEvent
	public void onPotionBrewPost(PotionBrewEvent.Post event)
	{
		
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		onEvent(event.getPlayer(), event);
	}
	
	@SubscribeEvent
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event)
	{
		onEvent(event.harvester, event);
	}
	
	@SubscribeEvent
	public void onBlockPlaced(BlockEvent.PlaceEvent event) 
	{
		
	}
	
	@SubscribeEvent
	public void onHoeEvent(UseHoeEvent event)
	{
		onEvent(event.entityPlayer, event);
	}
}
