package com.gendeathrow.skills.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;

import org.lwjgl.util.glu.Project;

import com.gendeathrow.skills.common.skill.SkillTrackerData;
import com.gendeathrow.skills.network.PacketDispatcher;
import com.gendeathrow.skills.network.client.sendEnchantSkill;
import com.gendeathrow.skills.skill_tree.helper.SkillTreeBase;
import com.gendeathrow.skills.skill_tree.magical.Enchanting;
import com.gendeathrow.skills.utils.Utils;
import com.google.common.collect.Lists;

public class SK_GuiEnchantment extends GuiEnchantment {

	public ContainerEnchantment containerEnchantment;
	//public InventoryPlayer inventorySlots;
	public static SK_GuiEnchantment instance;

    private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
    /** The ResourceLocation containing the texture for the Book rendered above the enchantment table */
    private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
    /** The ModelBook instance used for rendering the book on the Enchantment table */
    private static final ModelBook MODEL_BOOK = new ModelBook();
  
	
	public SK_GuiEnchantment(GuiEnchantment parent) 
	{		
		super((InventoryPlayer) Utils.getPrivateField(parent, GuiEnchantment.class, "playerInventory"),Minecraft.getMinecraft().theWorld,(IWorldNameable) Utils.getPrivateField(parent, GuiEnchantment.class, "field_175380_I"));
		this.containerEnchantment = Utils.getPrivateField(parent, GuiEnchantment.class, "container");
		Utils.setPrivateField(this, GuiEnchantment.class, this.containerEnchantment, "container");
		this.inventorySlots = this.containerEnchantment;
		instance = this;
	}
	
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
	
        SkillTrackerData tracker = SkillTrackerData.get(Minecraft.getMinecraft().thePlayer);
        
        int l = (this.width - this.xSize) / 2;
        int i1 = (this.height - this.ySize) / 2;

        for (int j1 = 0; j1 < 3; ++j1)
        {
            int k1 = mouseX - (l + 60);
            int l1 = mouseY - (i1 + 14 + 19 * j1);

            if (k1 >= 0 && l1 >= 0 && k1 < 108 && l1 < 19 && this.containerEnchantment.enchantItem(this.mc.thePlayer, j1))
            {
            	SkillTreeBase enchantingSkill = tracker.GetSkillByID(Enchanting.id);
            	//if(enchantingSkill == null) this.mc.playerController.sendEnchantPacket(this.containerEnchantment.windowId, j1);
            	if(j1 == 0 && enchantingSkill.getSkillLevel() >= 0)
            	{
            		this.mc.playerController.sendEnchantPacket(this.containerEnchantment.windowId, j1);
            		PacketDispatcher.sendToServer(new sendEnchantSkill());
            		return;
            	}
            	else if(j1 == 1 && enchantingSkill.getSkillLevel() >= 25)
            	{
            		this.mc.playerController.sendEnchantPacket(this.containerEnchantment.windowId, j1);
            		return;
            	}
            	else if(j1 == 2 && enchantingSkill.getSkillLevel() >= 50)
            	{
            		this.mc.playerController.sendEnchantPacket(this.containerEnchantment.windowId, j1);
            		return;
            	}
            	else 
            		{
            		System.out.println("Skill not High enough");
            		return;
            		}
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
	
    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
	    SkillTrackerData tracker = SkillTrackerData.get(Minecraft.getMinecraft().thePlayer);
      	SkillTreeBase enchantingSkill = tracker.GetSkillByID(Enchanting.id);
              
	      
        super.drawScreen(mouseX, mouseY, partialTicks);
        boolean flag = this.mc.thePlayer.capabilities.isCreativeMode;
        int k = this.containerEnchantment.getLapisAmount();

        for (int l = 0; l < 3; ++l)
        {
            int i1 = this.containerEnchantment.enchantLevels[l];
            int j1 = this.containerEnchantment.field_178151_h[l];
            int k1 = l + 1;

            if (this.isPointInRegion(60, 14 + 19 * l, 108, 17, mouseX, mouseY) && i1 > 0 && j1 >= 0)
            {
                ArrayList arraylist = Lists.newArrayList();
                String s;

                if (j1 >= 0 && Enchantment.getEnchantmentById(j1 & 255) != null)
                {
                    s = Enchantment.getEnchantmentById(j1 & 255).getTranslatedName((j1 & 65280) >> 8);
                    arraylist.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC.toString() + I18n.format("container.enchant.clue", new Object[] {s}));
                }

                if (!flag)
                {
                    if (j1 >= 0)
                    {
                        arraylist.add("");
                    }

                	if(l == 1 && enchantingSkill.getSkillLevel() < 25)
                	{
                        arraylist.add(EnumChatFormatting.RED.toString() + "Enchanting Requirement: 25");
                	}
                	else if(l == 2 && enchantingSkill.getSkillLevel() < 50)
                	{
                        arraylist.add(EnumChatFormatting.RED.toString() + "Enchanting Requirement:50 ");                	
                    }   
                	
                	if (this.mc.thePlayer.experienceLevel < i1)
                    {
                        arraylist.add(EnumChatFormatting.RED.toString() + "Level Requirement: " + this.containerEnchantment.enchantLevels[l]);
                    }
                    else
                    {
                        s = "";

                        if (k1 == 1)
                        {
                            s = I18n.format("container.enchant.lapis.one", new Object[0]);
                        }
                        else
                        {
                            s = I18n.format("container.enchant.lapis.many", new Object[] {Integer.valueOf(k1)});
                        }

                        if (k >= k1)
                        {
                            arraylist.add(EnumChatFormatting.GRAY.toString() + "" + s);
                        }
                        else
                        {
                            arraylist.add(EnumChatFormatting.RED.toString() + "" + s);
                        }

                        if (k1 == 1)
                        {
                            s = I18n.format("container.enchant.level.one", new Object[0]);
                        }
                        else
                        {
                            s = I18n.format("container.enchant.level.many", new Object[] {Integer.valueOf(k1)});
                        }

                        arraylist.add(EnumChatFormatting.GRAY.toString() + "" + s);
                    }
                }

                this.drawHoveringText(arraylist, mouseX, mouseY);
                break;
            }
        }
    }
	
	 /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
		
	    SkillTrackerData tracker = SkillTrackerData.get(Minecraft.getMinecraft().thePlayer);
      	SkillTreeBase enchantingSkill = tracker.GetSkillByID(Enchanting.id);
              

      	
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.viewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
        GlStateManager.translate(-0.34F, 0.23F, 0.0F);
        Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        float f1 = 1.0F;
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0F, 3.3F, -16.0F);
        GlStateManager.scale(f1, f1, f1);
        float f2 = 5.0F;
        GlStateManager.scale(f2, f2, f2);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
        GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
        float f3 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * partialTicks;
        GlStateManager.translate((1.0F - f3) * 0.2F, (1.0F - f3) * 0.1F, (1.0F - f3) * 0.25F);
        GlStateManager.rotate(-(1.0F - f3) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        float f4 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * partialTicks + 0.25F;
        float f5 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * partialTicks + 0.75F;
        f4 = (f4 - (float)MathHelper.truncateDoubleToInt((double)f4)) * 1.6F - 0.3F;
        f5 = (f5 - (float)MathHelper.truncateDoubleToInt((double)f5)) * 1.6F - 0.3F;

        if (f4 < 0.0F)
        {
            f4 = 0.0F;
        }

        if (f5 < 0.0F)
        {
            f5 = 0.0F;
        }

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        if (f5 > 1.0F)
        {
            f5 = 1.0F;
        }

        GlStateManager.enableRescaleNormal();
        MODEL_BOOK.render((Entity)null, 0.0F, f4, f5, f3, 0.0F, 0.0625F);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.matrixMode(5889);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        EnchantmentNameParts.getInstance().reseedRandomGenerator((long)this.containerEnchantment.xpSeed);
        int i1 = this.containerEnchantment.getLapisAmount();

        for (int j1 = 0; j1 < 3; ++j1)
        {
            int k1 = k + 60;
            int l1 = k1 + 20;
            byte b0 = 86;
            String s = EnchantmentNameParts.getInstance().generateNewRandomName();
            this.zLevel = 0.0F;
            this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
            int i2 = this.containerEnchantment.enchantLevels[j1];
            int skillLevel = 0;
            
            if(j1 == 1) skillLevel = 25;
            else if(j1 == 2) skillLevel = 50;
            
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            if (i2 == 0)
            {
                this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 185, 108, 19);
            }
            else
            {
                String s1 = "" + i2;
                FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
                int j2 = 6839882;

                
                if ((i1 < j1 + 1 || this.mc.thePlayer.experienceLevel < i2 || enchantingSkill.getSkillLevel() < skillLevel) && !this.mc.thePlayer.capabilities.isCreativeMode)
                {
                    this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 185, 108, 19);
                    this.drawTexturedModalRect(k1 + 1, l + 15 + 19 * j1, 16 * j1, 239, 16, 16);
                    fontrenderer.drawSplitString(s, l1, l + 16 + 19 * j1, b0, (j2 & 16711422) >> 1);
                    j2 = 4226832;
                }
                else
                {
                    int k2 = mouseX - (k + 60);
                    int l2 = mouseY - (l + 14 + 19 * j1);

                    if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19)
                    {
                        this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 204, 108, 19);
                        j2 = 16777088;
                    }
                    else
                    {
                        this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 166, 108, 19);
                    }

                    this.drawTexturedModalRect(k1 + 1, l + 15 + 19 * j1, 16 * j1, 223, 16, 16);
                    fontrenderer.drawSplitString(s, l1, l + 16 + 19 * j1, b0, j2);
                    j2 = 8453920;
                }

                fontrenderer = this.mc.fontRendererObj;
                fontrenderer.drawStringWithShadow(s1, (float)(l1 + 86 - fontrenderer.getStringWidth(s1)), (float)(l + 16 + 19 * j1 + 7), j2);
            }
        }
    }

}
