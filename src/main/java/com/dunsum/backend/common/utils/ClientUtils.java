package com.dunsum.backend.common.utils;

import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;

public class ClientUtils {

    /**
     * <pre>
     * get UserAgent Hints
     * </pre>
     * @param request HttpServletRequest
     * @return 접근장치 UA
     */
    public static String getUserAgent(HttpServletRequest request) {
        String clntIp = getClientIP(request);
        String acesEqpmt = getAcesEqpmt(request);
        String browser = getBrowserNm(request);
        String os = getOs(request);;

        return String.format("%s/%s/%s/%s", clntIp, acesEqpmt, os, browser);
    }

    /**
     * <pre>
     * get 접속 IP
     * </pre>
     * @param request HttpServletRequest
     * @return 접근장치 IP
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        ip = ip.split(",")[0];
        return ip;
    }

    /**
     * <pre>
     * 접근장치를 반환하는 메소드
     * </pre>
     * @param request HttpServletRequest
     * @return 접근장치 문자열
     */
    public static String getAcesEqpmt(HttpServletRequest request) {
        String headerStr = request.getHeader( "User-Agent" ).toUpperCase();
        String acesEqpmt = "PC";

        if ( headerStr.contains( "MOBILE" ) || headerStr.contains( "MOBI" ) ) {
            acesEqpmt = "MOBILE";
        }

        return acesEqpmt;
    }

    /**
     * <pre>
     * 운영체제를 반환하는 메소드
     * </pre>
     * @param request HttpServletRequest
     * @return 운영체제 문자열
     */
    public static String getOs(HttpServletRequest request) {
        try {
            String headerStr = request.getHeader( "User-Agent" );
            String os = "";

            Parser parser = new Parser();
            Client client = parser.parse( headerStr );
            os = client.os.family;

            return os;
        } catch (Exception e) {
            return "unknown";
        }
    }

    /**
     * <pre>
     * 브라우저 이름을 반환하는 메소드
     * </pre>
     * @param request HttpServletRequest
     * @return 브라우저 이름 문자열
     */
    public static String getBrowserNm(HttpServletRequest request) {
        String headerStr = request.getHeader( "User-Agent" ).toUpperCase();
        String browser = "";

        if (headerStr.contains("TRIDENT") || headerStr.contains("MSIE")) { //IE

            if (headerStr.contains("TRIDENT/7")) {
                browser = "IE 11";
            } else if (headerStr.contains("TRIDENT/6")) {
                browser = "IE 10";
            } else if (headerStr.contains("TRIDENT/5")) {
                browser = "IE 9";
            } else if (headerStr.contains("TRIDENT/4")) {
                browser = "IE 8";
            } else if (headerStr.contains("EDG") || headerStr.contains("EDGE")) {
                browser = "IE Edge";
            }

        } else if (headerStr.contains("WHALE")) { //네이버 WHALE
            browser = "Whale";
        } else if (headerStr.contains("OPERA") || headerStr.contains("OPR")) { //오페라
            browser = "Opera";
        } else if (headerStr.contains("FIREFOX")) { //파이어폭스
            browser = "Firefox";
        } else if (headerStr.contains("SAFARI") && !headerStr.contains("CHROME")) { //사파리
            browser = "Safari";
        } else if (headerStr.contains("CHROME")) { //크롬
            browser = "Chrome";
        } else {
            browser = "Other";
        }

        return browser;
    }
}