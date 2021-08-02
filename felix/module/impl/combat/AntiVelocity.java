package felix.module.impl.combat;

import org.lwjgl.input.Keyboard;

import felix.Client;
import felix.api.annotations.Handler;
import felix.events.packet.EventPacketReceive;
import felix.gui.notification.Notifications;
import felix.module.Module;
import felix.value.impl.BooleanValue;
import felix.value.impl.EnumValue;
import felix.value.impl.NumberValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class AntiVelocity extends Module {
	
	private EnumValue<Mode> velocityMode = new EnumValue<>("Velocity Mode", Mode.Cancel);
	
	private NumberValue<Integer> vertical = new NumberValue<>("Vertical", 100, 0, 100);
	
	private NumberValue<Integer> horizontal = new NumberValue<>("Horizontal", 0, 0, 100);
	
	private BooleanValue flagbackcheck = new BooleanValue("Redesky Check", true);
	
	
    private double motionX;
    private double motionZ;
    private double motionY;
    
	
	public AntiVelocity() {
		super("Velocity", 0, ModuleCategory.COMBAT);
		addValues(velocityMode, vertical ,horizontal, flagbackcheck);
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		EntityPlayerSP player = mc.thePlayer;
		switch (velocityMode.getValue()) {
		case Cancel:
			setSuffix("Cancel");
			if (event.getPacket() instanceof S12PacketEntityVelocity) {
				final S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) event.getPacket();
				if (player.getEntityId() == s12.getEntityID()) {
					event.setCancelled(true);
				}
			}
			else if (event.getPacket() instanceof S27PacketExplosion) {
				event.setCancelled(true);
			}
			break;
		case Customizable:
			setSuffix(vertical.getValueAsString() + " " + horizontal.getValueAsString());
			final S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
            int vertical = this.vertical.getValue();
            int horizontal = this.horizontal.getValue();
            if (vertical != 0 || horizontal != 0) {
                packet.setX(horizontal * packet.getMotionX() / 100);
                packet.setY(vertical * packet.getMotionY() / 100);
                packet.setZ(horizontal * packet.getMotionZ() / 100);
            } else {
                event.setCancelled(true);
            }
            if (event.getPacket() instanceof S27PacketExplosion) {
            	event.setCancelled(true);
            }
			break;
			
		case Redesky:
			
			
			
			setSuffix("Redesky");
			if (mc.thePlayer.hurtTime == 9) {
				motionX = mc.thePlayer.motionX;
				motionZ = mc.thePlayer.motionZ; 
				motionY = mc.thePlayer.motionY;
			}
		
		
		
		 if (mc.thePlayer.isMoving2()) {
				
	        if (mc.thePlayer.hurtTime == 8) {
	        	
	        	if(!Client.getInstance().getModuleManager().getModule("LongJump").isEnabled()) {
	        
	              mc.thePlayer.motionX = motionX * 0.85;
	              mc.thePlayer.motionZ = motionZ * 0.85;
	              mc.thePlayer.motionY = motionY * 0.957;
	              
	        	}
	            
	           }
	        
	        if(mc.thePlayer.hurtTime > 0) {
	        	if (event.getPacket() instanceof S08PacketPlayerPosLook && flagbackcheck.isEnabled()) {
					toggle();
					Notifications.getManager().post("Disabled Modules", "Velocity was disabled to prevent flags/errors");
				}
	        }
			
		 }
		 break;
		
		}
	
	}
	
	
	
	private enum Mode {
		Cancel, Customizable, Redesky;
	}
}
