package Snipeware.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Snipeware.Client;

public class Comparator {
	public static boolean isAuthed = false;
	
	public static boolean compare(String input) {
		if(input == null) {
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
				Client.nigger = false;
				isAuthed = true;
				return true;
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
