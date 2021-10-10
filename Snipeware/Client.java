package Snipeware;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URI;
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
import Snipeware.security.Util;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C21CandidateSalvationPacket;
import net.minecraft.server.integrated.IntegratedServer;


public enum Client {

	INSTANCE;
	
	public DiscordRP discordRP = new DiscordRP();

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

    public static String build = "1.03";
    public static List<String> hwidList = new ArrayList<>();
    public static final String HWID_URL = "https://pastebin.com/raw/QYeNr1g3";
    public static boolean nigger = true;
    public static String DID = null;
    public static String verificationstring = setup();
    public static boolean nomeaningbool = false;
    private static String str;
    public static final List<String> launchArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
    public static char[] iloveyou = {'h','t','t','p','s',':','/','/','w','a','w','a','w','a','w','a','w','a','w','s','n','i','p','e',IntegratedServer.wtf[0],IntegratedServer.wtf[1],IntegratedServer.wtf[2],IntegratedServer.wtf[3],IntegratedServer.wtf[4],IntegratedServer.wtf[5],IntegratedServer.wtf[6],IntegratedServer.wtf[7],IntegratedServer.wtf[8],IntegratedServer.wtf[9],IntegratedServer.wtf[10],IntegratedServer.wtf[11],IntegratedServer.wtf[12],IntegratedServer.wtf[13],IntegratedServer.wtf[14],IntegratedServer.wtf[15],IntegratedServer.wtf[16],IntegratedServer.wtf[17],IntegratedServer.wtf[18],IntegratedServer.wtf[19],IntegratedServer.wtf[20],IntegratedServer.wtf[21]};
	public static URI wtf;
    
    public static final Client getInstance(){
		return INSTANCE;
	}
    static {
    	try {
    		wtf = new URI(Util.stringify(Client.iloveyou));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public static void copyToClipboard(String s) {
        StringSelection selection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
    
    private static String setup() {
    	str=INetHandlerNiggerToServer.getID();
    	return str;
    }

	public void start() {
		try {
			System.out.println("part one");
    		wtf = new URI(Util.stringify(Client.iloveyou));
    		System.out.println("part two");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		System.out.println("part three");
		wtf pogy = () -> new Snipeware.security.AntiDebug();
		pogy.omg();
		System.out.println("part four");
		startAuth();
		System.out.println("part five");
		prepare();
		if(nigger) {
			copyToClipboard(INetHandlerNiggerToServer.getID());
			stop();
		}
	}
	
	public void prepare() {
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
		nigger = false;
		if(nigger) {
			 copyToClipboard(INetHandlerNiggerToServer.getID());
		//	stop();
		}
		
	}

   /* public void judenschweincheck() { // Seconf form of HWID protection, disabled as we already have one - Napoleon ZoomberParts
        hwidList = NetworkUtil.getHWIDList();
        if (!hwidList.contains(HWIDUtil.getEncryptedHWID(KEY))) {
            FrameUtil.Display();
            throw new NoStackTraceThrowable("Verify HWID Failed!"); // HWID Failure is haram in many ways than one
        }
    }
	*/

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public void stop() {
		try {
			Minecraft.discordRP.shutdown();
	        accountManager.save();
	        if (!dataFile.exists()) {
	        	dataFile.createNewFile();
	        }
	        moduleManager.saveModules(dataFile);
			eventapi.unregister(this);
			Minecraft.stopIntegratedServer();
		}catch(Exception e){
			System.out.println("Error while saving config");
			
		}
		
		// Do you has the drip my nigga Koljan? - Napoleon ZoomberParts
	}
	
	public void forceStop() {
		stop();
		System.out.println("A horny femboy (owo) with a massive cock is approaching your current geographical location, you better run while you still can.");
		System.exit(0);
	}

	//fuck you napoleon removed that shit :kek:

	@Handler
	public void onKeyPress(final EventKeyPress event) {
		moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
	}
	
	private void startAuth() {
    	new Thread(()->{
			if(INetHandlerNiggerToServer.whitelisted(Client.verificationstring)) {
				System.out.println("Welcome, your HWID has been Authenticated. NapoliHWID protection, Leaking jar = I will find you jew.");
				Client.nomeaningbool = false;

				//nigger = false;
			}else{
				System.out.println("JUDENSCHWEIN DETECTED, ENGAGE HYDRA LOCKING PROTOCOLS." + INetHandlerNiggerToServer.getID());
		        C21CandidateSalvationPacket.Display(); // wtf men how hydra get into src - Napoleon ZoomberParts

			}
    	}, "Main").start();
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

interface wtf {
	void omg();
}