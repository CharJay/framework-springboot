package com.framework.core.utils.json;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.core.utils.date.DateFormatHelper;

import java.text.SimpleDateFormat;
import java.util.List;

public class JacksonHelper {

	private static ObjectMapper map;

	public static ObjectMapper getObjectMapper() {
		if (map == null) {
			ObjectMapper map2 = new ObjectMapper();
			// 不需要引号、单引号、不对应属性名称
			map2.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			map2.configure(Feature.ALLOW_SINGLE_QUOTES, true);
			map2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			map2.setDateFormat(new SimpleDateFormat(DateFormatHelper.FORMAT_YEAR_SECOND_CONCAT));
			map = map2;
		}
		return map;
	}

	/**
	 * 对象转json，转换出错抛运行时异常
	 * 
	 * @param value
	 * @return
	 */
	public static String obj2jsonThrowRuntime(Object value) {
		if (value instanceof String)
			return value.toString();
		ObjectMapper om = JacksonHelper.getObjectMapper();
		String str = null;
		try {
			str = om.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("obj2json序列化失败：" + e.getMessage());
		}
		return str;
	}

	/**
	 * json字符串转对象，转换出错抛运行时异常
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2objThrowRuntime(String json, Class<T> clazz) {
		ObjectMapper om = JacksonHelper.getObjectMapper();
		T obj = null;
		try {
			obj = om.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("json2obj序列化失败：" + e.getMessage());
		}
		return obj;
	}

	/**
	 * json转list
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> json2listThrowRuntime(String json, Class<T> clazz) {
		ObjectMapper om = JacksonHelper.getObjectMapper();
		List<T> list = null;
		try {
			list = om.readValue(json, getCollectionType(om, List.class, clazz));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("json2obj序列化失败：" + e.getMessage());
		}
		return list;
	}

	/**
	 * json字符串转对象，转换出错抛运行时异常
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T json2objThrowRuntime(String json, TypeReference<T> type) {
		ObjectMapper om = JacksonHelper.getObjectMapper();
		T obj = null;
		try {
			obj = om.readValue(json, type);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("json2obj序列化失败：" + e.getMessage());
		}
		return obj;
	}

	private static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass,
			Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static void main(String[] args) {
		String ss = null;
		ss = JacksonHelper.obj2jsonThrowRuntime("jfie000");
		System.out.println(ss + ":" + JacksonHelper.obj2jsonThrowRuntime(ss));
		// ss = JacksonHelper.obj2jsonThrowRuntime( new Long(1) );
		// System.out.println( ss+":"+JacksonHelper.json2objThrowRuntime( ss,
		// Long.class ) );
		// ss = JacksonHelper.obj2jsonThrowRuntime( new Integer(1) );
		// System.out.println( ss+":"+JacksonHelper.json2objThrowRuntime( ss,
		// Integer.class ) );
		// ss = JacksonHelper.obj2jsonThrowRuntime( new Date() );
		// System.out.println( ss+":"+JacksonHelper.json2objThrowRuntime( ss,
		// Date.class ) );
		// ss = JacksonHelper.obj2jsonThrowRuntime( Timestamp.valueOf(
		// "2016-12-12 11:11:11.999" ) );
		// System.out.println( ss+":"+JacksonHelper.json2objThrowRuntime( ss,
		// Timestamp.class ) );
	}
}
