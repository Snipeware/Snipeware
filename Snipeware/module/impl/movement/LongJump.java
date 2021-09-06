package Snipeware.module.impl.movement;

import java.util.LinkedList;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.gui.notification.Notifications;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.player.MovementUtils;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.EnumValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class LongJump extends Module {

    public int stage, groundTicks;
    public double lastDistance;
    private boolean wasAir = false;
    public double movementSpeed;
	private EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.Vanilla);
	private BooleanValue AutoDisable = new BooleanValue("AutoDisable", true);
	private BooleanValue flagbackcheck = new BooleanValue("Flagback check", false);
	private final TimeHelper WatchdogTimer = new TimeHelper();
    public LongJump() {
        super("LongJump", 0, ModuleCategory.MOVEMENT);
        addValues(mode , flagbackcheck, AutoDisable);
    }

    
    private enum Mode {
		Vanilla, Redesky, Watchdog;
	}
    public double cameraY;
    
    @Override
    public void onEnable() {
    	double cameraY = mc.thePlayer.chasingPosY;
    	 WatchdogTimer.reset();
        lastDistance = movementSpeed = 0.0D;
        stage = groundTicks = 0;
        wasAir = false;
        switch (mode.getValue()) {
    	case Watchdog:{
    		if(mc.thePlayer.onGround) {
    		mc.thePlayer.motionY =+ 0.42f;
    		}
    	}
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        boolean autodisable = false;
        wasAir = false;
        WatchdogTimer.reset();
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
    	case Watchdog:{
    		setSuffix(mode.getValueAsString());
/*
    		if(mc.thePlayer.isMoving2() && mc.thePlayer.onGround && wasAir == false) {
    			MovementUtils.setMotion(MovementUtils.getSpeed() + 0.82);

    		}
    		if(!mc.thePlayer.onGround){
    		
    			if(WatchdogTimer.isDelayComplete(100)) {
    			
    				wasAir = true;
    			}
    			
    			
    			MovementUtils.setMotion(MovementUtils.getSpeed() + 0.15);
    			if(WatchdogTimer.isDelayComplete(300)  && mc.thePlayer.motionY < 0.1f) {

    				MovementUtils.setMotion(MovementUtils.getSpeed() + 0.13);
    				if(!WatchdogTimer.isDelayComplete(500)) {
    					mc.thePlayer.motionY += 0.082f;
    				}else {
    					MovementUtils.setMotion(MovementUtils.getSpeed() + 0.02);
    				}
    			}
 
    		}
    		
    		if(mc.thePlayer.onGround && wasAir && AutoDisable.isEnabled()) {
    			wasAir = false;
    			toggle();
    		}
*/

    		break;
    		}
    	}
    }
}
