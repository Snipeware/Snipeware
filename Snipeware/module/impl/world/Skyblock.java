package Snipeware.module.impl.world;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import Snipeware.api.objects.Root;
import Snipeware.api.objects.Root2;
import com.fasterxml.jackson.databind.ObjectMapper;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.player.EventKeyPress;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.other.TimeHelper;
import Snipeware.value.impl.EnumValue;
import net.minecraft.network.play.server.S02PacketChat;

public class Skyblock extends Module {
	private EnumValue<Mode> mode  = new EnumValue<>("Macro Mode", Mode.Netherwart);
	private TimeHelper Delay = new TimeHelper();
	private TimeHelper Delay2 = new TimeHelper();
	private TimeHelper Delay3 = new TimeHelper();
	private TimeHelper Delay4 = new TimeHelper();
	private TimeHelper Delay5 = new TimeHelper();
	private boolean shouldcall = false;
	//private boolean exec = false;
	private Root2 root2;
	private String s2;
	private boolean start;
	private int stage = 0;
	public ArrayList<String> Bins = new ArrayList<String>();

	public Skyblock() {
		super("Skyblock",0, ModuleCategory.WORLD);
		addValues(mode);
	}
	
	public void onEnable() {
		Delay3.reset();
		Delay2.reset();
		Delay4.reset();
		Delay5.reset();
		start = false;
		stage = 0;
		switch(mode.getValue()) {
		case Netherwart:
		//	Logger.print("Press Enter to Start");
			break;
		}
		super.onEnable();
	}
	
	public void onDisable() {
		switch(mode.getValue()) {
		case Netherwart:
	  	  mc.gameSettings.keyBindAttack.pressed = false;
    	  mc.gameSettings.keyBindForward.pressed = false;
		  mc.gameSettings.keyBindLeft.pressed = false;
		  mc.gameSettings.keyBindRight.pressed = false;
		  break;
		}
		super.onDisable();
	}
	
	
	private enum Mode {
		Netherwart, Auction
	}
	
	
	 @Handler
		public void onKeyPress(final EventKeyPress event) {
		 if(Client.getInstance().getModuleManager().getModule("Skyblock").isEnabled()) {
		 if(event.getKey() == 28) {
			 start = !start;
			 if(start == false) {
				 Logger.print("The macro was turned off");
			 	  mc.gameSettings.keyBindAttack.pressed = false;
		    	  mc.gameSettings.keyBindForward.pressed = false;
				  mc.gameSettings.keyBindLeft.pressed = false;
				  mc.gameSettings.keyBindRight.pressed = false;
			 }else {
				 mc.thePlayer.rotationPitch = 0;
				 mc.thePlayer.rotationYaw = -90;
				 
				 Logger.print("The macro was turned on");
			 }
		 }
	 }
	 }
	 
		@Handler
		public void onReceivePacket(final EventPacketReceive event) {
			if(start) {
				if (event.getPacket() instanceof S02PacketChat) {
					final S02PacketChat packet = (S02PacketChat) event.getPacket();
					String text = packet.getChatComponent().getUnformattedText();
					if (text.contains("You were spawned in Limbo")) {			
						mc.thePlayer.sendChatMessage("/lobby");
					}
				}
			}
		}
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event){
		if(Client.getInstance().getModuleManager().getModule("Skyblock").isEnabled()) {
		switch(mode.getValue()) {
		case Auction:
		if(Delay.isDelayComplete(5000)) {
		try {
			ObjectMapper om = new ObjectMapper();
			s2 = Caller("https://api.hypixel.net/skyblock/auctions?key=00000000-0000-0000-0000-000000000000&page=0");
			root2 = om.readValue(s2, Root2.class);
			
			//Auction auction = om.readValue(s2, Auction.class);
			//Bid bid = om.readValue(s2, Bid.class);
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			Delay.reset();
			} 
			System.out.println(root2.page);
			for(int i = 0; i < root2.totalAuctions; i++) {
			//	if(root2.auctions.get(i).bin) {
			//		Bins.add("Item: " + root2.auctions.get(i).item_name.toString() + " | Price: " + String.valueOf(root2.auctions.get(i).starting_bid) + " | UUID: " + root2.auctions.get(i).auctioneer + " | Index: " + String.valueOf(i) + "                                                 ");
			//	}
			//	Logger.print(String.valueOf(i));
			}
			break;
			case Netherwart:
				if(Delay5.isDelayComplete(1500)) {
					shouldcall = true;
				}
				if(Delay5.isDelayComplete(1550)) {
					shouldcall = false;
					Delay5.reset();
				}
				if(start) {
					Thread thread = new Thread(new Runnable() {
						 public void run() {
							 if(shouldcall) {
							 try {
								ObjectMapper om = new ObjectMapper();
								String s1 = Caller("https://api.hypixel.net/status?key=e7e6339c-a9ec-4303-a51f-cb084679d539&uuid=1add6d2b-71fe-4091-9739-923372de6037");
								Root root = om.readValue(s1, Root.class);
								if(!root.session.gameType.contains("SKYBLOCK")) {
									if(Delay4.isDelayComplete(900)) {
										mc.gameSettings.keyBindAttack.pressed = false;
										mc.thePlayer.sendChatMessage("/play skyblock");
										mc.gameSettings.keyBindAttack.pressed = true;
										Delay4.reset();
									}
								}
								if(!root.session.mode.contains("dynamic") && root.session.gameType.contains("SKYBLOCK")) {
									if(Delay4.isDelayComplete(900)) {
										mc.gameSettings.keyBindAttack.pressed = false;
										mc.thePlayer.sendChatMessage("/is");
										mc.gameSettings.keyBindAttack.pressed = true;
										Delay4.reset();
									}
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
						 }
					});
					thread.start();
				      mc.gameSettings.keyBindAttack.pressed = true;
				      if(mc.thePlayer.capabilities.isFlying == true) {
				    	  mc.gameSettings.keyBindJump.pressed = true;
				    	  if(Delay5.isDelayComplete(100)) {
				    		  mc.gameSettings.keyBindJump.pressed = false;
				    		  
				    	  }
				    	  if(Delay5.isDelayComplete(150)) {
				    		  mc.gameSettings.keyBindJump.pressed = true;
				    	  }
				    	  if(Delay5.isDelayComplete(200)) {
				    		  mc.gameSettings.keyBindJump.pressed = false;
				    		  Delay5.reset();
				    	  }
				      }else {
				    	  mc.gameSettings.keyBindJump.pressed = false;
				      }
				      if(stage == 0) {
				    	  if(!Delay3.isDelayComplete(500)) {
				    		  mc.gameSettings.keyBindForward.pressed = true;
				    	  }else {
				    		  mc.gameSettings.keyBindForward.pressed = false;
				    		  Delay3.reset();
				    		  stage++;
				    	  }
				      }
				       if(stage == 1) {
				    	  if(!Delay3.isDelayComplete(37400)) {
				    		  mc.gameSettings.keyBindForward.pressed = true;
				    		  mc.gameSettings.keyBindLeft.pressed = true;
				    	  }else {
				    		  mc.gameSettings.keyBindForward.pressed = false;
				    		  mc.gameSettings.keyBindLeft.pressed = false;
				    		  Delay3.reset();
				    		  stage++;
				    	  }
				      }
				       if(stage == 2) {
					    	  if(!Delay3.isDelayComplete(500)) {
					    		  mc.gameSettings.keyBindForward.pressed = true;
					    	  }else {
					    		  mc.gameSettings.keyBindForward.pressed = false;
					    		  Delay3.reset();
					    		  stage++;
					    	  }
					      }
					      if(stage == 3) {
					    	  if(!Delay3.isDelayComplete(37400)) {
					    		mc.gameSettings.keyBindForward.pressed = true;
					    	  	mc.gameSettings.keyBindRight.pressed = true;
					    	  }else {
					    		  mc.gameSettings.keyBindForward.pressed = false;
						    	  mc.gameSettings.keyBindRight.pressed = false;
						    	  Delay3.reset();
						    	  stage = 0;
					    	  }
					      }
				       
				      }else {
				    	  Delay3.reset();
				    	  stage = 0;
				    	  mc.gameSettings.keyBindAttack.pressed = false;
				      }	
				
			break;
		}
		}
	}
	
	
	public static String Caller(String _url) throws Exception {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        
        return response.toString();
        
    }
} 