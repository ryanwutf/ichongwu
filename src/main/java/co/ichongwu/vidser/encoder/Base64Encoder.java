package co.ichongwu.vidser.encoder;


import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Encoder {

	public static Base64Encoder base64Encoder;
	public static final String CHARSET = "UTF-8";

	private Base64Encoder() {

	}

	public static synchronized Base64Encoder getInstance() {
		if (base64Encoder == null) {
			base64Encoder = new Base64Encoder();
		}
		return base64Encoder;
	}

	/**
	 * 
	 * #func 返回字符串的base64码<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public String encode(String str) {
		if (str == null) {
			return null;
		}

		String code = null;
		try {
			code = Base64.encodeBase64URLSafeString(str.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return code;
	}

	/**
	 * 
	 * #func 返回字节码的base64码<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public String encode(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		return Base64.encodeBase64URLSafeString(bytes);
	}

	/**
	 * 
	 * #func 还原base64编码串<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public String decode(String str) {
		if (str == null) {
			return null;
		}

		byte[] bytes = Base64.decodeBase64(str);

		str = null;
		try {
			str = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 
	 * #func 还原base64编码串<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public String decode(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		bytes = Base64.decodeBase64(bytes);

		String str = null;
		try {
			str = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 
	 * #func 还原base64编码串<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public byte[] decodeBytes(String str) {
		if (str == null) {
			return null;
		}

		return Base64.decodeBase64(str);
	}

//	public static void main(String[] agrs) {
//		String code = Base64Encoder.getInstance().encode("1234567890ABCDEFG");
//		String str = Base64Encoder.getInstance().decode(code);
//		System.out.println(code);
//		System.out.println(str);
//	}

}
