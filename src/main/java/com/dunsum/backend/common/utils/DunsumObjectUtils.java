package com.dunsum.backend.common.utils;

import com.dunsum.backend.common.vo.BaseVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DunsumObjectUtils {

    @SuppressWarnings({ "rawtypes"})
    public static boolean isBlank(Object o) throws Exception {
        if(o == null) {
            return true;
        }

        boolean result = true;

        if(o instanceof String){
            String str = (String)o;
            result = "".equals(str.trim());
        } else if(o instanceof Character) {
            Character ch = (Character)o;
            result = ch == ' ';
        } else if(o instanceof List) {
            List list = (List)o;
            result = list.isEmpty();
        } else if(o instanceof Map) {
            Map map = (Map) o;
            result = map.isEmpty();
        } else if(o instanceof Set) {
            Set set = (Set) o;
            result = set.isEmpty();
        } else if(o instanceof Number) {
            Number number = (Number) o;
            // ModelUtils.getDataType 형변환에 따른다.
            if(number instanceof Long){
                result = number.longValue() == 0L;
            } else if (number instanceof Integer) {
                result = number.intValue() == 0;
            } else if (number instanceof BigInteger) {
                result = number.equals(BigInteger.ZERO);
            } else if (number instanceof  BigDecimal) {
                result = number.equals(BigDecimal.ZERO);
            }
        } else if (o instanceof Boolean || o instanceof Timestamp) {
            // 위 두 타입은 항상 값이 존재하는 것으로 판단
            result = false;
        } else if (o instanceof BaseVO) {
            // TODO :: BaseVO인 경우 빈값 여부 판단 로직 추가
            result = false;
        }

        return result;
    }

    @SuppressWarnings({ "unchecked"})
    public static Map<String, Object> convertMap(Object o) throws Exception {
        if(DunsumObjectUtils.isBlank(o)){
            return new HashMap<>();
        }

        ObjectMapper om = new ObjectMapper();
        return om.convertValue(o, Map.class);
    }

    public static void eraseMapIfBlack(Map<String, Object> map) throws Exception {
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            Object val = map.get(key);
            if(DunsumObjectUtils.isBlank(val)) {
                map.remove(key);
            }
        }
    }

    /**
     * Object를 받아 int를 반환
     * @param o 변환할 Object
     */
    public static int convertInt(Object o) throws  Exception {
        if(o == null){
            return 0;
        }

        if(o instanceof String){
            String str = (String)o;
            if(!DunsumStringUtils.isNumber(str)){
                return 0;
            }
            return Integer.parseInt(str);
        } else if (o instanceof Number) {
            Number n = (Number) o;
            return n.intValue();
        }

        return 0;
    }
}
