package felix;

import java.awt.Color;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;

import com.google.common.eventbus.EventBus;
import com.thealtening.AltService;

import felix.api.annotations.Handler;
import felix.api.bus.Bus;
import felix.api.bus.BusImpl;
import felix.command.Command;
import felix.events.Event;
import felix.events.packet.EventPacketSend;
import felix.events.player.EventKeyPress;
import felix.events.player.EventMotionUpdate;
import felix.events.player.EventSendMessage;
import felix.gui.alt.system.AccountManager;
import felix.hwid.FrameUtil;
import felix.hwid.HWIDUtil;
import felix.hwid.NetworkUtil;
import felix.hwid.NoStackTraceThrowable;
import felix.management.CommandManager;
import felix.management.ConfigManager;
import felix.management.FontManager;
import felix.management.ModuleManager;
import felix.module.Module;
import felix.security.JUDENSCHWEIN;
import felix.util.other.Logger;
import felix.util.other.PlayerUtil;
import felix.util.other.TimeHelper;
import felix.value.Value;
import felix.value.impl.BooleanValue;
import felix.value.impl.EnumValue;
import felix.value.impl.NumberValue;
import net.halozy.Protection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C20PacketAntiJudaism;
import net.minecraft.network.play.client.C21CandidateSalvationPacket;
import net.minecraft.util.Session;


public enum Client {
	
	INSTANCE;

	//private DiscordRP discordRP = new DiscordRP();
	
	private ModuleManager moduleManager;
	
	private Bus<Event> eventapi;

	public String user = "";
	

	private FontManager fontManager;
	
    private AccountManager accountManager;
    
    private AltService altService = new AltService();
    
    private File directory;
    
    private File configDirectory;
    
    private CommandManager commandManager;
    
    private ConfigManager configManager;
    
    private File dataFile;
    
    public String build = "1.03";
    public static List<String> hwidList = new ArrayList<>();
    public static final String HWID_URL = "https://pastebin.com/raw/QYeNr1g3";
    //public static final String KEY      = new SHA256().hash("nig");
    public static boolean nigger = true;
    
    public static final Client getInstance(){
		return INSTANCE;
	}
    
	
	public void start() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		//System.out.println(getHWID());
		//HWIDCheck("d1608c729b094a2be2a6893a137fc8f6");
		
		
		
		//Minecraft.getMinecraft().session = new Session("KoljanLOL8", "", "", "mojang");
		
	//	discordRP.start();
	
		directory = new File(Minecraft.getMinecraft().mcDataDir, "Snipeware");
		configDirectory = new File(directory, "configs");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (!configDirectory.exists()) {
        	configDirectory.mkdir();
        }
        dataFile = new File(directory, "modules.txt");
		eventapi = new BusImpl<Event>();
		moduleManager = new ModuleManager();
		configManager = new ConfigManager();
		commandManager = new CommandManager();
	    
		fontManager = new FontManager();
		accountManager = new AccountManager(directory);
		configManager.loadConfigs();
		moduleManager.loadModules(dataFile);
		
		eventapi.register(this);
		
		HitlerYouthAntiJudaismProcessManipulationAtBirthPropagandaMachineAntiJudenschwein();
		
		if(nigger) {
			System.out.print("Kys Nigger");
			throw new NoStackTraceThrowable("");
		}
		//judenschweincheck();
	}
	
   /* public void judenschweincheck() { // Seconf form of HWID protection, disabled as we already have one - Napoleon ZoomberParts
        hwidList = NetworkUtil.getHWIDList();
        if (!hwidList.contains(HWIDUtil.getEncryptedHWID(KEY))) {
            FrameUtil.Display();
            throw new NoStackTraceThrowable("Verify HWID Failed!"); // HWID Failure is haram in many ways than one
        }
    } */
	
	public void HitlerYouthAntiJudaismProcessManipulationAtBirthPropagandaMachineAntiJudenschwein() {
		if(INetHandlerNiggerToServer.whitelisted()) {
			System.out.println("Welcome, your HWID has been Authenticated. NapoliHWID protection, Leaking jar = I will find you jew.");
			nigger = false;
		}
		if(!INetHandlerNiggerToServer.whitelisted()) {
			System.out.println("JUDENSCHWEIN DETECTED, ENGAGE HYDRA LOCKING PROTOCOLS." + INetHandlerNiggerToServer.getID());
    		try {
				JUDENSCHWEIN.nig();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        C21CandidateSalvationPacket.Display(); // wtf men how hydra get into src - Napoleon ZoomberParts
		}
		
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public void stop() {
		//discordRP.shutdown();
        accountManager.save();
        if (!dataFile.exists()) {
        	try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        moduleManager.saveModules(dataFile);
		eventapi.unregister(this);
		if(nigger) {
			System.out.println("🎵 And all they can ask is, SUBMIT YOUR FUCKING HWID FAGGOT 🎵");
		} // Do you has the drip my nigga Koljan? - Napoleon ZoomberParts
	}
	
	public void HWIDCheck(String hwid) { // LOL
		if(hwid == "d1608c729b094a2be2a6893a137fc8f6") { // LOLOLOLOLOL
		
		}else if(hwid == "b4798eb2545104a413e4929b0cc9a0b2") { // Stop pulling the funny Koljan
			
		}else if(hwid == "o9bfe7329c81b41853716a6a96f31788") { // This is very haram
			
		}else if(hwid == "o9bfe7329c81b41853716a6a96f31788") { 
			
		}else if(hwid == "c60b53b9432f9b086a0a199f2110c058") {
			
		}else if(hwid == "e8d712e0f8f4df4e257afba26cd0c9f7") {
			
		}else if(hwid == "e61c668ac4b31811b3a3a1070504deaf") {
			
		}else if(hwid == "8e1bc69f0c35c9a7d09c723bb0e522d7") {
			
		}else if(hwid == "8376e079d34bb7a71dc5769661c5bae5") {
			
		}else if(hwid == "02cf6b297c55e14c2f6f9a546578f13c") {
			
		}else if(hwid == "c91b92760c9f73e59511801f9f71b825") {
			
		}else if(hwid == "9eb5d85d77e0ea33b925c23ab89d7384") {
			
		}else if(hwid == "4b5a53451d8e6d2e30821161b4be7c91") {
			
		}else if(hwid == "51432729b93a32b32ad39b291db4b536") {
			
		}else if(hwid == "3be1747b9da0d7eb7e9327d5374e6a7f") {
			
		}else if(hwid == "547a1d48553f50b76c87d95f89624b8b") {
			
		}else if(hwid == "1050fee3dd4dcb41414e9e1303bfddf9") {
			
		}else if(hwid == "76b76955e2c42cbb0800daae22c9e9e9") {
			
		}else if(hwid == "4da54948e01c30e21005949cf23fc539") {
			
		}else if(hwid == "547a1d48553f50b76c87d95f89624b8b") {
			
		}
			
		
		
		else {
		     JOptionPane.showMessageDialog(null,"Invaild HWID message Koljan#6767!","Invaild HWID", JOptionPane.CANCEL_OPTION);
			Minecraft.getMinecraft().shutdown();
		}
		
		
	}
	
    public static String getHWID() {
        try{
            String toEncrypt =  System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            StringBuffer hexString = new StringBuffer();
            
            byte byteData[] = md.digest();
            
            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace(); 
            return "Error";
        }
    }
	
	@Handler
	public void onKeyPress(final EventKeyPress event) {
		moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
	}
	
	@Handler
	public void onSendMessage(final EventSendMessage eventChat) {
		for (final Command command : commandManager.getComands()) {
			String chatMessage = eventChat.getMessage();
			String formattedMessage = chatMessage.replace(".", "");
			String[] regexFormattedMessage = formattedMessage.split(" ");
			if (regexFormattedMessage[0].equalsIgnoreCase(command.getCommandName())) {
				ArrayList<String> list = new ArrayList<>(Arrays.asList(regexFormattedMessage));
				list.remove(command.getCommandName());
				regexFormattedMessage = list.toArray(new String[0]);
				command.executeCommand(regexFormattedMessage);
			}
		}
		if (eventChat.getMessage().startsWith(".")) {
			eventChat.setCancelled(true);
		}
	}
	
	public ModuleManager getModuleManager() {
		return moduleManager;
	}
	
	public Bus getEventManager() {
		return eventapi;
	}
	/*
	public DiscordRP getDiscordRP(){
		return discordRP;
	}
	
	*/
 
	public FontManager getFontManager() {
		return fontManager;
	}

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void switchToMojang() {
        try {
            altService.switchService(AltService.EnumAltService.MOJANG);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to modank altservice");
        }
    }

    public void switchToTheAltening() {
        try {
            altService.switchService(AltService.EnumAltService.THEALTENING);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to altening altservice");
        }
    }

	public File getConfigDirectory() {
		return configDirectory;
	}
	
	public File getDirectory() {
		return directory;
	}
}
