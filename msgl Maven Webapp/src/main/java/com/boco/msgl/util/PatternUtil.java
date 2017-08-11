package com.boco.msgl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
	public static List<String> getMatch(String source, String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(source);
		List<String> result = new ArrayList<String>();
		
		while(m.find()){
			result.add(m.group());
		}
		return result;
	}
	
	public static void main(String[] args) {
	}
}
