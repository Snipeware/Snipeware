package Snipeware.module.impl.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.value.impl.BooleanValue;

import java.util.Objects;

public class InvMove extends Module {

    public InvMove() {
        super("InvMove", 0, ModuleCategory.PLAYER);
        setHidden(true);
    }
}