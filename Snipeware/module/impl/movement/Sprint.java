package Snipeware.module.impl.movement;

import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.Event;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.module.Module;
import Snipeware.value.impl.BooleanValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends Module {
	
	public BooleanValue omni = new BooleanValue("Omnisprint", true);

	public Sprint() {
		super("Sprint", 0, ModuleCategory.MOVEMENT);
		addValues(omni);
	}
	
	@Handler
	public void onEvent(final EventMove event) {
		mc.thePlayer.setSprinting(shouldSprint());
	}
	
	private boolean shouldSprint() {
		return omni.isEnabled() ? mc.thePlayer.isMoving() : mc.thePlayer.moveForward > 0;
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		mc.thePlayer.setSprinting(false);
		super.onDisable();
	}
}
