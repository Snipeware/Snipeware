package Snipeware.module.impl.movement;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.api.annotations.Priority;
import Snipeware.events.Event;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventCollide;
import Snipeware.events.player.EventJump;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.gui.notification.Notifications;
import Snipeware.module.Module;
import Snipeware.module.impl.combat.KillAura;
import Snipeware.module.impl.world.Disabler;
import Snipeware.util.other.Logger;
import Snipeware.util.other.PlayerUtil;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.player.MovementUtils;
import Snipeware.value.Value;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Flight extends Module {

	private EnumValue<Mode> flightMode = new EnumValue<>("Flight Mode", Mode.Vanilla);

	private NumberValue<Float> timerValue = new NumberValue<Float>("Timer Speed", 1.2f, 1.0f, 3.0f);

	private NumberValue<Float> timerDuration = new NumberValue<Float>("Timer Duration", 1000f, 100f, 2000f);

	private BooleanValue damage = new BooleanValue("Damage", true);

	private BooleanValue timerBoost = new BooleanValue("Timer Boost", false);

	private BooleanValue viewBobbing = new BooleanValue("View Bobbing", false);
	
	

	private EventPacketSend packetEvent;
	private TimeHelper longJumpTimerXD = new TimeHelper();
	


	private double lastDist;
	private double moveSpeed;
	private double moveSpeedMotion = 1F;
	private int stage = 0, counter;
	private EntityOtherPlayerMP clonedPlayer=null;
	private TimeHelper flytimer = new TimeHelper();

	private TimeHelper timerStopwatch = new TimeHelper();
	
	private TimeHelper Takatimer = new TimeHelper();
	private TimeHelper Takatimer2 = new TimeHelper();
	private TimeHelper Redesky = new TimeHelper();
	private final TimeHelper WatchdogTimer = new TimeHelper();
    private boolean wasAir = false;
	private TargetStrafe ts;
	private TimeHelper Blinktimer = new TimeHelper();
	public double yrarr;
	private boolean shouldBlink = false;
	private boolean pines = false;
	private double x;
	private double y;
	private double z;
	

	private float glideAmount2 = 4f;

	public Flight() {
		super("Flight", Keyboard.KEY_F, ModuleCategory.MOVEMENT);
		addValues(flightMode, timerValue, timerDuration, timerBoost, viewBobbing, damage);
	}

	private enum Mode {
		Vanilla, Watchdog, Verus, Taka, Motion, Test, Gay;
	}
	

	
	 @Handler
	    public void a(EventPacketSend event){
		 switch (flightMode.getValue()) {
		 case Taka:
			final EntityPlayerSP player = mc.thePlayer;
			if(!mc.gameSettings.keyBindJump.pressed) {
				player.motionY = 0.0;
			}else {
				player.motionY = 0.3;
			}
			if(mc.gameSettings.keyBindSneak.pressed) {
				player.motionY = -0.3;
			}
	        if(pines = false) {
	            if (event.getPacket() instanceof C03PacketPlayer) {
	            	C03PacketPlayer nigger = (C03PacketPlayer) event.getPacket();
	            	event.setCancelled(true);
	            }
	        }
	            break;
		 case Watchdog:
				final Packet p = event.getPacket();
				if (p instanceof C03PacketPlayer) {
					if(shouldBlink) {
			         event.setCancelled(true);
					}
					
				}
	    }
	 }

	public void onDisable() {
		super.onDisable();
		Takatimer.reset();
		
		switch (flightMode.getValue()) {
			case Vanilla:
				mc.thePlayer.capabilities.isFlying = false;
				break;
			case Watchdog:
			
				break;
			case Motion:
				MovementUtils.setMotion(0.0);
				mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ, mc.thePlayer.onGround));
		    	mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ, mc.thePlayer.onGround));
				break;
			case Verus:
				break;
			case Gay:
			      mc.setRenderViewEntity(mc.thePlayer);
                  mc.theWorld.removeEntityFromWorld(clonedPlayer.getEntityId());
                  clonedPlayer=null;
                  break;
		case Test:
			break;
		default:
			break;
		}
		if (ts == null) {
			ts = (TargetStrafe) Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class);
		}

		mc.timer.timerSpeed = 1.0f;
		timerStopwatch.reset();
	}

	public void onEnable() {
		super.onEnable();
		yrarr = mc.thePlayer.posZ;
		Blinktimer.reset();
		WatchdogTimer.reset();
		wasAir = false;
		if (mc.thePlayer == null) return;
		timerStopwatch.reset();
		longJumpTimerXD.reset();
		flytimer.reset();
		stage = 0;
		lastDist = 0.0;
		mc.timer.timerSpeed = 1.0f;
		moveSpeed = MovementUtils.getSpeed();
		switch (flightMode.getValue()) {
			case Watchdog:
				mc.thePlayer.motionY = 0.56f;
				damage2();
				x = mc.thePlayer.posX + 0.2f;
				y = mc.thePlayer.posY + 0.2f;
				z = mc.thePlayer.posZ + 0.2f;
				break;
			case Motion:

				break;
			case Vanilla:
				break;
			case Verus:
				break;
			case Taka:
				break;
			case Test:

				break;
		}
	}

	public void damage2() {
		shouldBlink = true;
	      double offset = 0.0060100000351667404D;
	      NetHandlerPlayClient netHandler = mc.getNetHandler();
	      EntityPlayerSP player = mc.thePlayer;
	      double x = player.posX;
	      double y = player.posY;
	      double z = player.posZ;

	      for(int i = 0; (double)i < (double)getMaxFallDist() / 0.05510000046342612D + 1.0D; ++i) {
	         netHandler.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.060100000351667404D, z, false));
	         netHandler.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.004999999888241291D + 6.01000003516674E-8D, z, false));
	      }

	      netHandler.addToSendQueueNoEvent(new C03PacketPlayer(true));
	     
	}

	@Handler
	public void onMove(final EventMove event) {
		 if(mc.thePlayer.hurtTime >= 4) {
	    	  shouldBlink = false;
	      }
		final EntityPlayerSP player = mc.thePlayer;
		final NetHandlerPlayClient netHandler = mc.getNetHandler();
		final GameSettings gameSettings = mc.gameSettings;
		switch (flightMode.getValue()) {
			case Watchdog:
				if(!mc.thePlayer.onGround) {
					mc.gameSettings.keyBindRight.pressed = false;
					mc.gameSettings.keyBindLeft.pressed = false;
					if(!timerStopwatch.isDelayComplete(550)) {
						MovementUtils.setSpeed(event, 0.4);
					}else {
						MovementUtils.setSpeed(event, 0.35);
					}
					if(timerStopwatch.isDelayComplete(800)) {
						MovementUtils.setSpeed(event,0.37);
					}
				}
				
				if(WatchdogTimer.isDelayComplete(300)) {
	    			
    				wasAir = true;
    			}
				
				if(mc.thePlayer.onGround && wasAir) {
	    			wasAir = false;
	    			toggle();
	    		}
				break;
			case Motion:
				event.setY(player.motionY = -0.0625);
				if (gameSettings.keyBindJump.isKeyDown()) {
					event.setY(player.motionY += 1.0);
				}
				else if (gameSettings.keyBindSneak.isKeyDown()) {
					event.setY(player.motionY -= 1.0);
				}
				if (!mc.thePlayer.isMoving()) {
					MovementUtils.setMotion(0);
				}

				//mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + glideValue2, mc.thePlayer.posZ);
				MovementUtils.setSpeed(event, 2);
				//mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
				//netHandler.addToSendQueueNoEvent(new C0CPacketInput(player.moveStrafing, player.moveForward, player.movementInput.jump, player.movementInput.sneak));
				break;
			case Vanilla:
				break;
			case Verus:
				break;
			case Taka:	
				
			
		
				
				MovementUtils.setSpeed(event, 1.08);
				break;
			case Gay:
				break;
			case Test:
				MovementUtils.setMotion(2f);
                float newSpeed = 2 * 0.725f;
                event.setY(mc.thePlayer.movementInput.jump ? newSpeed : mc.thePlayer.movementInput.sneak ? -newSpeed : mc.thePlayer.onGround ? 0.645443F : 0);
                mc.thePlayer.motionY = 0;
            	EntityPig entityHorse = new EntityPig(mc.theWorld);
            	mc.thePlayer.setPosition(entityHorse.posX, entityHorse.posY, entityHorse.posZ);
                mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(entityHorse, Action.RIDING_JUMP));
				break;
		}
	}



	
    private boolean nigger() {
    	if (longJumpTimerXD.isDelayComplete(300)) {
    		
    		return true;
    	}
    	
		return false;
	}

	public void damage() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        
        for (int i = 0; i < 10; i++) {
        	mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.42, z, false));
        	mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + MovementUtils.getJumpBoostModifier(0.42 % .0000625), z, false));
        	mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer(false));
        }
    }

    public float getMaxFallDist() {
        PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return (float) (mc.thePlayer.getMaxFallHeight() + f);
    }
    
    public float getMinFallDist() {
        float minDist = 3.0F;
        if (mc.thePlayer.isPotionActive(Potion.jump))
            minDist += mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0F;
        return minDist;
    }
    
    @Handler
    public void onJump(final EventJump event) {
    	final KillAura killaura = (KillAura) Client.INSTANCE.getModuleManager().getModule(KillAura.class);
    	event.setCancelled(killaura.target == null);
    	
    }
    
    @Handler(Priority.LOW)
    public void onReceivePacket(final EventPacketReceive event) {
    	if (event.getPacket() instanceof S08PacketPlayerPosLook) {
        	switch (flightMode.getValue()) {
        	case Watchdog:
        	
        		break;
			case Motion:
				break;
			case Vanilla:
				break;
			case Verus:
				break;
			case Test:
			default:
				break;
        	}
    	}
    }
    
    @Handler
    public void onPacketSend(final EventPacketSend event) {

    	switch (flightMode.getValue()) {
	    	case Gay: {
	    		break;
	    	}
	    	case Motion: {
	    		break;
	    	}
    	}
    }

    

    @Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		setSuffix(flightMode.getValueAsString());
		final EntityPlayerSP player = mc.thePlayer;
		final Timer timer = mc.timer;
		if (event.isPre()) {
			final double posX = player.posX - player.prevPosX;
			final double posZ = mc.thePlayer.posZ - player.prevPosZ;
			lastDist = Math.sqrt(posX * posX + posZ * posZ);
            counter++;
            if (viewBobbing.isEnabled()) {
            	player.cameraYaw = 0.08f;
            }
			switch (flightMode.getValue()) {
				case Watchdog:
					if(!mc.thePlayer.onGround) {
					mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x,y,z,false));
					}else {
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,false));
					}
					break;
				case Vanilla:
					player.capabilities.isFlying = true;
					break;
				case Verus:
					event.setOnGround(true);
					player.motionY = 0;
					player.onGround = true;
					break;
				case Motion:
				case Taka:
				
					event.setPitch(90);
					event.setYaw(90);
					
					if(Takatimer.reach(1000)) {
						pines = true;
						Takatimer.reset();
					}
					
					if(Takatimer2.reach(1300)) {
						pines = true;
						Takatimer.reset();
					}
					
					if(pines = true) {
			            int xPos = (int)mc.thePlayer.posX ;
			               int yPos = (int)mc.thePlayer.posY ;
			               int zPos = (int)mc.thePlayer.posZ ;
			               BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
			               Block block = mc.theWorld.getBlockState(blockPos).getBlock();
						  mc.getNetHandler().addToSendQueueNoEvent(new C0APacketAnimation());
		                  mc.getNetHandler().addToSendQueueNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
		  				BlockPos blockPos1 = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY - 1.0D, mc.thePlayer.posZ);
						Vec3d vec = new Vec3d(blockPos).addVector(0.4D, 0.4D, 0.4D).mul(0.4F);
						mc.playerController.onPlayerRightClick3d(mc.thePlayer, mc.theWorld, new ItemStack(Blocks.barrier), blockPos1, EnumFacing.UP, vec);

						
					
						event.setPosY(999);
						event.setLastPosX(-999);
						event.setLastPosY(-999);
						event.setLastPosZ(-999);
					}
					break;
				case Gay:
				
					   clonedPlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
					    clonedPlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
	                    clonedPlayer.copyLocationAndAnglesFrom(mc.thePlayer);
	                    mc.theWorld.addEntityToWorld((int) -(Math.random() * 10000), clonedPlayer);
	                    clonedPlayer.setInvisible(true);
	                    mc.setRenderViewEntity(clonedPlayer);
	                    clonedPlayer.inventory.copyInventory(mc.thePlayer.inventory);
	                    clonedPlayer.setHealth(mc.thePlayer.getHealth());
	                    clonedPlayer.rotationYaw=mc.thePlayer.rotationYaw;
	                    clonedPlayer.rotationPitch=mc.thePlayer.rotationPitch;
	                	clonedPlayer.motionY = 0;
	                	mc.thePlayer.motionY = 0;
	                    if(Redesky.isDelayComplete(100)){
	                    	  clonedPlayer.setPosition(event.getPosX(),event.getPosY(),event.getPosZ());
	                    	Redesky.reset();
	                    }
					  
		                break;
				
				default:
					break;
			}
		}
	}
}
