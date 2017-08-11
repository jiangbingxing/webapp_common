package com.boco.msgl.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.boco.msgl.base.BaseObject;




public class ReflectionUtil {
	public static final Integer ARRAY_FRIST_INDEX = 0;

	public static final String PATTERN_MAPPER = "Dao";

	public static Class<?> getArgumentType(Class<?> cls) {

		Type[] types = ((ParameterizedType) cls.getGenericSuperclass()).getActualTypeArguments();

		return (Class<?>) types[ARRAY_FRIST_INDEX];
	}

	public static Class<?> getMatcherMapper(Class<?> cls) {
		Class<?>[] classes = cls.getInterfaces();
		Pattern pattern = Pattern.compile(getArgumentType(cls).getSimpleName() + PATTERN_MAPPER);
		for (Class<?> c : classes) {
			Matcher matcher = pattern.matcher(c.getSimpleName());
			if (matcher.find()) {
				return c;
			}
		}
		return null;
	}
	
	public static <T extends BaseObject>void setValue(T t, String fieldName, String value) throws Exception{
		Class<? extends BaseObject> clazz = t.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		Class<?> type = field.getType();
		String typeName = type.getSimpleName();
		if("Double".equalsIgnoreCase(typeName)){
			try{
				field.set(t, Double.valueOf(value));
			} catch(Exception e) {
				field.set(t, 0.00);
			}
		} else if("Integer".equalsIgnoreCase(typeName)){
			try{
				field.set(t, Integer.valueOf(value));
			} catch(Exception e) {
				field.set(t, 0);
			}
		}  else {
			field.set(t, value); 
		}
	}
}
