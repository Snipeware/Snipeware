package Snipeware.security;

import Snipeware.Client;

public class AntiDebug {
	public AntiDebug() {
		for (String s : Client.launchArgs) {
			if (s.startsWith("-Xbootclasspath") || s.startsWith("-Xdebug") || s.startsWith("-agentlib")
			|| s.startsWith("-javaagent:") || s.startsWith("-Xrunjdwp:") || s.startsWith("-verbose")) {
			System.exit(0);
		}
		for(int i = 0; i < Client.launchArgs.size();i++) {
            String argument = Client.launchArgs.get(i);
            if(argument.contains("-Xdebug") || argument.contains("-agentlib")){
                System.exit(0);
            }else {
             continue;
            }
        }
		}
	}

}
