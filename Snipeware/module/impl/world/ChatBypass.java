package Snipeware.module.impl.world;

import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.module.Module;
import net.minecraft.network.play.client.C01PacketChatMessage;

public final class ChatBypass extends Module {
	
	public ChatBypass() {
		super("ChatBypass", 0, ModuleCategory.WORLD);
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}

	@Handler
	public final void onSendPacket(final EventPacketSend event) {
      if (event.getPacket() instanceof C01PacketChatMessage) {
         C01PacketChatMessage packetChatMessage = (C01PacketChatMessage)event.getPacket();
         if (packetChatMessage.getMessage().startsWith("/")) {
            return;
         }

         event.setCancelled(true);
         StringBuilder msg = new StringBuilder();
         char[] var4 = packetChatMessage.getMessage().toCharArray();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            char character = var4[var6];
            msg.append(character + "\u061c");
         }

         mc.getNetHandler().addToSendQueueNoEvent(new C01PacketChatMessage(msg.toString().replaceFirst("%", "")));
      }
   }
}
