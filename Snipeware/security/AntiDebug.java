package Snipeware.security;

import java.io.IOException;

import Snipeware.Client;

public class AntiDebug {
	public AntiDebug() {
		for(int i = 0; i < Client.launchArgs.size();i++) {
            String argument = Client.launchArgs.get(i);
            if(argument.contains("-Xbootclasspath") || argument.contains("-Xdebug")/* || argument.contains("-agentlib") || argument.contains("-javaagent:") */|| argument.contains("-Xrunjdwp:") || argument.contains("-verbose") || debuggersAreRunning()){
               try{
            	   System.out.println(Client.launchArgs.get(i));
            	   Client.INSTANCE.forceStop();
               }finally {
            	   Runtime.getRuntime().exit(0);
               }
            }else {
            	continue;
            }
		}
		
	}
	private static boolean debuggersAreRunning() {
        String line;
        try {
			while ((line = Client.halal.readLine()) != null)
			{
			    if (line.toLowerCase().contains("wireshark"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("fiddler"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("ollydbg"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("tcpview"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("autoruns"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("autorunsc"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("filemon"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("procmon"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("regmon"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("procexp"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("idaq"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("idaq64"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("immunitydebugger"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("dumpcap"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("hookexplorer"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("importrec"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("petools"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("lordpe"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("dumpcap"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("sysinspector"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("proc_analyzer"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("sysAnalyzer"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("sniff_hit"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("windbg"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("joeboxcontrol"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("joeboxserver"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("fiddler"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("teamViewer_service"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("teamviewer"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("tv_w32"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vboxservice"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vboxtray"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("xenservice"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vmtoolsd"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vmwaretray"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vmwareuser"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vgauthservice"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vmacthlp"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vmsrvc"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("vmusrvc"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("prl_cc"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("prl_tools"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("qemu-ga"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("program manager"))
			    {
			        return true;
			    }
//            if (line.toLowerCase().contains("sbiesvc"))
//            {
//                return true;
//            }
			    if (line.toLowerCase().contains("vmdragdetectwndclass"))
			    {
			        return true;
			    }
//            if (line.toLowerCase().contains("sandboxie"))
//            {
//                return true;
//            }
			    if (line.toLowerCase().contains("windump"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("tshark"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("networkminer"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("capsa"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("solarwinds"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("glasswire"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("http sniffer"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("httpsniffer"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("http debugger"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("httpdebugger"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("http debug"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("httpdebug"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("httpsniff"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("httpnetworksniffer"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("kismac"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("cain and able"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("cainandable"))
			    {
			        return true;
			    }
			    if (line.toLowerCase().contains("etherape"))
			    {
			        return true;
			    }
			    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return false;
    }

}
