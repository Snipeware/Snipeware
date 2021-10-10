package Snipeware.security;

public class Util {
	public static String stringify(char[] input) {
		StringFunction value = (str) -> String.valueOf(input);
		return format("femboyfardonmydickuwu",value);
	}
	
	public static String format(String str, StringFunction format) {
		String result = format.run(str);
		return result;
	}
}

interface StringFunction {
	String run(String str);
}
