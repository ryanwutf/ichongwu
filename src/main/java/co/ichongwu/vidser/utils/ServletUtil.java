package co.ichongwu.vidser.utils;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


public class ServletUtil {

    public static final void printJson(Object json, HttpServletRequest request, HttpServletResponse response) {
        if (json == null) {
            return;
        }
        if (isIE(request)) {
            response.setContentType("text/plain;charset=UTF-8");
        }
        else {
            response.setContentType("application/json;charset=UTF-8");
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            JsonUtil.writeJsonString(json, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.flush();
            }
            IOUtil.close(writer);
        }
    }

    public static void setDownloadHeader(String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (isIE(request)) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            else {
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
        }
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
    }

    public static boolean isIE(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && userAgent.toLowerCase().indexOf("msie") > 0) {
            return true;
        }
        return false;
    }

    public static String remoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(remoteAddr)) {
            String forwardeds = request.getHeader("X-Forwarded-For");
            if (forwardeds != null) {
                int firstComma = forwardeds.indexOf(",");
                remoteAddr = firstComma >= 0 ? forwardeds.substring(0, firstComma) : forwardeds;
            }
        }
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr;
    }
    
    public static final Pattern IPV4_PATTERN = Pattern.compile("\\d+(\\.\\d+){3}");
    
    public static final Pattern IPV4_SPLIT = Pattern.compile("\\.");
    
    public static int intAddress(String ipv4) {
        if (StringUtils.isNotBlank(ipv4)
                        && IPV4_PATTERN.matcher(ipv4).matches()) {
            String[] segments = IPV4_SPLIT.split(ipv4);
            if (segments != null && segments.length == 4) {
                return (Integer.valueOf(segments[0]) & 0xff) << 24
                                | (Integer.valueOf(segments[1]) & 0xff) << 16
                                | (Integer.valueOf(segments[2]) & 0xff) << 8
                                | (Integer.valueOf(segments[3]) & 0xff);
            }
        }
        return 0;
    }

    public static String cookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
}
