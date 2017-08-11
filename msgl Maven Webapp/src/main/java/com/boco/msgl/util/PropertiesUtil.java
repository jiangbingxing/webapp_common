package com.boco.msgl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private static final String URL_PROPERTIES_FILE_PATH = "url.properties";
	
	static Properties pro = new Properties();
	static{
		init();
	}
	private static void init(){
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(URL_PROPERTIES_FILE_PATH);
		try {
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static String getConnectTestUrl(){
		return pro.getProperty("connect_test");
	}
	
	public static String getCompliantCheckUrl(){
		return pro.getProperty("compliant_check");
	}
	
	public static String getCompliantCountUrl() {
		return pro.getProperty("compliant_count");
	}
	
	public static String getProperty(String key){
		return pro.getProperty(key);
	}

	public static String getReportDownloadUrl() {
		return pro.getProperty("result_download");
	}
}
