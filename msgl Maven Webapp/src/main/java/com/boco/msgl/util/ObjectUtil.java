package com.boco.msgl.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

public class ObjectUtil {
	
	public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 从驼峰格式的转成下划线格�?,仅用来将bean的属性名称转成数据库对应的字段名称，其余条件可能不�?�用
	 * @param source
	 * @return
	 */
	public static String CamelCaseToUnderscore(String source){
		if(StringUtils.isEmpty(source)){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < source.length(); i++){
			char c = source.charAt(i);
			if(Character.isUpperCase(c)){
				sb.append("_").append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(CamelCaseToUnderscore("stirngIP"));
	}
}
