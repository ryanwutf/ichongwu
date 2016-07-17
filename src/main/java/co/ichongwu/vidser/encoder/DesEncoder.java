package co.ichongwu.vidser.encoder;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesEncoder {

	/**
	 * 密钥，长度必须是8的倍数
	 */
	public static final String KEY = "aDm@sINa";
	public static final String CHARSET = "UTF-8";
	public static DesEncoder desEncoder;

	private SecretKey securekey;

	private DesEncoder() {
		try {
			byte[] keyBytes = KEY.getBytes(CHARSET);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			securekey = keyFactory.generateSecret(new DESKeySpec(keyBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DesEncoder getInstance() {
		if (desEncoder == null) {
			desEncoder = new DesEncoder();
		}
		return desEncoder;
	}

	/**
	 * 
	 * #func 使用DES算法加密字符串<br>
	 * #desc 使用16进制表示结果
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public byte[] encode(String str) {
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
			return cipher.doFinal(str.getBytes(CHARSET));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * #func 使用DES算法加密字符串<br>
	 * #desc 使用16进制表示结果
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public byte[] encode(byte[] bytes) {
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 
	 * #func 使用DES解密字符串<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public byte[] decode(String str) {
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
			return cipher.doFinal(str.getBytes(CHARSET));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * #func 使用DES解密字节码<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public byte[] decode(byte[] bytes) {
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * #func 使用DES解密字节码,返回字符串<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public String decodeToString(byte[] bytes) {
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
			byte[] b = cipher.doFinal(bytes);
			
			String str = new String(b, "UTF-8");
			return str;
		} catch (Exception e) {
			return null;
		}
	}
	
//	public static void main(String[] args){
//		String json = "fdafdasfsa}";
//		byte[] b = DesEncoder.getInstance().encode(json);
//		String jsonStr = Base64Encoder.getInstance().encode(b);
//		System.out.println(jsonStr);
//		
//		b = Base64Encoder.getInstance().decodeBytes(jsonStr);
//		json = DesEncoder.getInstance().decodeToString(b);
//		System.out.println(json);
//	}
}
