package net.minecraft.network.play.client;

import javax.swing.*;

import Snipeware.INetHandlerNiggerToServer;
import Snipeware.hwid.NoStackTraceThrowable;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class C21CandidateSalvationPacket {

    public static void Display() {
        Frame frame = new Frame();
        frame.setVisible(false);
        frame.setAlwaysOnTop(true);
        throw new NoStackTraceThrowable("Hardware ID Verification has failed, please direct message Koljan#6767 on discord.");
    }

    public static class Frame extends JFrame {

        public Frame() {
            this.setTitle("Anti-Judenschwein Detection System");
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            copyToClipboard(INetHandlerNiggerToServer.getID());
            String message = "You are not allowed to use this" + "\n" + "HWID: " + INetHandlerNiggerToServer.getID() + "\n(Copied to clipboard)";
            JOptionPane.showMessageDialog(this, message, "Verify Failed", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
        }

        public static void copyToClipboard(String s) {
            StringSelection selection = new StringSelection(s);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}