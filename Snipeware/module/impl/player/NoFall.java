package Snipeware.module.impl.player;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.value.impl.EnumValue;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class NoFall extends Module {
	
	private EnumValue<Mode> nofallMode = new EnumValue("NoFall Mode", Mode.Watchdog);

	public NoFall() {
		super("NoFall", Keyboard.KEY_NONE, ModuleCategory.PLAYER);
		addValues(nofallMode);
	}
    private double lastX, lastY, lastZ;
    private double lastFallDist = 0;
    public static boolean smooth = false;
    private boolean canNegate = true;
	
	private enum Mode {
		Watchdog, Edit, 
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}

    private boolean isOverGlass(double x, double y, double z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)) != Blocks.glass && mc.theWorld.getBlockState(new BlockPos(x, y, z)) != Blocks.stained_glass;
    }

    private boolean isInCage() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        for (int i = 0; i < 1; ++i) {
            if (isOverGlass(x - 1, y + i, z) || isOverGlass(x + 1, y + i, z) || isOverGlass(x, y + i, z - 1) || isOverGlass(x, y + i, z + 1)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isBlockUnder() {
        if (mc.thePlayer.posY < 0)
            return false;
        for (int offset = 0; offset < (int) mc.thePlayer.posY + 2; offset += 2) {
            AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);
            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }
	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		switch (nofallMode.getValue()) {
		case Watchdog:
	       if (canNegate) {
	            if (smooth && event.getPacket() instanceof S08PacketPlayerPosLook) {
	                S08PacketPlayerPosLook S08 = (S08PacketPlayerPosLook) event.getPacket();
	                mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(S08.getX(), S08.getY(), S08.getZ(), S08.getYaw(), S08.getPitch(), false));
	                S08.x = lastX;
	                S08.y = lastY + 0.01;
	                S08.z = lastZ;
	                smooth = false;
	                mc.thePlayer.setSprinting(false);
	            }
	        }
	       break;
		}
	}
    
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		setSuffix(nofallMode.getValueAsString());
		if (!Client.INSTANCE.getModuleManager().getModule("Flight").isEnabled() && mc.thePlayer.fallDistance > 3) {
			switch (nofallMode.getValue()) {
			case Watchdog:
				   double x = event.getPosX();
			        double y = event.getPosY();
			        double z = event.getPosZ();
			        float yaw = event.getYaw();
			        float pitch = event.getPitch();
			        if (canNegate) {
			            if (mc.thePlayer.onGround && lastFallDist > 3 && !mc.thePlayer.isPotionActive(Potion.jump)) {
			                mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(event.getPosX(), event.getPosY() - 0.075, event.getPosZ(), false));
			                mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(event.getPosX(), event.getPosY() - 0.08, event.getPosZ(), yaw, pitch, false));
			                mc.thePlayer.setSprinting(true);
			                smooth = true;
			                lastX = x;
			                lastY = y;
			                lastZ = z;
			            }
			            lastFallDist = mc.thePlayer.fallDistance;
			        }
			        if (isInCage()) {
			            canNegate = false;
			        } else if (!isInCage() && !canNegate && mc.thePlayer.onGround) {
			            canNegate = true;
			        }
           break;
			case Edit:
				event.setOnGround(true);
				break;
			}
		}
	}
	
	@Handler
	public void onPacketSend(EventPacketSend event) {
		switch (nofallMode.getValue()) {
		case Watchdog:
		   if (canNegate) {
	            if (mc.thePlayer.fallDistance > 2.5 && isBlockUnder() && !mc.thePlayer.isPotionActive(Potion.jump)) {
	                if (event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition.C06PacketPlayerPosLook) {
	                    C03PacketPlayer.C04PacketPlayerPosition.C06PacketPlayerPosLook C06 = (C03PacketPlayer.C04PacketPlayerPosition.C06PacketPlayerPosLook) event.getPacket();
	                    mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(C06.getPositionX(), C06.getPositionY(), C06.getPositionZ(), false));
	                    event.setCancelled(true);
	                }
	                if (mc.thePlayer.isMoving() && event.getPacket() instanceof C0BPacketEntityAction) {
	                    event.setCancelled(true);
	                }
	            }
	        }
		}
	}

    public double getBlockHeight() {
        return mc.thePlayer.posY - Math.round(mc.thePlayer.posY);
    }
    

}
