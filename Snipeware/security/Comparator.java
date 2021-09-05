package Snipeware.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Snipeware.Client;
import Snipeware.INetHandlerNiggerToServer;

public class Comparator {
	public static boolean isAuthed = false;
	
	public static boolean compare(String input, String verify) {
		if(verify == INetHandlerNiggerToServer.getID()) {
			isAuthed = false;
			return false;
		}
		if(input == null) {
			return false;
		}
		if(!Client.nomeaningbool) {
			return false;
		}
		try {
			input = input.substring(8);
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(input);
			final byte b = (byte)0b11001000;
			final int i = b & 0xFF;
			while(m.find()) {
				if(Integer.parseInt(m.group())==i) {
					if(!Client.nomeaningbool) {
						return false;
					}else {
						Client.nigger = false;
						isAuthed = true;
						return true;
					}
				}
			}
		}catch(Exception e) {
			return false;
		}
		return false;
	}
	
	public static boolean isAuthed() {
		return isAuthed;
	}

}
