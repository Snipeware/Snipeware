package Snipeware.events.player;

import Snipeware.events.Cancellable;
import Snipeware.events.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventLiquidCollide extends Cancellable implements Event {

    private Block block;
    private BlockPos pos;
    private AxisAlignedBB bounds;

    public EventLiquidCollide(Block block, BlockPos pos, AxisAlignedBB bounds) {
        this.block = block;
        this.pos = pos;
        this.bounds = bounds;;
    }

    public AxisAlignedBB getBounds() {
        return bounds;
    }

    public void setBounds(AxisAlignedBB bounds) {
        this.bounds = bounds;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Block getBlock() {
        return block;
    }
}
