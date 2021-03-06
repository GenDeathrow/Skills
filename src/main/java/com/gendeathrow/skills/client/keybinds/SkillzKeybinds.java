package com.gendeathrow.skills.client.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import com.gendeathrow.skills.client.gui.DebugHud;
import com.gendeathrow.skills.core.SKSettings;

@SideOnly(Side.CLIENT)
public class SkillzKeybinds 
{
	public static KeyBinding changeCat;
	public static KeyBinding debugGui;
	
	public static void register()
	{
		changeCat = new KeyBinding(StatCollector.translateToLocal("keybinds.Skillz.changeCat"), Keyboard.KEY_N, "Skillz");
		debugGui = new KeyBinding(StatCollector.translateToLocal("Show Debug Gui"), Keyboard.KEY_M, "Skillz");
		
		ClientRegistry.registerKeyBinding(changeCat);
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(changeCat.isPressed())
		{
			DebugHud.instance.changeCat();
		}else if(debugGui.isPressed())
		{
			SKSettings.showDebug = !SKSettings.showDebug;
		}
		
	}
}
