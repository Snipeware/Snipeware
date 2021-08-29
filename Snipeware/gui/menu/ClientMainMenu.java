package Snipeware.gui.menu;

import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import Snipeware.Client;
import Snipeware.gui.alt.gui.GuiAltManager;
import Snipeware.module.Module;
import Snipeware.util.visual.RenderUtil;
import Snipeware.util.visual.Translate;
import font.FontRenderer;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ClientMainMenu extends GuiMainMenu {

    private ResourceLocation finalTexture = null;
    private ResourceLocation texture1;
    private ResourceLocation texture2;
    private ResourceLocation texture3;
    private ResourceLocation texture4;
    private static String Name = "SNIPEW";
    private static String Name2 = "ARE";
    private ArrayList<String> changelogs = new ArrayList<>();
    private double animated;

    @Override
    public void initGui() {
    	Client.getInstance().getDiscordRP().update("idling", "Main Menu");
        super.initGui();
        buttonList.clear();
        final String strSSP = I18n.format("SINGLEPLAYER");
        final String strSMP = I18n.format("MULTIPLAYER");
        final String strAccounts = "ALT LOGIN";
        
        final String strLang = I18n.format("LANGUAGE");
        final String strOptions = "OPTIONS";
        final String strShutDown = "SHUTDOWN";
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int initHeight = sr.getScaledWidth() / 5 + 20;
        int objHeight = 22;
        int objWidth = 162;
        int xMid = width / 2 - objWidth / 2;
        
        
        buttonList.add(new Button(0, xMid , initHeight + 30, objWidth, objHeight, strSSP));
        buttonList.add(new Button(1, xMid , initHeight + 18 - 2 + 30 + 9 ,objWidth, objHeight, strSMP));
        buttonList.add(new Button(4, xMid , initHeight + 35 - 3 + 30 + 18, objWidth , objHeight, strAccounts));
        buttonList.add(new Button(2, xMid , initHeight + 52 - 4 + 30 + 27, objWidth, objHeight, strOptions));
        buttonList.add(new Button(5, xMid , initHeight + 69 - 5 + 30 + 36, objWidth, objHeight, strShutDown));
        
    //   buttonList.add(new TextButton(3, xMid + width / 3, initHeight - 153, objWidth, objHeight, strLang));
      //  buttonList.add(new TextButton(2, xMid + width / 3 + 110, initHeight - 153, objWidth, objHeight, strOptions));
       
        //buttonList.add(new ExitButton(5, xMid + width / 3 + 192, initHeight - 157, 25, 25, strOptions));
        
   
  
    }
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 3:
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                break;
            case 4:
                mc.displayGuiScreen(new GuiAltManager());
                break;
            case 5:
                mc.shutdown();
                DiscordRPC.discordShutdown();
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	int objWidth = 210;
        int objHeight = 183;
    	int xMid = width / 2 - objWidth / 2;
        
    
    	  
    	  
    	mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "background.png"));
    	Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        this.drawGradientRect(0, height - 150, width, height, 0, -16777216);
        
        
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.pushMatrix();
        
        RenderUtil.drawRect(xMid, sr.getScaledWidth() / 5 + 1,objWidth, objHeight + 1, new Color(138,43,226,200).getRGB());
        
        
        RenderUtil.drawRect(xMid, sr.getScaledWidth() / 5,objWidth, objHeight, new Color(42, 42, 42, 253).getRGB());
        
     
        
        for (int i = 0; i < buttonList.size(); ++i) { 
            GuiButton g = (GuiButton) buttonList.get(i);
            g.drawButton(mc, mouseX, mouseY);
        }
    
  
    
        
        
    	
    	int xMidStr = width / 2 - 187 / 2;
        
        final FontRenderer fr2 = Client.INSTANCE.getFontManager().getFont("Aquire 62", true);
        fr2.drawStringWithShadow(Name,  xMidStr - 5,sr.getScaledWidth() / 5 + 9, new Color(158,63,236).getRGB());
        fr2.drawStringWithShadow(Name2,  xMidStr - 5 + 118,sr.getScaledWidth() / 5 + 9, new Color(158,63,236).getRGB());
        final FontRenderer fr = Client.INSTANCE.getFontManager().getFont("Display 21", true);
        final String welcome = "Welcome back";
        final String Credits = "Made by Koljan, Tear, Napoleon";
        
        fr.drawStringWithShadow(Credits, (float)(this.width - fr.getWidth(Credits)) / 50, (float)(this.height - 24), new Color(250,250,250,80).getRGB());
        
        fr.drawStringWithShadow(welcome, (float)(this.width - fr.getWidth(welcome) - 13), (float)(this.height - 24),  new Color(250,250,250, 80).getRGB());
      

        changelogs.add("Added changelogs.");
        changelogs.add("Being cool.");
        
       
        
        GlStateManager.popMatrix();
        
    }
	private int count(int i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
