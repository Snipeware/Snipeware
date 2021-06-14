package felix.module.impl.world;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomUtils;

import felix.Client;
import felix.api.annotations.Handler;
import felix.events.Event;
import felix.events.packet.EventPacketReceive;
import felix.events.packet.EventPacketSend;
import felix.events.player.EventMove;
import felix.events.player.EventMotionUpdate;
import felix.gui.notification.Notifications;
import felix.module.Module;
import felix.module.impl.movement.Flight;
import felix.util.other.PlayerUtil;
import felix.util.other.TimeHelper;
import felix.value.impl.EnumValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;

public class Disabler extends Module {

	private EnumValue<Mode> mode = new EnumValue("Mode", Mode.Watchdog);

	private boolean flag;

	private TimeHelper timer = new TimeHelper();

	private final ArrayDeque<Packet> list = new ArrayDeque<>();
	
	private int watchdogCounter;
	private double watchdogMovement;
	private boolean groundCheck, watchdogPacket;

	public Disabler() {
		super("Disabler", 0, ModuleCategory.WORLD);
		addValues(mode);
		setHidden(true);
	}

	private enum Mode {
		FakeLag, Watchdog, Horizon, Verus, Ghostly, Test;
	}

	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		switch (mode.getValue()) {
			case FakeLag: {
				if (PlayerUtil.isOnServer("hypixel") || PlayerUtil.isOnServer("ilovecatgirls")) {
					if (mc.thePlayer.ticksExisted < .5) {
						list.clear();
					}
					if (timer.reach(ThreadLocalRandom.current().nextInt(3000, 5000))) {
						while (list.size() > 0) {
							mc.getNetHandler().addToSendQueueNoEvent(list.removeLast());
							timer.reset();
						}
					}
			
				}
				break;
			}
			case Ghostly: {
				break;
			}
			case Horizon: {
				break;
			}
			case Verus: {
				break;
			}
			case Watchdog: {
				break;
			}
			case Test: {
				double x = mc.thePlayer.posX;
				double y = mc.thePlayer.posY;
				double z = mc.thePlayer.posZ;
				for (int i = 0; i < 2; ++i) {
					
					mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 1, z, mc.thePlayer.onGround));
					mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 1, z, mc.thePlayer.onGround));
				}
				break;
			}
		}
	}

	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		boolean flagged = event.getPacket() instanceof S08PacketPlayerPosLook;
			switch (mode.getValue()) {
			case Ghostly: {
				break;
			}
			case Horizon: {
				break;
			}
			case Verus: {
				break;
			}
			case Watchdog: {
				if (flagged) {
					if (!flag) {
						flag = true;
						Notifications.getManager().post("Success", "You can do what you want for 5 seconds.",
								Notifications.Type.SUCCESS);
					}
				}
				break;
			}
			case FakeLag: {
				break;
			}
			case Test: {

				break;
			}
		}
	}

	@Handler
	public void onSendPacket(final EventPacketSend event) {
		boolean playerpacket = event.getPacket() instanceof C03PacketPlayer;
		switch (mode.getValue()) {
			case Ghostly: {
				if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
					event.setCancelled(true);
				}
				if (mc.thePlayer.ticksExisted % 3 == 0) {
					mc.getNetHandler().addToSendQueueNoEvent(new C18PacketSpectate(mc.thePlayer.getUniqueID()));
					mc.getNetHandler().addToSendQueueNoEvent(new C0CPacketInput(mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.jump, mc.thePlayer.movementInput.sneak));
				}
				break;
			}
			case Horizon: {
				break;
			}
			case Verus: {
				break;
			}
			case Watchdog: {
				if (playerpacket && !flag) {
					event.setCancelled(true);
				}
				break;
			}
			case FakeLag: {
				if (PlayerUtil.isOnServer("hypixel") || PlayerUtil.isOnServer("ilovecatgirls")) {
					if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
						final C0FPacketConfirmTransaction pp = (C0FPacketConfirmTransaction) event.getPacket();
						if (pp.getUid() < 0) {
							list.push(pp);
							event.setCancelled(true);
						}
					}
					if (event.getPacket() instanceof C00PacketKeepAlive){
						final C00PacketKeepAlive keepAlive = (C00PacketKeepAlive) event.getPacket();
						list.push(keepAlive);
						event.setCancelled(true);
					}
				}
				break;
			}
			case Test: {
				if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
					final C0FPacketConfirmTransaction packet = (C0FPacketConfirmTransaction) event.getPacket();
					if (packet.getUid() < 0) {
						event.setCancelled(true);
					}
				}
				break;
			}
		}
	}

	@Handler
	public void onMove(final EventMove event) {
		switch (mode.getValue()) {
		case Watchdog:
			if (!flag) {
				event.setCancelled(true);
			}
			break;
		case FakeLag:
			break;
		case Ghostly:
			break;
		case Horizon:
			break;
		case Verus:
			break;
		default:
			break;
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
		if (mc.thePlayer == null)
			return;
		flag = false;
		switch (mode.getValue()) {
		case Ghostly:
			break;
		case Horizon:
			break;
		case Verus:
			break;
		case Watchdog:
			mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
					mc.thePlayer.posY, mc.thePlayer.posZ, true));
			mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
					mc.thePlayer.posY + 0.18D, mc.thePlayer.posZ, true));
			mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
					mc.thePlayer.posY + 0.08D, mc.thePlayer.posZ, true));
			break;
		case FakeLag:
			timer.reset();
			break;
		case Test:
			watchdogPacket = false;
			watchdogCounter = 0;
			watchdogMovement = 0;
			groundCheck = mc.thePlayer.onGround;
			break;
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}