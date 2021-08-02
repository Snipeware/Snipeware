package felix.module.impl.movement;

import java.util.LinkedList;

import felix.Client;
import felix.api.annotations.Handler;
import felix.events.player.EventMove;
import felix.gui.notification.Notifications;
import felix.events.packet.EventPacketReceive;
import felix.events.player.EventMotionUpdate;
import felix.module.Module;
import felix.util.other.Logger;
import felix.util.other.TimeHelper;
import felix.util.player.MovementUtils;
import felix.value.impl.BooleanValue;
import felix.value.impl.EnumValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class LongJump extends Module {

    public int stage, groundTicks;
    public double lastDistance;
    public double movementSpeed;

    
	private EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.Vanilla);
	private BooleanValue flagbackcheck = new BooleanValue("Flagback check", true);

    
    public LongJump() {
        super("LongJump", 0, ModuleCategory.MOVEMENT);
        addValues(mode , flagbackcheck);
    }

    
    private enum Mode {
		Vanilla, Redesky;
	}
    public double cameraY;
    
    @Override
    public void onEnable() {
    	double cameraY = mc.thePlayer.chasingPosY;
    	
        lastDistance = movementSpeed = 0.0D;
        stage = groundTicks = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        boolean autodisable = false;
      
        mc.timer.timerSpeed = 1.0f;
        mc.gameSettings.keyBindJump.pressed = false;
    }

    @Handler
    public void onMotionUpdate(final EventMotionUpdate event) {
    	switch (mode.getValue()) {
		case Vanilla:{
        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        lastDistance = Math.sqrt(xDist * xDist + zDist * zDist);

        if (stage < 2) {
            event.setPosY(mc.thePlayer.posY);
        }
        if (stage > 3) {
        	groundTicks = 0;
        	mc.thePlayer.motionY = 0.0;
        	mc.timer.timerSpeed = 1.0f;
        }
        break;
    }
    	}
    	}
    
    @Handler
	public void onReceivePacket(final EventPacketReceive event) {
		if (event.getPacket() instanceof S08PacketPlayerPosLook && flagbackcheck.isEnabled()) {
			toggle();
			Notifications.getManager().post("Disabled Modules", "LongJump was disabled to prevent flags/errors.");
		}
	}

    @Handler
    public void onMove(final EventMove event) {
    	switch (mode.getValue()) {
    	case Vanilla:{
    		setSuffix(mode.getValueAsString());
        if (stage == 1) {
            mc.thePlayer.cameraYaw = 0.03f;
            movementSpeed = 0;
            mc.timer.timerSpeed = 0.402371238f;
            
        } else if (stage == 2) {
            event.setY(mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(mc.thePlayer.getJumpUpwardsMotion()));
            
            movementSpeed = 1.9 * MovementUtils.getSpeed();
        } else if (stage == 3) {
            movementSpeed = 3.188 * MovementUtils.getSpeed();
        } else if (stage == 4) {
            movementSpeed *= 1.22;
        } else {
            if (stage < 15) {
                if (mc.thePlayer.motionY < 0) {
                    event.setY(mc.thePlayer.motionY *= .7225f);
                }
                movementSpeed = lastDistance - lastDistance / 159;
            } else {
            	movementSpeed = lastDistance - lastDistance / 159;
            }
        }
        MovementUtils.setSpeed(event, Math.max(movementSpeed, MovementUtils.getSpeed()));
        stage++;
    	break;
    	}
    	case Redesky:{

    		
    		setSuffix(mode.getValueAsString());
    	
    		
    		mc.thePlayer.setSprinting(true);
    		if(mc.thePlayer.isMoving2() && mc.thePlayer.onGround) {
    			
    			
    		

    			MovementUtils.setMotion(MovementUtils.getSpeed() + 0.20);
    			
    			mc.gameSettings.keyBindJump.pressed = true;
    			
    			
    	
    		}else {
    			
    			mc.gameSettings.keyBindJump.pressed = false;
    		}
    		
    		
    		
    		
    		
    		break;
    	}
    	
    		
    }
    }
}
