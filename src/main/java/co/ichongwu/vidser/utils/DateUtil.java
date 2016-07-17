package co.ichongwu.vidser.utils;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 
 * #func 日期格式化utils #desc 在此添加实现相关说明
 * 
 * @author hedan
 * @version 4.0
 * 
 */
public class DateUtil {

	public static final int SECOND = 1;
	public static final int MINUTE = 2;
	public static final int HOUR = 3;
	public static final int DAY = 4;

	/**
	 * 
	 * #func 判断指定日期的格式是否合法<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static boolean isValid(String date, String pattern) {
		Date d = parseDate(date, pattern);
		return d != null && format(d, pattern).equals(date);
	}

	/**
	 * 
	 * #func 格式化日期<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 
	 * #func 格式化日期<br>
	 * #desc 使用yyyy-MM-dd作为样式
	 * 
	 * @author hedan
	 * @version VERSION
	 */
	public static String format(Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd");
	}

	/**
	 * 
	 * #func 把字符串转化为日期<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static Date parseDate(String date, String pattern) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(date,
					new String[] { pattern });
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 
	 * #func 把字符串转化为日期<br>
	 * #desc 使用yyyy-MM-dd作为样式
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static Date parseDate(String date) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(date,
					new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 
	 * #func 计算相对年<br>
	 * #desc amount可以为负数
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static Date addYears(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.addYears(date, amount);
	}

	/**
	 * 
	 * #func 计算相对月<br>
	 * #desc amount可以为负数
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static Date addMonths(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.addMonths(date, amount);
	}

	/**
	 * 
	 * #func 计算相对日<br>
	 * #desc amount可以为负数
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static Date addDays(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, amount);
	}

	/**
	 * 
	 * #func 计算相对星期<br>
	 * #desc 在此添加实现相关说明
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static Date addWeeks(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.addWeeks(date, amount);
	}

	/**
	 * 
	 * #func 计算时间跨度<br>
	 * #desc 计算方式：结束时间减开始时间
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static long getTimeSpan(Date begin, Date end, int type) {
		long diff = end.getTime() - begin.getTime();
		switch (type) {
		case DAY:
		default:
			return diff / org.apache.commons.lang.time.DateUtils.MILLIS_PER_DAY;
		case HOUR:
			return diff
					/ org.apache.commons.lang.time.DateUtils.MILLIS_PER_HOUR;
		case MINUTE:
			return diff
					/ org.apache.commons.lang.time.DateUtils.MILLIS_PER_MINUTE;
		case SECOND:
			return diff
					/ org.apache.commons.lang.time.DateUtils.MILLIS_PER_SECOND;
		}
	}

	/**
	 * 
	 * #func 获得日期差<br>
	 * #desc 不足一天的忽略
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static long getDaySpan(Date begin, Date end) {
		return getTimeSpan(begin, end, DAY);
	}

	/**
	 * 
	 * #func 获得小时差<br>
	 * #desc 不足一小时的忽略
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static long getHourSpan(Date begin, Date end) {
		return getTimeSpan(begin, end, HOUR);
	}

	/**
	 * 
	 * #func 获得分钟差<br>
	 * #desc 不足一分钟的忽略
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static long getMinuteSpan(Date begin, Date end) {
		return getTimeSpan(begin, end, MINUTE);
	}

	/**
	 * 
	 * #func 获得秒差<br>
	 * #desc 不足一秒的忽略
	 * 
	 * @author hedan
	 * @version 4.0
	 */
	public static long getSecondSpan(Date begin, Date end) {
		return getTimeSpan(begin, end, SECOND);
	}

	/**
	 * 
	 * #func 获取月份差<br>
	 * 
	 * @author dongguoshuang
	 */
	public static int getMonthSpan(Date begin, Date end) {
		Calendar beginCal = new GregorianCalendar();
		beginCal.setTime(begin);
		Calendar endCal = new GregorianCalendar();
		endCal.setTime(end);
		int m = (endCal.get(Calendar.MONTH)) - (beginCal.get(Calendar.MONTH));
		int y = (endCal.get(Calendar.YEAR)) - (beginCal.get(Calendar.YEAR));
		return y * 12 + m;
	}

	/**
	 * 
	 * #func 获取当前时间当月的第一天<br>
	 * 
	 * @author dongguoshuang
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		return org.apache.commons.lang.time.DateUtils.setDays(date, firstDay);
	}
	
	/**
	 * 
	 * #func 获取当前时间当月的最后一天<br>
	 * 
	 * @author dulin
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int firstDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return org.apache.commons.lang.time.DateUtils.setDays(date, firstDay);
	}

	/**
	 * 
	 * #func 获取时间的完整格式<br>
	 * #desc addDays为计算相对时间，可为负数
	 * 
	 * @author dulin
	 * @version 4.0.14
	 * 
	 */
	public static String parseFullFormat(String dateStr, String pattern,
			int addDays) {
		try {
			Date date = parseDate(dateStr, pattern);
			if (addDays != 0) {
				date = addDays(date, addDays);
			}
			return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获得指定日期以前的一个日期，0表示当天 正整数向前推 负数向后推
	 * 比如1 表示明天  -1表示昨天  0表示今天
	 * @author wuqiang
	 * @version 1.0.0
	 */
	public static Date getDateByNum(Date date,Integer num,String format) throws ParseException{
		if(num==null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		if(date!=null){
			cal.setTime(date);
		}
		cal.setTimeInMillis(cal.getTimeInMillis() + num*24L*60*60L*1000L);
		
		return strToDateTime(dateToString(cal.getTime(),format),"");
	}
	
	/**
	 * 获得指定日期以前的一个日期，0表示当天 正整数向前推 负数向后推
	 * 比如1 表示明天  -1表示昨天  0表示今天
	 * @author wuqiang
	 * @version 1.0.0
	 */
	public static Date getDateByNum(Date date,Integer num) throws ParseException{
		if(num==null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		if(date!=null){
			cal.setTime(date);
		}
		cal.setTimeInMillis(cal.getTimeInMillis() + num*24L*60*60L*1000L);
		
		return cal.getTime();
	}
	
	public static String dateToString(Date date, String format){
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static Date strToDateTime(String time) throws ParseException {

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		java.util.Date d = sdf.parse(time);

		return d;
	}
	
	public static Date strToDateTime(String time, String join) throws ParseException {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
		"yyyy" + join + "MM" + join + "dd");
		java.util.Date d = sdf.parse(time);
		return d;
	}
	
	

//	public static void main(String args[]) {
//		System.out.println(isValid("为而为", "yyyyMMdd"));
//		System.out.println(DateUtil.parseDate("20100801", "yyyyMMdd"));
//	}

}
