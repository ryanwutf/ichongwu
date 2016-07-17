package co.ichongwu.vidser.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.ichongwu.vidser.encoder.Base64Encoder;
import co.ichongwu.vidser.encoder.DesEncoder;

/**
 * 
 * @author hedan
 * 
 */
public class UserTokenUtil {

	public static final boolean DES = true;

	public static final String CHARSET = "UTF-8";

	private static Pattern p = Pattern.compile("^\\[[a-zA-Z0-9]+\\]\\[[0-9]+\\]$");

	/**
	 * 
	 * #func 获得指定用户id的token<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static String encryptToken(Long customerid) {
		String timestamp = DateUtil.format(new Date(), "ddHHmmssSSS");
		String token = "[" + customerid.toString() + "][" + timestamp + "]";
		return encrypt(token);
	}

	/**
	 * 
	 * #func 获得指定用户name的token<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static String encryptToken(String name) {
		String timestamp = DateUtil.format(new Date(), "ddHHmmssSSS");
		String token = "[" + name + "][" + timestamp + "]";
		return encrypt(token);
	}
	
	/**
	 * 
	 * #func 根据ucid和登录时间拼接token，用于登录后的用户绑定session使用<br>
	 * #desc 使用的uc的login_time,表示的是上次登录时间，第一次登陆时，为0
	 * 
	 * @author zhangzhibo
	 * @version 4.0
	 */
	public static String genToken(String ucid, Long loginTime) {
		String token = "[STAT][" + ucid + "][" + String.valueOf(loginTime) + "]";
		return token;
	}

	/**
	 * 
	 * #func 判断token是否是一个有效的token<br>
	 * #desc 用户篡改token将返回false
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static boolean isValidToken(String token) {
		token = decrypt(token);
		if (token == null || token.length() == 0) {
			return false;
		}

		Matcher m = p.matcher(token);

		if (!m.find()) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * #func 使用DES算法加密字符串<br>
	 * #desc 使用16进制表示结果
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static String encrypt(String str) {
		byte[] bytes = getBytes(str);
		if (DES && bytes != null) {
			bytes = DesEncoder.getInstance().encode(bytes);
		}
		return Base64Encoder.getInstance().encode(bytes);
	}

	/**
	 * 
	 * #func 使用DES解密字符串<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static String decrypt(String str) {
		byte[] bytes = Base64Encoder.getInstance().decodeBytes(str);
		if (DES && bytes != null) {
			bytes = DesEncoder.getInstance().decode(bytes);
		}

		return getString(bytes);
	}

	private static byte[] getBytes(String str) {
		if (str == null) {
			return null;
		}

		byte[] bytes = null;
		try {
			bytes = str.getBytes(CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	private static String getString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		String str = null;
		try {
			str = new String(bytes, CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

//	public static void main(String[] args) {
//		System.out.println(encryptToken("2sddff1"));
//		System.out.println(decrypt("vwpBs8_Q4gvfkZcCNGH54kbz0H9Ham-Y"));
//		System.out.println(isValidToken("vwpBs8_Q4gvfkZcCNGH54kbz0H9Ham-Y"));
//	}
}
