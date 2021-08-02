package felix.module.impl.world;

import felix.api.annotations.Handler;
import felix.events.packet.EventPacketSend;
import felix.events.player.EventCollide;
import felix.events.player.EventMotionUpdate;
import felix.events.render.EventModelUpdate;
import felix.events.render.EventRender2D;
import felix.module.Module;
import felix.module.impl.movement.Speed.Redemode;
import felix.module.impl.player.Safewalk;
import felix.util.other.InventoryUtils;
import felix.util.other.TimeHelper;
import felix.util.player.MovementUtils;
import felix.util.player.Rotation;
import felix.util.player.RotationUtils;
import felix.util.visual.RenderUtil;
import felix.value.impl.BooleanValue;
import felix.value.impl.ColorValue;
import felix.util.player.setBlockAndFacing;
import felix.value.impl.EnumValue;
import felix.value.impl.NumberValue;
import javafx.scene.transform.Scale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



public class Scaffold extends Module {


private final float[] rotations = new float[2];
private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
private final List<Block> badBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence);
private BlockData blockData;
private BooleanValue safewalk = new BooleanValue("Safewalk", true);
private BooleanValue keepsprint = new BooleanValue("Sprint", false);
private BooleanValue silient = new BooleanValue("Silent", true);
private BooleanValue tower  = new BooleanValue("Tower", false);
private BooleanValue keeprots = new BooleanValue("Keep Rotations", true);
private BooleanValue Swing = new BooleanValue("Swing", true);
private BooleanValue keepy = new BooleanValue("Keepy", false);
private BooleanValue eagle = new BooleanValue("Eagle", false);
public  BooleanValue blockCountBarProperty = new BooleanValue("Block count", true);
private BooleanValue edge = new BooleanValue("Edge", false);
private BooleanValue raycast = new BooleanValue("Raycast", false);
public NumberValue<Float> TimerBoost = new NumberValue<>("Time Speed", 1.00f, 0.1f, 5f, 0.1f);
public NumberValue<Float> delay = new NumberValue<>("Delay", 50f, 0f, 1000f, 10f);
public NumberValue<Float> eageOffset = new NumberValue<>("Edge Offset", 0.13f, 0f, 5f, 0.1f);
int stage = 0;
public static boolean isPlaceTick = false;
public static boolean stopWalk = false;
private double startY;
public TimeHelper towerTimer = new TimeHelper();
private int count;
private static BlockPos currentPos;
private EnumFacing currentFacing;
private boolean rotated = false;
private final TimeHelper timer = new TimeHelper();

float oldPitch = 0;
private RotationUtils RayCastUtil;

public final EnumValue<ScafMode> scafMode  = new EnumValue<>("SC-Mode", ScafMode.Normal);
private EnumValue<TowerMode> towerMode  = new EnumValue<>("TowerMode", TowerMode.Hypixel);


public Scaffold() {
    super("Scaffold", 0, ModuleCategory.WORLD);
    this.addValues(towerMode, scafMode, safewalk, keepsprint, silient, blockCountBarProperty, tower, keeprots, Swing, keepy ,eagle, edge, raycast, TimerBoost , delay, eageOffset);
}

private enum ScafMode {
	Normal,
	Hypixel,
	
	}

private enum TowerMode {
	Hypixel, Packet
	
	}



float yaw = 0;
float pitch = 0;

private boolean isBlockUnder() {
    for (int i = (int) (mc.thePlayer.posY - 1.0); i > 0; --i) {
        BlockPos pos = new BlockPos( mc.thePlayer.posX, i, mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
        return true;
    }
    return false;
}

@Override
public void onEnable() {
    super.onEnable();
    timer.reset();
    slotTimer.reset();
    ticks = 0;
    if (mc.thePlayer !=null) {
        startY = mc.thePlayer.posY;
    }
    
   
    
}

float lastYaw = 0;

public void fakeJump() {
    mc.thePlayer.isAirBorne = true;
    mc.thePlayer.triggerAchievement(StatList.jumpStat);

}

int ticks = 0;






@Handler
public void onMotionUpdate(final EventMotionUpdate event) {
	if(!mc.gameSettings.keyBindJump.pressed && mc.thePlayer.onGround) {
	 mc.timer.timerSpeed = TimerBoost.getValue();
	}else{
		mc.timer.timerSpeed = 1;
	}
    setSuffix ( scafMode.getValueAsString () ); 

            int slot = this.getSlot ();
            stopWalk = (getBlockCount () == 0 || slot == -1) && safewalk.getValue ().booleanValue ();
            isPlaceTick = keeprots.getValue ().booleanValue () ? blockData != null && slot != -1 : blockData != null && slot != -1 && mc.theWorld.getBlockState ( new BlockPos ( mc.thePlayer ).add ( 0, -1, 0 ) ).getBlock () == Blocks.air;
            if (slot == -1) {
                moveBlocksToHotbar ();

                return;
            }
           
            this.blockData = getBlockData ();
            if (this.blockData == null) {
                return;
            }

            // tower and towermove
            if (mc.gameSettings.keyBindJump.isKeyDown () && tower.getValue ().booleanValue () && !mc.thePlayer.isMoving () && !mc.thePlayer.isPotionActive ( Potion.jump )) {
                setSuffix ( scafMode.getValueAsString () );
                switch (towerMode.getValueAsString ()) { 
                    case "Hypixel":
                        EntityPlayerSP player = mc.thePlayer;
                        if (!MovementUtils.isOnGround ( 0.79 ) || mc.thePlayer.onGround) {
                            player.motionY = 0.41985;
                            stage = 1;
                        }
                        if (towerTimer.reach ( 1500 )) {
                        
                            player.motionY = -1;
                            towerTimer.reset();
                        }


                    case "Packet":
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.setPosition ( mc.thePlayer.posX, mc.thePlayer.posY + 0.99, mc.thePlayer.posZ );
                            mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition ( mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688698, mc.thePlayer.posZ, false ) );
                            mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition ( mc.thePlayer.posX, mc.thePlayer.posY + 0.7531999805212, mc.thePlayer.posZ, false ) );
                        }
                    case "Cubecraft":
                        count++;
                        mc.thePlayer.motionX = 0;
                        mc.thePlayer.motionZ = 0;
                        mc.thePlayer.jumpMovementFactor = 0;
                        if (MovementUtils.isOnGround ( 2 ))
                            if (count == 1) {
                                mc.thePlayer.motionY = 0.41;
                            } else {

                                mc.thePlayer.motionY = 0.47;
                                count = 0;
                            }
                }

            } else {
                towerTimer.reset ();
            }

            if (isPlaceTick) {
               Rotation targetRotation = new Rotation ( setBlockAndFacing.BlockUtil.getDirectionToBlock ( blockData.getPosition ().getX (), blockData.getPosition ().getY (), blockData.getPosition ().getZ (), blockData.getFacing () )[0], 79.44f );
                Rotation limitedRotation = setBlockAndFacing.BlockUtil.limitAngleChange ( new Rotation ( yaw, event.getPitch () ), targetRotation, (float) ThreadLocalRandom.current ().nextDouble ( 20, 30 ) );
                yaw = getRotations(blockData.getPosition(), blockData.getFacing())[0];
                pitch = limitedRotation.getPitch ();
                
              
                
            	if (this.scafMode.getValue() == ScafMode.Hypixel) {
                
                if(!mc.gameSettings.keyBindJump.pressed) {
                
                event.setYaw(mc.thePlayer.rotationYaw - 170);
                event.setPitch(79);
                }else {
                	  event.setYaw(mc.thePlayer.rotationYaw - 190);
                      event.setPitch(90);
                }
               
                
            	}else if(this.scafMode.getValue() == ScafMode.Normal) {
                
             
                    if(!mc.gameSettings.keyBindJump.pressed) {
                        
                        event.setYaw(mc.thePlayer.rotationYaw - 180);
                        event.setPitch(79);
                        }else {
                        	  event.setYaw(mc.thePlayer.rotationYaw - 180);
                              event.setPitch(90);
                        }
                
                
                  
               
                }
             



            rotated = false;
            currentPos = null;
            currentFacing = null;

            BlockPos pos = new BlockPos ( mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ );
            if (mc.theWorld.getBlockState ( pos ).getBlock () instanceof BlockAir) {
                setBlockAndFacing ( pos );

                if (currentPos != null) {
                    float[] facing = setBlockAndFacing.BlockUtil.getDirectionToBlock ( currentPos.getX (), currentPos.getY (), currentPos.getZ (), currentFacing );

                    float yaw = facing[0] + randomNumber ( 3, -3 );
                    float pitch = Math.min ( 90, facing[1] + 9 + randomNumber ( 3, -3 ) );

                    rotations[0] = yaw;
                    rotations[1] = pitch;

                    rotated = !raycast.getValue ().booleanValue () || rayTrace ( yaw, pitch );


                }
            } else {
            
            	if (this.scafMode.getValue() == ScafMode.Hypixel) {
            	
            	if(!mc.gameSettings.keyBindJump.pressed) {
                if (keeprots.getValue ().booleanValue ()) {
                    event.setYaw(mc.thePlayer.rotationYaw - 170);
                    event.setPitch(79);
                }
            	}else {
            		if (keeprots.getValue ().booleanValue ()) {
                        event.setYaw(mc.thePlayer.rotationYaw - 190);
                        event.setPitch(90);
                    }
            	}
            	}else if(this.scafMode.getValue() == ScafMode.Normal) {
            	   
            	
            
            			if(!mc.gameSettings.keyBindJump.pressed) {
                            if (keeprots.getValue ().booleanValue ()) {
                                event.setYaw(mc.thePlayer.rotationYaw - 180);
                                event.setPitch(79);
                            }
                        	}else {
                        		if (keeprots.getValue ().booleanValue ()) {
                                    event.setYaw(mc.thePlayer.rotationYaw - 180);
                                    event.setPitch(90);
                                }
                        	}
            	
            		  
            	  }
            }
    }
    mc.thePlayer.rotationYawHead = event.getYaw ();
    //mc.thePlayer.rotationPitchHead = event.getPitch();
    mc.thePlayer.renderYawOffset = event.getYaw ();

}


@Handler
public void onMotionUpdate1(final EventMotionUpdate event) {
    //setSuffix ( towerMode.getValueAsString () );
	if(event.isPre()) {
    if (!this.keepsprint.getValue ().booleanValue ()) {
            mc.thePlayer.setSprinting(false);
           

            mc.gameSettings.keyBindSprint.pressed = false;

    }

    int slot = this.getSlot ();
    double x = mc.thePlayer.posX;
    double z = mc.thePlayer.posZ;
    double forward = MovementInput.moveForward;
    double strafe = MovementInput.moveStrafe;
    float YAW = mc.thePlayer.rotationYaw;
 
    BlockPos pos = new BlockPos ( x, mc.thePlayer.posY - 1, z );
    if (slot != -1 && this.blockData != null) {
        final int currentSlot = mc.thePlayer.inventory.currentItem;
        if (pos.getBlock () instanceof BlockAir) {
                mc.thePlayer.inventory.currentItem = slot;
            if (this.getPlaceBlock ( this.blockData.getPosition (), this.blockData.getFacing () )) {
                mc.thePlayer.sendQueue.addToSendQueue ( new C09PacketHeldItemChange ( currentSlot ) );
            }
        } else {
            mc.timer.timerSpeed = 1.0f;
        }
        if (silient.getValue()) {
            mc.thePlayer.inventory.currentItem = currentSlot;
        }
        switch (towerMode.getValueAsString ()) {
            case "Packet":
                for (int i = 0; i < 9; i++) {
                    if (mc.thePlayer.inventory.getStackInSlot ( i ) == null)
                        continue;
                    if (mc.thePlayer.inventory.getStackInSlot ( i ).getItem () instanceof ItemBlock) {
                        mc.thePlayer.sendQueue.addToSendQueueNoEvent ( new C09PacketHeldItemChange ( mc.thePlayer.inventory.currentItem = i ) );
                    }
                }
                if (currentPos != null) {
                    if (timer.reach ( this.delay.getValue () ) && rotated) {
                        if (mc.thePlayer.getCurrentEquippedItem () != null && mc.thePlayer.getCurrentEquippedItem ().getItem () instanceof ItemBlock) {
                            if (mc.playerController.onPlayerRightClick ( mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem (), currentPos, currentFacing, new Vec3 ( currentPos.getX () * 0.5, currentPos.getY () * 0.5, currentPos.getZ () * 0.5 ) )) {
                                timer.reset ();
                                if (Swing.getValue ().booleanValue ()) {
                                    mc.thePlayer.swingItem ();
                                } else {
                                    mc.getNetHandler ().addToSendQueueNoEvent ( new C0APacketAnimation () );
                                }

                            }
                        }
                    }
                }
        }
    }
}
}

private boolean getPlaceBlock(final BlockPos pos, final EnumFacing facing) {
    final Vec3 eyesPos = new Vec3( mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
    Vec3i data = this.blockData.getFacing().getDirectionVec();
    if (timer.reach( this.delay.getValue() )){
        if (edge.getValue() ? mc.playerController.onPlayerRightClick( mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, facing, getVec3(new BlockData(pos, facing))) && isOnEdgeWithOffset(eageOffset.getValue()) : mc.playerController.onPlayerRightClick( mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, facing, getVec3(new BlockData(pos, facing)))) {
            if (this.Swing.getValue ().booleanValue ()) {
                mc.thePlayer.swingItem();
            } else {
                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            }

            timer.reset();
            return true;
        }


    }
    return false;
}

private Vec3 getVec3(BlockData data) {
    BlockPos pos = data.getPosition();
    EnumFacing face = data.getFacing();
    double x = (double) pos.getX() + 0.5D;
    double y = (double) pos.getY() + 0.5D;
    double z = (double) pos.getZ() + 0.5D;
    x += (double) face.getFrontOffsetX() / 2.0D;
    z += (double) face.getFrontOffsetZ() / 2.0D;
    y += (double) face.getFrontOffsetY() / 2.0D;
    if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
        y += this.randomNumber(0.49D, 0.5D);
    } else {
        x += this.randomNumber(0.3D, -0.3D);
        z += this.randomNumber(0.3D, -0.3D);
    }

    if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
        z += this.randomNumber(0.3D, -0.3D);
    }

    if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
        x += this.randomNumber(0.3D, -0.3D);
    }

    return new Vec3(x, y, z);
}

private double randomNumber(double max, double min) {
    return Math.random() * (max - min) + min;
}

public static boolean rayTrace(float yaw, float pitch) {
    Vec3 vec3 = Minecraft.getMinecraft().thePlayer.getPositionEyes(1.0f);
    Vec3 vec31 = RotationUtils.getVectorForRotation (new float[]{yaw, pitch});
    Vec3 vec32 = vec3.addVector(vec31.xCoord * 5, vec31.yCoord * 5, vec31.zCoord * 5);


    MovingObjectPosition result = Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, vec32, false);


    return result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && currentPos.equals(result.getBlockPos());
}

static Random rng = new Random();

public static int getRandom(final int floor, final int cap) {
    return floor + rng.nextInt(cap - floor + 1);
}

public static Color rainbow(int delay) {
    double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
    rainbowState %= 360;
    return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f);
}

public static float[] getRotations(BlockPos block, EnumFacing face) {
    double x = block.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posX +  (double) face.getFrontOffsetX()/2;
    double z = block.getZ() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ +  (double) face.getFrontOffsetZ()/2;
    double y = (block.getY() + 0.5);
    double d1 = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - y;
    double d3 = MathHelper.sqrt_double(x * x + z * z);
    float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
    float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
    if (yaw < 0.0F) {
        yaw += 360f;
    }
    return new float[]{yaw, pitch};
}

public void setBlockAndFacing(BlockPos var1) {

    //if(!shouldDownwards()) {
    if (mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, -1, 0);
        currentFacing = EnumFacing.UP;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 0, 0);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 0, 0);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {

        currentPos = var1.add(0, 0, -1);
        currentFacing = EnumFacing.SOUTH;

    } else if (mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {

        currentPos = var1.add(0, 0, 1);
        currentFacing = EnumFacing.NORTH;

    } else if (mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 0, -1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 0, 1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 0, -1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 0, 1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, -1, 0);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, -1, 0);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, -1, -1);
        currentFacing = EnumFacing.SOUTH;
    } else if (mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, -1, 1);
        currentFacing = EnumFacing.NORTH;
    } else if (mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, -1, -1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, -1, 1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, -1, -1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, -1, 1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(-2, 0, 0);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(2, 0, 0);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 0, -2);
        currentFacing = EnumFacing.SOUTH;
    } else if (mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 0, 2);
        currentFacing = EnumFacing.NORTH;
    } else if (mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
        currentPos = var1.add(-2, 0, -2);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
        currentPos = var1.add(-2, 0, 2);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
        currentPos = var1.add(2, 0, -2);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
        currentPos = var1.add(2, 0, 2);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, 1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 1, 0);
        currentFacing = EnumFacing.DOWN;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 1, 0);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 1, 0)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 1, 0);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(0, 1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 1, -1);
        currentFacing = EnumFacing.SOUTH;
    } else if (mc.theWorld.getBlockState(var1.add(0, 1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(0, 1, 1);
        currentFacing = EnumFacing.NORTH;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 1, -1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(-1, 1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(-1, 1, 1);
        currentFacing = EnumFacing.EAST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 1, -1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 1, -1);
        currentFacing = EnumFacing.WEST;
    } else if (mc.theWorld.getBlockState(var1.add(1, 1, 1)).getBlock() != Blocks.air) {
        currentPos = var1.add(1, 1, 1);
        currentFacing = EnumFacing.WEST;
    }
}



private float[] aimAtLocation(BlockPos paramBlockPos, EnumFacing paramEnumFacing) {
    double d1 = paramBlockPos.getX() + 0.5D - mc.thePlayer.posX + paramEnumFacing.getFrontOffsetX() / 2.0D;
    double d2 = paramBlockPos.getZ() + 0.5D - mc.thePlayer.posZ + paramEnumFacing.getFrontOffsetZ() / 2.0D;
    double d3 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - (paramBlockPos.getY() + 0.5D);
    double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
    float f1 = (float) (Math.atan2(d2, d1) * 180.0D / 3.141592653589793D) - 90.0F;
    float f2 = (float) (Math.atan2(d3, d4) * 180.0D / 3.141592653589793D);
    if (f1 < 0.0F) {
        f1 += 360.0F;
    }
    return new float[]{f1, f2};
}

@Override
public void onDisable() {
    super.onDisable();
    this.setSneaking(false);
    mc.timer.timerSpeed = 1f;
}

private void setSneaking(boolean b) {
    KeyBinding sneakBinding = mc.gameSettings.keyBindSneak;
    mc.gameSettings.keyBindSneak.pressed = b;
}

public BlockData getBlockData() {
    final EnumFacing[] invert = {EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
    double yValue = 0;
    /*if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && !mc.gameSettings.keyBindJump.isKeyDown() && blockfly.getValue ().booleanValue ()) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        yValue -= 0.6;
    }*/
    BlockPos aa = new BlockPos( mc.thePlayer.getPositionVector()).offset(EnumFacing.DOWN).add(0, yValue, 0);
    BlockPos playerpos = aa;
 
 
    boolean tower = !this.tower.getValue ().booleanValue () && !mc.thePlayer.isMoving();
    if (/*!this.blockfly.getValue ().booleanValue () &&*/ this.keepy.getValue ().booleanValue () && !tower && mc.thePlayer.isMoving()) {
        playerpos = new BlockPos(new Vec3( mc.thePlayer.getPositionVector().xCoord, this.startY, mc.thePlayer.getPositionVector().zCoord)).offset(EnumFacing.DOWN);
    } else {
        this.startY = mc.thePlayer.posY;
    }

    for (EnumFacing facing : EnumFacing.values()) {
        if (playerpos.offset(facing).getBlock().getMaterial() != Material.air) {
            return new BlockData(playerpos.offset(facing), invert[facing.ordinal()]);
        }
    }
    final BlockPos[] addons = {
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, -1),
            new BlockPos(0, 0, 1)};

    for (int length2 = addons.length, j = 0; j < length2; ++j) {
        final BlockPos offsetPos = playerpos.add(addons[j].getX(), 0, addons[j].getZ());
        if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
            for (int k = 0; k < EnumFacing.values().length; ++k) {
                if (mc.theWorld.getBlockState(offsetPos.offset(EnumFacing.values()[k])).getBlock().getMaterial() != Material.air) {

                    return new BlockData(offsetPos.offset(EnumFacing.values()[k]), invert[EnumFacing.values()[k].ordinal()]);
                }
            }
        }
    }

    return null;
}

int slotIndex = 0;

private final TimeHelper slotTimer = new TimeHelper();

private int getSlot() {
    ArrayList<Integer> slots = new ArrayList<>();
    for (int k = 0; k < 9; ++k) {
        final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
        if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
            slots.add(k);
        }
    }
    if (slots.isEmpty()) {
        return -1;
    }
    /*if (slotTimer.hasReached(150)) {
        if (slotIndex >= slots.size() || slotIndex == slots.size() - 1) {
            slotIndex = 0;
        } else {
            slotIndex++;
        }
        slotTimer.reset();
    }*/
    return slots.get(slotIndex);
}
@Handler
public void a(EventRender2D event) {
    if (blockCountBarProperty.getValue()) {
        ScaledResolution sr = new ScaledResolution(mc);
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                continue;
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (!(is.getItem() instanceof ItemBlock) || !InventoryUtils.isValidBlock(((ItemBlock) item).getBlock(), false))
                continue;
            blockCount += is.stackSize;
        }
        int color = new Color(255, 0, 0).getRGB();
        int bgcolor = new Color(1,1,1).getRGB();
        if (blockCount >= 64 && 128 > blockCount) {
            color = new Color(255, 255, 0).getRGB();
        } else if (blockCount >= 128) {
            color = new Color(0, 255, 0).getRGB();
        }

        GlStateManager.pushMatrix();
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2 + 1, (sr.getScaledHeight() >> 1) - 15, bgcolor);
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2 - 1, (sr.getScaledHeight() >> 1) - 15, bgcolor);
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2, (sr.getScaledHeight() >> 1) - 15 + 1, bgcolor);
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2, (sr.getScaledHeight() >> 1) - 15 - 1, bgcolor);
        mc.fontRendererObj.drawString(Integer.toString(blockCount), (sr.getScaledWidth() >> 1)  - mc.fontRendererObj.getStringWidth(Integer.toString(blockCount)) / 2, (sr.getScaledHeight() >> 1) - 15, color);
        GlStateManager.popMatrix();
    }
}


private boolean isValid(ItemStack itemStack) {
    if (itemStack.getItem() instanceof ItemBlock) {
        boolean isBad = false;

        ItemBlock block = (ItemBlock) itemStack.getItem();
        for (int i = 0; i < this.badBlocks.size(); i++) {
            if (block.getBlock().equals(this.badBlocks.get(i))) {
                isBad = true;
            }
        }

        return !isBad;
    }
    return false;
}

public int getBlockCount() {
    int count = 0;
    for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
        final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
        if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
            count += itemStack.stackSize;
        }
    }
    return count;
}

public class BlockData {
    private final BlockPos blockPos;
    private final EnumFacing enumFacing;

    public BlockData(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
    }

    public EnumFacing getFacing() {
        return this.enumFacing;
    }

    public BlockPos getPosition() {
        return this.blockPos;
    }
}

private void moveBlocksToHotbar() {
    boolean added = false;
    if (!isHotbarFull()) {
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            if (k > 8 && !added) {
                final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
                if (itemStack != null && this.isValid(itemStack)) {
                    shiftClick(k);
                    added = true;
                }
            }
        }
    }
}

public boolean isHotbarFull() {
    int count = 0;
    for (int k = 0; k < 9; ++k) {
        final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
        if (itemStack != null) {
            count++;
        }
    }
    return count == 8;
}

public float setSmooth(float current, float target, float speed) {
    if (target - current > 0) {
        current -= speed;
    } else {
        current += speed;
    }
    return current;
}

public static void shiftClick(int slot) {
   Minecraft.getMinecraft().playerController.windowClick( Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer );
   Minecraft.getMinecraft().playerController.windowClick( Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer );
}

public boolean isAirBlock(Block block) {
    if (block.getMaterial().isReplaceable()) {
        return !(block instanceof BlockSnow) || !(block.getBlockBoundsMaxY() > 0.125);
    }

    return false;
}

public static int randomNumber(int max, int min) {
    return Math.round(min + (float) Math.random() * ((max - min)));
}

//Thx To domi
private boolean isOnEdgeWithOffset(double paramDouble) {
    double d1 = mc.thePlayer.posX;
    double d2 = mc.thePlayer.posY;
    double d3 = mc.thePlayer.posZ;
    BlockPos blockPos1 = new BlockPos(d1 - paramDouble, d2 - 0.5D, d3 - paramDouble);
    BlockPos blockPos2 = new BlockPos(d1 - paramDouble, d2 - 0.5D, d3 + paramDouble);
    BlockPos blockPos3 = new BlockPos(d1 + paramDouble, d2 - 0.5D, d3 + paramDouble);
    BlockPos blockPos4 = new BlockPos(d1 + paramDouble, d2 - 0.5D, d3 - paramDouble);
    return (mc.thePlayer.worldObj.getBlockState(blockPos1).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos2).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos3).getBlock() == Blocks.air && mc.thePlayer.worldObj.getBlockState(blockPos4).getBlock() == Blocks.air);
}
}

