package com.dunsum.backend.common.utils;

import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;

/**
 * Custom String Utils
 * @author javayaji
 */
@Component
public class DunsumStringUtils {

    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String defaultStr(String str, String defaultStr) {
        if(isBlank(str)) return defaultStr;
        else return str;
    }

    /**
     * 접두사 제거
     * @param str 변경대상
     * @param prefix 접두사
     * @return 접두사 제거한 Str
     */
    public static String RemovePrefix(String str, String prefix) {
        return str.replaceAll(prefix,"");
    }

    /**
     * 카멜표기법 변경
     * @param str 변경대상
     * @param firstUp 첫문자 대문자변경 여부
     * @return 카멜표기법으로 변경한 Str
     */
    public static String getCamelStr(String str, boolean firstUp) {
        String camelName = JdbcUtils.convertUnderscoreNameToPropertyName(str);
        if(firstUp) {
            camelName = camelName.substring(0, 1).toUpperCase() + camelName.substring(1);
        }
        return camelName;
    }

    /**
     * String이 숫자로 이루어졌는지 체크
     * @param str 체크할 String
     */
    public static boolean isNumber(String str) {
        if(str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
