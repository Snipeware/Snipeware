package felix.module.impl.player;

import java.util.Random;

import felix.api.annotations.Handler;
import felix.events.packet.EventPacketReceive;
import felix.module.Module;
import felix.util.other.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;

public final class KillSay extends Module {
	
	private int messageIndex;
	private final String[] MESSAGES = new String[]{"Don't mind me, %s I'm just testing the anticheat!","%s ip is: " + generateRandomPassword(2) + "." + generateRandomPassword(2) + "." + generateRandomPassword(2) + "." + generateRandomPassword(2), "%s got professionally nae nae’d", "Back to the lobby you go! %s", "I'm not hacking, %s just wasn't clicking fast enough", "Have fun in spectator %s", "%s why not use Snipeware discord.gg/snipeware", "%s, you're this bad? Probably this is why your friends hated you, OH WAIT you dont have one", "%s you need to press the left big button on your mouse to win, np", "%s, can you friend me? So we can talk about how useless you are", "%s is so fat they cause the tide to come in", "%s im a lixo hacker now cry", "How did %s even press the start game button", "%s, if you're this bad, go outside and touch grass"};
	
	public KillSay() {
		super("KillSay", 0, ModuleCategory.PLAYER);
		setHidden(true);
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
	

	public static String generateRandomPassword(int len) {
		String chars = "0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

	
	
	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		if (event.getPacket() instanceof S02PacketChat) {
			final S02PacketChat packet = (S02PacketChat) event.getPacket();
			String text = packet.getChatComponent().getUnformattedText();
			if (text.contains("foi morto por " + mc.thePlayer.getGameProfile().getName())) {			
				if (messageIndex >= MESSAGES.length) {
					messageIndex = 0;
				}
			
				mc.thePlayer.sendChatMessage(String.format(MESSAGES[messageIndex], text.split(" ")[0]));
				++messageIndex;
			}
			if (PlayerUtil.isOnServer("mineplex")) {
				text = text.substring(text.indexOf(" "));
			}
			if (text.contains("by " + mc.thePlayer.getGameProfile().getName())) {				
				if (messageIndex >= MESSAGES.length) {
					messageIndex = 0;
				}
				
				
				mc.thePlayer.sendChatMessage(String.format(MESSAGES[messageIndex], text.split(" ")[0]));
				++messageIndex;
			}
		}
	
	}
}

