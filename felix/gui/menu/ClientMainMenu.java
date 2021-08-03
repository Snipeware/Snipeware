package felix.gui.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import felix.Client;
import felix.gui.alt.gui.GuiAltManager;
import felix.module.Module;
import felix.util.visual.RenderUtil;
import felix.util.visual.Translate;
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
    
    private ArrayList<String> changelogs = new ArrayList<>();

    @Override
    public void initGui() {
  //  	Client.getInstance().getDiscordRP().update("idling", "Main Menu");
        super.initGui();
        buttonList.clear();
        final String strSSP = I18n.format("SINGLEPLAYER");
        final String strSMP = I18n.format("MULTIPLAYER");
        final String strAccounts = "ALT LOGIN";
        
        final String strLang = I18n.format("LANGUAGE");
        final String strOptions = "OPTIONS";
        int initHeight = height / 4 + 48;
        int objHeight = 15;
        int objWidth = 102;
        int xMid = width / 2 - objWidth / 2;
        
        buttonList.add(new Button(0, xMid, initHeight + 30, objWidth, objHeight, strSSP));
        buttonList.add(new Button(1, xMid, initHeight + 18 - 2 + 30, objWidth, objHeight, strSMP));
        buttonList.add(new Button(4, xMid, initHeight + 35 - 3 + 30, objWidth, objHeight, strAccounts));
        buttonList.add(new Button(2, xMid, initHeight + 52 - 4 + 30, objWidth, objHeight, strOptions));
        
    //   buttonList.add(new TextButton(3, xMid + width / 3, initHeight - 153, objWidth, objHeight, strLang));
      //  buttonList.add(new TextButton(2, xMid + width / 3 + 110, initHeight - 153, objWidth, objHeight, strOptions));
        
        buttonList.add(new ExitButton(5, xMid + width / 3 + 172, initHeight - 157, 25, 25, strOptions));
        

       
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
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	
    	mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "background.png"));
    	Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        this.drawGradientRect(0, height - 150, width, height, 0, -16777216);
 
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.pushMatrix();
        
        for (int i = 0; i < buttonList.size(); ++i) { 
            GuiButton g = (GuiButton) buttonList.get(i);
            g.drawButton(mc, mouseX, mouseY);
        }
    RenderUtil.drawImage(new ResourceLocation("minecraft", "logo.png"), sr.getScaledWidth() / 2 - 30, height / 4 - 35, 60, 64);
      
        
        final FontRenderer fr = Client.INSTANCE.getFontManager().getFont("Display 21", true);
        final String welcome = "Welcome back";
        final String build = "Snipeware (this is a beta and there are many bugs)";
       
        
        fr.drawStringWithShadow(welcome, (float)(this.width - fr.getWidth(welcome) - 13), (float)(this.height - 24), -1);
        fr.drawStringWithShadow(build, (float)(this.width - fr.getWidth(build)) / 50, (float)(this.height - 24), -1);
     
       
        
        //RenderUtil.drawImage(new ResourceLocation("minecraft", "language.png"), width / 3 + 397, height / 4 + 48 - 159, 25, 25);
        //RenderUtil.drawImage(new ResourceLocation("minecraft", "options.png"), width / 3 + 506, height / 4 + 48 - 159, 26, 26);
      
        changelogs.add("Added changelogs.");
        changelogs.add("Being cool.");
        
        final FontRenderer changelogFont = Client.INSTANCE.getFontManager().getFont("Fatality 35", true);
        GlStateManager.popMatrix();
        
    }
	private int count(int i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
