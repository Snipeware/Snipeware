package Snipeware.module.impl.player;

import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module {
	
	public NoRotate() {
		super("NoRotate", 0, ModuleCategory.PLAYER);
		setHidden(true);
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		if (event.getPacket() instanceof S08PacketPlayerPosLook) {
			final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
			packet.yaw = mc.thePlayer.rotationYaw;
			packet.pitch = mc.thePlayer.rotationPitch;
		}
	}
}
