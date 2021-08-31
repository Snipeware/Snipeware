package Snipeware;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.thealtening.AltService;

import Snipeware.api.annotations.Handler;
import Snipeware.api.bus.Bus;
import Snipeware.api.bus.BusImpl;
import Snipeware.command.Command;
import Snipeware.events.Event;
import Snipeware.events.player.EventKeyPress;
import Snipeware.events.player.EventSendMessage;
import Snipeware.gui.alt.system.AccountManager;
import Snipeware.hwid.NoStackTraceThrowable;
import Snipeware.management.CommandManager;
import Snipeware.management.ConfigManager;
import Snipeware.management.FontManager;
import Snipeware.management.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C21CandidateSalvationPacket;


public enum Client {
	
	INSTANCE;

	public static DiscordRP discordRP = new DiscordRP();
	
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
    public static final String HWID_URL = "https://pastebin.com/raw/QYeNr1g3";
    //public static final String KEY      = new SHA256().hash("nig");
    public static boolean nigger = true;
    public static String DID = null;
    
    public static final Client getInstance(){
		return INSTANCE;
	}
    
    public static void copyToClipboard(String s) {
        StringSelection selection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
	
	public void start() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		
		
		
	
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
		
	
		/*
		HitlerYouthAntiJudaismProcessManipulationAtBirthPropagandaMachineAntiJudenschwein();
	
		if(nigger) {
			 copyToClipboard(INetHandlerNiggerToServer.getID());
			stop();
		}
		 */
	}
	
   /* public void judenschweincheck() { // Seconf form of HWID protection, disabled as we already have one - Napoleon ZoomberParts
        hwidList = NetworkUtil.getHWIDList();
        if (!hwidList.contains(HWIDUtil.getEncryptedHWID(KEY))) {
            FrameUtil.Display();
            throw new NoStackTraceThrowable("Verify HWID Failed!"); // HWID Failure is haram in many ways than one
        }
    } 
	*/
	public void HitlerYouthAntiJudaismProcessManipulationAtBirthPropagandaMachineAntiJudenschwein() {
		if(INetHandlerNiggerToServer.whitelisted()) {
			System.out.println("Welcome, your HWID has been Authenticated. NapoliHWID protection, Leaking jar = I will find you jew.");
		
			//nigger = false;
		}else{
			System.out.println("JUDENSCHWEIN DETECTED, ENGAGE HYDRA LOCKING PROTOCOLS." + INetHandlerNiggerToServer.getID());
	        C21CandidateSalvationPacket.Display(); // wtf men how hydra get into src - Napoleon ZoomberParts
	    
		}
		
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public void stop() {
	discordRP.shutdown();
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
		System.exit(69420); 
		// Do you has the drip my nigga Koljan? - Napoleon ZoomberParts
	}
	
	//fuck you napoleon removed that shit :kek:
	
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
	
	public DiscordRP getDiscordRP(){
		return discordRP;
	}
	
	
 
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
