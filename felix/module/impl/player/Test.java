package felix.module.impl.player;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import felix.Client;
import felix.api.annotations.Handler;
import felix.events.packet.EventPacketSend;
import felix.events.player.EventMotionUpdate;
import felix.events.player.EventMove;
import felix.events.render.EventRender2D;
import felix.module.Module;
import felix.util.other.Logger;
import felix.util.other.TimeHelper;
import felix.value.impl.EnumValue;
import font.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Test extends Module {

	public int rar = -50;

	public TimeHelper timer = new TimeHelper();

	public Test() {
		super("Test", Keyboard.KEY_NONE, ModuleCategory.PLAYER);
	
	}
	
	@Handler
	public void a(EventRender2D event) {
		  final FontRenderer fr = Client.INSTANCE.getFontManager().getFont("Display 42", true);
		  ScaledResolution sr = new ScaledResolution(mc);

		 String test = "Penis";
	
		
		   fr.drawString(test,  (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(test) / 2, (sr.getScaledHeight() >> 1) - rar, -1);
	}
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		
		if(event.isPre() && mc.thePlayer.fallDistance > 2) {
			Logger.print("pines");
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
;			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
		}
	}
	
	@Handler
	public void onMove(final EventMove event) {
		
	
		if(!mc.thePlayer.isMoving2() && mc.thePlayer.fallDistance > 3) {
		
		
	
		}else {
			mc.timer.timerSpeed = 1f;
		}
	}
	
	
	public void onEnable() {

		super.onEnable();
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		rar = -50;
		super.onDisable();
	}
	

}