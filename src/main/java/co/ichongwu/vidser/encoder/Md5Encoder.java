package co.ichongwu.vidser.encoder;

public class Md5Encoder extends DigestEncoder {

	private static Md5Encoder instance = null;

	private Md5Encoder() {
		super("MD5");
	}

	public synchronized static Md5Encoder getInstance() {
		if (instance == null) {
			instance = new Md5Encoder();
		}
		return instance;
	}

//	public static void main(String a[]) {
//		System.out.println(Md5Encoder.getInstance().encode("2222"));
//	}
}
