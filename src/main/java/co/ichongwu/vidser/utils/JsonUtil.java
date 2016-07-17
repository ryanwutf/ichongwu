package co.ichongwu.vidser.utils;

import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @ClassName: JsonUtil
 * @Description: json 工具类
 * @author qupeng@staff.sina.com.cn
 * @2013-12-24 下午3:48:32
 */
public class JsonUtil {
	


	/**
	 * json转换map
	 * 
	 * @Title: parseJSON2Map
	 * @Description:
	 * @param jsonStr
	 * @return Map<String,Object>
	 * @see JsonUtil.java
	 */
	public static Map<Object, String> parseJSON2Map(String jsonStr) {
		Map<Object, String> map = new HashMap<Object, String>();
		JSONObject json;
		try {
			json = new JSONObject(jsonStr);
			Iterator<String> iterator = json.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = json.getString(key);
				map.put(key, value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static String getStringValue(String json, String key) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			return jsonObject.getString(key);
		} catch (JSONException e) {
			return null;
		}
	}

	public static int getIntValue(String json, String key) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			return jsonObject.getInt(key);
		} catch (JSONException e) {
			return 0;
		}
	}

	public static long getLongValue(String json, String key) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			return jsonObject.getLong(key);
		} catch (JSONException e) {
			return 0;
		}
	}

	public static boolean getBooleanValue(String json, String key) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			return jsonObject.getBoolean(key);
		} catch (JSONException e) {
			return false;
		}
	}

	public static JSONObject getJSONObjectValue(String json, String key) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			return jsonObject.getJSONObject(key);
		} catch (JSONException e) {
			return null;
		}
	}

	public static JSONArray getJSONArrayValue(String json, String key) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			return jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			return null;
		}
	}

	public static String[] getStringArrayValue(String json, String key) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			JSONArray jSONArray = jsonObject.getJSONArray(key);
			String[] s = new String[jSONArray.length()];
			for (int i = 0; i < jSONArray.length(); i++) {
				s[i] = jSONArray.getString(i);
			}
			return s;
		} catch (JSONException e) {
			return null;
		}
	}

	public static Integer[] getIntegerArrayValue(String json, String key) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			JSONArray jSONArray = jsonObject.getJSONArray(key);
			Integer[] s = new Integer[jSONArray.length()];
			for (int i = 0; i < jSONArray.length(); i++) {
				s[i] = jSONArray.getInt(i);
			}
			return s;
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * @Title: validJson
	 * @Description: 判断是否为json格式
	 * @param json
	 * @return boolean
	 * @see JsonUtil.java
	 */
	public static boolean validJson(String json) {
		try {
			new JSONObject(json);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}

	private static final SerializerFeature[] DEFAULT_SERIALIZER_FEATURES = new SerializerFeature[] {
			SerializerFeature.WriteMapNullValue,//
			SerializerFeature.DisableCircularReferenceDetect,//
			SerializerFeature.WriteDateUseDateFormat };

	public static String toJsonString(Object o) {
		return JSON.toJSONString(o, DEFAULT_SERIALIZER_FEATURES);
	}

	public static void writeJsonString(Object o, Writer writer) {
		JSON.writeJSONStringTo(o, writer, DEFAULT_SERIALIZER_FEATURES);
	}

}
