package co.ichongwu.vidser.encoder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * #func 加密父类<br>
 * #desc
 * 
 * @author luochao
 * @version 1.0.0
 * 
 */
public class DigestEncoder {

	private String algorithm = "MD5";

	public DigestEncoder(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * 
	 * #func 计算字符串的md5码<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public String encode(String str) {
		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = getMessageDigest();
		byte[] digest;

		try {
			digest = messageDigest.digest(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		}

		return new String(Hex.encodeHex(digest));
	}

	/**
	 * 
	 * #func 计算文件内容md5码<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public String encode(File file) {
		if (file == null || !file.exists()) {
			return null;
		}

		try {
			return encode(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * #func 计算输入流md5码<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public String encode(InputStream in) {
		MessageDigest messageDigest = getMessageDigest();

		try {
			BufferedInputStream bi = new BufferedInputStream(in, 4096);
			byte[] byteArray = new byte[4096];
			int length = 0;
			while ((length = bi.read(byteArray)) > 0) {
				messageDigest.update(byteArray, 0, length);
			}
			bi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String(Hex.encodeHex(messageDigest.digest()));
	}

	/**
	 * Get a MessageDigest instance for the given algorithm. Throws an
	 * IllegalArgumentException if <i>algorithm</i> is unknown
	 * 
	 * @return MessageDigest instance
	 * @throws IllegalArgumentException
	 *             if NoSuchAlgorithmException is thrown
	 */
	protected final MessageDigest getMessageDigest() throws IllegalArgumentException {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
		}
	}

	public String getAlgorithm() {
		return algorithm;
	}
}
