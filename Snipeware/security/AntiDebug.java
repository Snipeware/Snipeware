package Snipeware.security;

import Snipeware.Client;

public class AntiDebug {
	public AntiDebug() {
		for(int i = 0; i < Client.launchArgs.size();i++) {
            String argument = Client.launchArgs.get(i);
            if(argument.contains("-Xbootclasspath") || argument.contains("-Xdebug") || argument.contains("-agentlib") || argument.contains("-javaagent:") || argument.contains("-Xrunjdwp:") || argument.contains("-verbose")){
               try{
            	   Client.INSTANCE.forceStop();
               }finally {
            	   Runtime.getRuntime().exit(0);
               }
            }else {
            continue;
            }
		}
	}

}
