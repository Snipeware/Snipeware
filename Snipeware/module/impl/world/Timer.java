package Snipeware.module.impl.world;

import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.value.impl.NumberValue;

public class Timer extends Module {
	
	private NumberValue<Float> timerValue = new NumberValue<>("Timer Speed", 1.0f, 0.1f, 100.0f);
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1.0f;
	}
	
	public Timer() {
		super("Timer",0, ModuleCategory.WORLD);
		addValues(timerValue);
	}
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		mc.timer.timerSpeed = timerValue.getValue();
	}
}
