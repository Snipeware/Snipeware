package Snipeware.module.impl.player;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.value.impl.EnumValue;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
	
	private EnumValue<Mode> nofallMode = new EnumValue("NoFall Mode", Mode.Watchdog);

	public NoFall() {
		super("NoFall", Keyboard.KEY_NONE, ModuleCategory.PLAYER);
		addValues(nofallMode);
	}
	
	private enum Mode {
		Watchdog, Edit, 
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		setSuffix(nofallMode.getValueAsString());
		if (!Client.INSTANCE.getModuleManager().getModule("Flight").isEnabled() && mc.thePlayer.fallDistance > 3) {
			switch (nofallMode.getValue()) {
			case Watchdog:
                if(mc.thePlayer.fallDistance > 3F){
                	mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer(true));
                	mc.thePlayer.fallDistance = 0;
				}
           
			case Edit:
				event.setOnGround(true);
				break;
			}
		}
	}

    public double getBlockHeight() {
        return mc.thePlayer.posY - Math.round(mc.thePlayer.posY);
    }
}
