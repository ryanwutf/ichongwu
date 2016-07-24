package co.ichongwu.vidser.crawler.letv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import co.ichongwu.crawler.utils.HttpUtil;

public class LetvUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(LetvUtil.class);

	private static final String JS_GET_LETV_TKEY_METHOD_NAME = "getLetvTKey";
	private static final String LETV_TKEY_JS = "letv/tkey.js";

	private static final String H5_GETJSON_URL = "http://api.le.com/mms/out/video/playJsonH5?platid=3&splatid=301&tss=no&id=$id$&detect=0&dvtype=1000&accessyx=1&domain=m.le.com&tkey=$TKEY$&devid=30104c9bb2132a6f83fca81467fdcc3f";
	private static final String G3PROXY_URL_SUFFIX = "&format=1&jsonp=vjs_146902685967854&expect=3&p1=0&p2=04&termid=2&uuid=$UUID$";

	public static RealPlayInfo getPlayInfo(String id)
			throws NoSuchMethodException, ScriptException, IOException {
		String url = getG3ProxyUrl(id);
		String json = cutJsonP(HttpUtil.get(url));
		LOG.debug(json);
		RealPlayInfo info = JSON.parseObject(json, RealPlayInfo.class);
		return info;
	}

	private static String cutJsonP(String content) {
		return content.substring(
				content.indexOf("(") + 1,
				content.lastIndexOf(")")
				);
	}

	public static String getG3ProxyUrl(String id) throws NoSuchMethodException,
			ScriptException, IOException {
		PlayJsonH5 playJsonH5 = getH5PlayJson(id);
		String domain = playJsonH5.getPlayurl().getDomain().get(0);
		String mp4 = playJsonH5.getPlayurl().getDispatch().get("mp4").get(0);
		String url = domain + mp4
				+ G3PROXY_URL_SUFFIX.replace("$UUID$", ramdomUUID());
		return url;
	}

	public static PlayJsonH5 getH5PlayJson(String id)
			throws NoSuchMethodException, ScriptException, IOException {
		String content = formatPlayJsonH5Url(id);
		return JSON.parseObject(content, PlayJsonH5.class);
	}

	private static String formatPlayJsonH5Url(String id)
			throws NoSuchMethodException, ScriptException,
			FileNotFoundException, IOException {
		String tkey = getH5tKey(now());
		String url = H5_GETJSON_URL.replace("$TKEY$", tkey);
		url = url.replace("$id$", id);
		String content = HttpUtil.get(url);
		return content;
	}

	public static long getTKey(long time) {
		return gen(gen(time, 773625421 % 13) ^ 773625421, 773625421 % 17);
	}

	private static long gen(long val, long r_bits) {
		return ((val & ((long) (Math.pow(2, 32)) - 1)) >> ((r_bits % 13) % 32))
				| (val << (32 - (r_bits % 32)) & ((long) (Math.pow(2, 32)) - 1));
	}

	public static long now() {
		return (long) (System.currentTimeMillis() / 1000);
	}

	public static String getH5tKey(long time) throws NoSuchMethodException,
			ScriptException, FileNotFoundException {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("javascript");
		engine.eval(new InputStreamReader(LetvUtil.class.getClassLoader().getResourceAsStream(LETV_TKEY_JS)));

		if (engine instanceof Invocable) {
			Invocable invoker = (Invocable) engine;
			// long time = (long) (System.currentTimeMillis() / 1000);
			// System.out.println(time);
			return invoker.invokeFunction(JS_GET_LETV_TKEY_METHOD_NAME, time)
					.toString();
			// System.out.println(result);
		} else {
			return null;
		}
	}

	public static String ramdomUUID() {
		StringBuffer uuid = new StringBuffer();
		uuid.append(1);
		for (int i = 0; i < 15; i++) {
			uuid.append(String.valueOf((int) (Math.random() * 10)));
		}
		return uuid.toString();
	}

}
