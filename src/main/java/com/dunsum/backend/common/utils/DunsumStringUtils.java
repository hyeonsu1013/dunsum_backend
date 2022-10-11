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
        return str != null && !"".equals(str.trim());
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


}
