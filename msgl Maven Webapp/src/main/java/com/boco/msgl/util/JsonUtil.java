package com.boco.msgl.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.boco.msgl.base.BaseObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


public class JsonUtil {
	private static final String DEFAULT_DATASOURCE_NAME = "data";
	
	public static JsonConfig jsonConfig = new JsonConfig();
	
	static {
		jsonConfig.registerJsonValueProcessor(Object.class,
				new JsonValueProcessor() {
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						if(arg0 == null)
							return "";
						return arg0.toString();
					}

					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if(arg1 == null)
							return "";
						return arg1.toString();
					}
				});
		
		//date类型处理方法
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonValueProcessor() {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");

					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return simpleDateFormat.format((Date) arg0);
					}

					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if(arg1 != null){
							return simpleDateFormat.format((Date) arg1);
						} else {
							return "";
						}
						
					}
				});

		//double类型处理方法

		jsonConfig.registerJsonValueProcessor(Double.class,
				new JsonValueProcessor() {
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return String.format("%.2f", arg0);
					}

					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						return String.format("%.2f", arg1);
					}
				});

		//int类型处理方法
		jsonConfig.registerJsonValueProcessor(Integer.class,
				new JsonValueProcessor() {
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return arg0;
					}

					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if(arg1 != null){
							return arg1;
						} else {
							return "";
						}
					}
				});

	
	}
	public static String list2Json(List<?> list){
		JSONArray array = JSONArray.fromObject(list, jsonConfig);;
		return array.toString();
	}
	
	public static String obj2json(Object obj){
		JSONObject object = JSONObject.fromObject(obj, jsonConfig);
		return object.toString();
	}
	
	/**
	 * 将查询得到的数据转成前端dataTable能够识别的JSON
	 * @param keyName
	 * @param list
	 * @return
	 */
	public static String formatResult(String keyName, List<?> list){
		JSONObject obj = new JSONObject();
		obj.element(keyName, list, jsonConfig);
		return obj.toString();
	}
	
	/**
	 * 将查询得到的数据转成前端dataTable能够识别的JSON
	 * @param list
	 * @return
	 */
	public static String formatResult(List<?> list){
		return formatResult(DEFAULT_DATASOURCE_NAME, list);
	}
	
	public static JSONObject formatResultToJSON(List<?> list){
		return JSONObject.fromObject(formatResult(DEFAULT_DATASOURCE_NAME, list));
	}
	
	public static JSONObject formatResultToJSON(List<?> list, Map<String, ?> map){
		if(map == null || map.size() == 0){
			return formatResultToJSON(list);
		}
		JSONObject obj = new JSONObject();
		obj.element(DEFAULT_DATASOURCE_NAME, list, jsonConfig);
		for(Entry<String, ?> entry : map.entrySet()){
			obj.element(entry.getKey(), entry.getValue(), jsonConfig);
		}
		return obj;
	}
	
	public static BaseObject paraseStr(String source){
		return (BaseObject)JSONObject.toBean(JSONObject.fromObject(source));
	}
	
}