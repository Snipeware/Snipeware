package felix.command.impl;

import org.lwjgl.input.Keyboard;

import felix.Client;
import felix.command.Command;
import felix.module.Module;
import felix.util.other.Logger;
import net.minecraft.client.Minecraft;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class ign extends Command {

    public ign() {
        super("ign");
    }

    @Override
    public String usage() {
        return "ign";
    }

    @Override
    public void executeCommand(String[] commandArguments) {
        Logger.print("Copied " + Minecraft.getMinecraft().thePlayer.getNameClear() + " to clipboard");
        String Name = Minecraft.getMinecraft().thePlayer.getNameClear();
        StringSelection stringSelection = new StringSelection(Name);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        
    }
}
