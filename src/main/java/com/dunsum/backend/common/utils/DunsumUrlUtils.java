package com.dunsum.backend.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DunsumUrlUtils {

    private static final String PATH_VAL_PREFIX = "::";
    public static final String URL_SEPARATOR = "/";

    public static String bindPathValUrl(String url, Object o) throws Exception {
        if(DunsumObjectUtils.isBlank(url)){
            return "";
        }

        if(DunsumObjectUtils.isBlank(o)){
            return url;
        }

        StringBuilder sb = new StringBuilder();
        Map<String, Object> map = DunsumObjectUtils.convertMap(o);
        StringTokenizer stn = new StringTokenizer(url, URL_SEPARATOR);
        while(stn.hasMoreTokens()){
            String token = stn.nextToken();
            sb.append(URL_SEPARATOR);
            if(token.startsWith(PATH_VAL_PREFIX)) {
                token = token.replaceAll(PATH_VAL_PREFIX, "");
                String token2val = (String)map.get(token);
                sb.append(token2val);
            } else {
                sb.append(token);
            }
        }
        return sb.toString();
    }

    public static Map<String, Object> eraseMapKeys(String url, Object o, boolean containBlank) throws Exception {
        if(DunsumObjectUtils.isBlank(o)){
            return new HashMap<>();
        }

        Map<String, Object> map = DunsumObjectUtils.convertMap(o);
        StringTokenizer stn = new StringTokenizer(url, URL_SEPARATOR);
        while(stn.hasMoreTokens()){
            String token = stn.nextToken();
            if(token.startsWith(PATH_VAL_PREFIX)) {
                map.remove(token);
            }
        }

        // 빈값을 포함하지 않을 시
        if(!containBlank){
            DunsumObjectUtils.eraseMapIfBlack(map);
        }

        return map;
    }

}
