package co.ichongwu.vidser.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtilsBean;

public class ValueUtil {

    /** 123 */
    private static final Pattern UNSIGNED_INTEGER_PATTERN = Pattern.compile("^\\d+$");

    /** 123 -321 */
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");

    /** 123.456 */
    private static final Pattern UNSIGNED_DECIMAL_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");

    /** 123.456 -654.321 */
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    /** 2012-06-01 */
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    /** 2012-06-01 12:23:34 */
    private static final Pattern DATETIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$");

    /** 2012-06-01 */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final ThreadLocal<DateFormat> DATE_FORMAT_LOCAL = new DateFormatThreadLocal(DATE_FORMAT);

    /** 20120601 */
    private static final String DATE_INT_FORMAT = "yyyyMMdd";

    private static final ThreadLocal<DateFormat> DATE_INT_FORMAT_LOCAL = new DateFormatThreadLocal(DATE_INT_FORMAT);

    /** 2012-06-01 12:23:34 */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<DateFormat> DATETIME_FORMAT_LOCAL = new DateFormatThreadLocal(DATETIME_FORMAT);

    /** 1,2,3 */
    private static final Pattern INTEGER_ARRAY_PATTERN = Pattern.compile("^-?\\d+(,-?\\d+)*$");

    private static final Pattern INTEGER_ARRAY_SEPARATOR_PATTERN = Pattern.compile(",");

    private static final ConvertUtilsBean CONVERT_UTILS_BEAN = new ConvertUtilsBean();

    static {
        CONVERT_UTILS_BEAN.register(false, true, 0);
    }
    
    public static boolean match(Pattern p, String s) {
        if (p != null && s != null && p.matcher(s).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isUnsignedInteger(String s) {
        return match(UNSIGNED_INTEGER_PATTERN, s);
    }

    public static boolean isInteger(String s) {
        return match(INTEGER_PATTERN, s);
    }

    public static boolean isUnsignedDecimal(String s) {
        return match(UNSIGNED_DECIMAL_PATTERN, s);
    }

    public static boolean isDecimal(String s) {
        return match(DECIMAL_PATTERN, s);
    }

    public static boolean isDate(String s) {
        return match(DATE_PATTERN, s);
    }

    public static boolean isDatetime(String s) {
        return match(DATETIME_PATTERN, s);
    }

    public static boolean isIntegerArray(String s) {
        return match(INTEGER_ARRAY_PATTERN, s);
    }

    /**
     * 
     * @param string
     *            "abcd"
     * @param c
     *            'z'
     * @param length
     *            8
     * @return "zzzzabcd"
     */
    public static String leftPadding(String string, char c, int length) {
        if (string == null) {
            return null;
        }
        if (string.length() < length) {
            char[] chars = new char[length];
            int fillLength = length - string.length();
            Arrays.fill(chars, 0, fillLength, c);
            System.arraycopy(string.toCharArray(), 0, chars, fillLength, string.length());
            string = new String(chars);
        }
        return string;
    }

    public static <T> String join(Collection<T> collection, Object separator) {
        StringBuffer stringBuffer = new StringBuffer();
        if (collection != null//
                        && separator != null) {
            boolean addition = false;
            for (T t : collection) {
                if (addition) {
                    stringBuffer.append(separator);
                }
                stringBuffer.append(t);
                addition = true;
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 
     * @param s
     *            2012-06-01
     * @return Date or null
     */
    public static Date toDate(String s) {
        if (isDate(s)) {
            try {
                return DATE_FORMAT_LOCAL.get().parse(s);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    /**
     * 
     * @param s
     *            2012-06-01 12:23:34
     * @return Date or null
     */
    public static Date toDatetime(String s) {
        if (isDatetime(s)) {
            try {
                return DATETIME_FORMAT_LOCAL.get().parse(s);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    /**
     * 
     * @param date
     * @return 2012-06-01 or null
     */
    public static String dateString(Date date) {
        return date == null ? null : DATE_FORMAT_LOCAL.get().format(date);
    }

    /**
     * 
     * @param date
     * @return 20120601 or null
     */
    public static Integer dateInt(Date date) {
        return date == null ? null : Integer.valueOf(DATE_INT_FORMAT_LOCAL.get().format(date));
    }

    /**
     * 
     * @param date
     * @return 2012-06-01 12:23:34 or null
     */
    public static String datetimeString(Date date) {
        return date == null ? null : DATETIME_FORMAT_LOCAL.get().format(date);
    }

    /**
     * 
     * @param date
     *            2012-06-01 12:23:34
     * @return 2012-05-31 12:23:34
     */
    public static Date prevDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 
     * @param date
     *            2012-06-01 12:23:34
     * @return 2012-06-02 12:23:34
     */
    public static Date nextDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 
     * @param date
     *            2012-06-01 12:23:34.567
     * @return 2012-06-01 00:00:00.0
     */
    public static Date cutDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();

    }

    public static BigDecimal toBigDecimal(String s) {
        if (isDecimal(s)) {
            return new BigDecimal(s);
        }
        return null;
    }

    /**
     * 
     * @param decimal
     *            123.00 123.40 123.45
     * @return 123 123.4 123.45
     */
    public static BigDecimal cutDecimalZero(BigDecimal decimal) {
        if (decimal == null) {
            return null;
        }
        for (BigDecimal tryCut = decimal.setScale(decimal.scale() - 1, RoundingMode.HALF_UP); //
        decimal.scale() > 0 && tryCut.doubleValue() == decimal.doubleValue(); //
        tryCut = decimal.setScale(decimal.scale() - 1, RoundingMode.HALF_UP)) {
            decimal = tryCut;
        }
        return decimal;
    }

    /**
     * 
     * @param doublePercent
     *            0.123
     * @return 12.3%
     */
    public static String percentString(Double doublePercent) {
        if (doublePercent == null) {
            return "";
        }
        BigDecimal decimalPercent = new BigDecimal(doublePercent);
        decimalPercent = decimalPercent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        decimalPercent = cutDecimalZero(decimalPercent);
        return decimalPercent.toString() + '%';
    }

    public static <K, V> Map<K, V> emptyMap() {
        return new HashMap<K, V>(0);
    }

    public static <T> List<T> emptyList() {
        return new ArrayList<T>(0);
    }

    public static <T> List<T> constList(T... t) {
        return Collections.unmodifiableList(Arrays.asList(t));
    }

    public static <T> T convert(Object o, Class<T> t) {
        if (o == null || t == null) {
            return null;
        }
        return t.cast(CONVERT_UTILS_BEAN.convert(o, t));
    }
    
    public static <T> T convert(Object o, Class<T> t, T defaultValue) {
        if (o == null || t == null) {
            return defaultValue;
        }
        return t.cast(CONVERT_UTILS_BEAN.convert(o, t));
    }
    
    public static <T> T convert(Object o, T defaultValue) {
        if (defaultValue == null) {
            return null;
        }
        if (o == null) {
            return defaultValue;
        }
        @SuppressWarnings("unchecked")
        Class<T> t = (Class<T>) defaultValue.getClass();
        return t.cast(CONVERT_UTILS_BEAN.convert(o, t));
    }

    public static <T> List<T> toList(String s, Class<T> t) {
        List<T> list;
        if (!isIntegerArray(s)) {
            list = emptyList();
        }
        else {
            String[] stringArray = INTEGER_ARRAY_SEPARATOR_PATTERN.split(s);
            list = new ArrayList<T>(stringArray.length);
            for (int i = 0, length = stringArray.length; i < length; i++) {
                list.add(convert(stringArray[i], t));
            }
        }
        return list;
    }

    public static final Integer NULL_LESS_COMPARE_EQUALS = 0;

    public static final Integer NULL_LESS_COMPARE_LESS = -1;

    public static final Integer NULL_LESS_COMPARE_GREATER = 1;

    public static final Integer NULL_LESS_COMPARE_UNKNOW = null;

    public static Integer nullLessCompare(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return NULL_LESS_COMPARE_EQUALS;
        }
        else if (o1 == null) {
            return NULL_LESS_COMPARE_LESS;
        }
        else if (o2 == null) {
            return NULL_LESS_COMPARE_GREATER;
        }
        else {
            return NULL_LESS_COMPARE_UNKNOW;
        }
    }
    
    private static class DateFormatThreadLocal extends ThreadLocal<DateFormat> {
        
        private String dateFormat;

        public DateFormatThreadLocal(String dateFormat) {
            super();
            this.dateFormat = dateFormat;
        }
        
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(dateFormat);
        }
        
    }
    /**
     * 验证 ip是否合法
     * @author qiaozhenpeng
     * 2013-12-16	
     * @return
     */
	public static boolean validateIp(String ips){
    	 String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
	    			
			                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
			
			                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
			
			                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
	
	        Pattern pattern = Pattern.compile(ip);
	
	        Matcher matcher = pattern.matcher(ips);
	        return matcher.matches();
    }
}
