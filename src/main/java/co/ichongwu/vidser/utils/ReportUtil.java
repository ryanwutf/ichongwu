package co.ichongwu.vidser.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReportUtil {
	
	private static Logger LOG = LoggerFactory.getLogger(ReportUtil.class);
	
	public static BigDecimal NON_SENSE = new BigDecimal(-1);
	
	/**
	 * 计算CTR，保留4位小数
	 * @param pv
	 * @param click
	 * @return
	 */
	public static BigDecimal ctr(Long pv, Long click) {
		if(pv == null || pv.equals(new Long(0))) {
			return NON_SENSE;
		}
		return new BigDecimal(click).divide(new BigDecimal(pv), 5, RoundingMode.HALF_UP);
	}
	
	/**
	 * 计算interest click_uv/impression_uv
	 * @return
	 */
	public static BigDecimal interest(Long uv, Long uclick) {
		if(uv == null || uv.equals(new Long(0))) {
			return NON_SENSE;
		}
		return new BigDecimal(uclick).divide(new BigDecimal(uv), 5, RoundingMode.HALF_UP);
	}
	
	/**
	 * CPM费用，保留2位小数，单位元
	 * @param pv
	 * @param pay
	 * @return
	 */
	public static BigDecimal ecpm(Long pv, BigDecimal pay) {
		if(pay == null || pv == null || pv.equals(new Long(0))) {
			return NON_SENSE;
		}
		return pay.multiply(new BigDecimal(1000)).divide(new BigDecimal(pv), 2, RoundingMode.HALF_UP);
	}
	
	/**
	 * cpc,保留2位小数，单位元
	 * @param click
	 * @param pay
	 * @return
	 */
	public static BigDecimal cpc(Long click, BigDecimal pay) {
		if(pay == null || click == null || click.equals(new Long(0))) {
			return NON_SENSE;
		}
		return pay.divide(new BigDecimal(click), 2, RoundingMode.HALF_UP);
	}
	
	

		
	public static String likeStr(String name) {
		return "%" + name + "%";
	}
	
	public static String sqlLog(String sql) {
		return "[SQL]:" + sql; 
	}
	
	public static String paramsLog(Map<String, Object> params) {
		StringBuffer log = new StringBuffer("[PARAMS]: ");
		if(params != null) {
			for(String key : params.keySet()) {
				log.append(key + ":" + params.get(key).toString()).append("; ");
			}
		}
		return log.toString();
	}
	
	
	
	

//	public static void main(String[] args) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("key1", "value1");
//		map.put("key2", "value2");
//		System.out.println(paramsLog(map));
//	}

}
