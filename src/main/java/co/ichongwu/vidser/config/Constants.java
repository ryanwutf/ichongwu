package co.ichongwu.vidser.config;

public class Constants {
	
	/**
     * 分页中每页的默认大小
     */
    public static final int DEFAULT_PAGE_SIZE = 15;
    public static final String DEFAULT_PAGE_SIZE_STRING = "15";
    
    
    public static final String COOKIE_USER_KEY = "Statistics_stat";
    public static final String SSO_SESSION_KEY_PREFIX = "sso_session_key_";
    public static final String ENGINE_SSOID_SUFFIX = "_PINPAI-CPC";
    
	
	/**
     * MIME类型
     */
    public static class MIME {
        public static final String MINE_JSON = "application/json;charset=utf-8";
    }
    
    public static class WEB_TYPE {
    	public static final Long PC = 0L;
    	public static final Long WAP = 1L;
    	public static final Long ALL = -1L;
    }
    
    public static class PLATFORM {
    	public static final String AGENT = "1";
    	public static final String CLIENT = "0";
    }
    
    public static class REALTIME_STAT_TYPE {
    	public static final String AGENT = "agent";
    	public static final String CLIENT = "client";
    }
    
    public static class ENGINE_STAT_TYPE {
    	public static final String CLIENT = "client";
    	public static final String ADVERTGROUP = "advertgroup";
    	public static final String ADVERT = "advert";
    }
    
    public static final String  ENGINE_REALTIME_URL = "http://10.13.224.75:10060/redis/report/get";

}
