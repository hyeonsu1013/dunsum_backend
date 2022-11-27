package com.dunsum.backend.common.utils;

import com.dunsum.backend.common.vo.BaseVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

public class DunsumObjectUtils {

    @SuppressWarnings({ "rawtypes"})
    public static boolean isBlank(Object o) {
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

    public static boolean isNotBlank(Object o) { return !isBlank(o); }

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

    /**
     *  객체복사
     *  @return T : return 객체 타입
     *  @param rtnClz : return 객체의 Class
     *  @param copyModel : 복사할 Object
     *  @param superCntn : 상위클래스 Field 포함 여부
     *  @author kb0153
     */
    public static <T> T getCloneObject(Class<T> rtnClz, Object copyModel, boolean superCntn) {
        try {
            Class<?> clazz = copyModel.getClass();

            // 반환할 Instance 생성
            Constructor<T> cons = rtnClz.getDeclaredConstructor();
            T clone = cons.newInstance();

            // 반환할 Instance의 Field Name 목록
            List<String> rtnFieldNameList = new ArrayList<String>();
            List<Field> fieldList = superCntn ? getAllFields(rtnClz) : Arrays.asList(rtnClz.getDeclaredFields());
            for(Field field : fieldList) {
                rtnFieldNameList.add(field.getName());
            }

            // Field 값 복사
            List<Field> copyFieldList = superCntn ? getAllFields(clazz) : Arrays.asList(rtnClz.getDeclaredFields());
            for (Field field : copyFieldList) {
                // 복사할 Object final, static Field 통과
                if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                // 복사할 Object Field가 반환할 Object Field에 포함되어 있는지 확인
                if(!rtnFieldNameList.contains(field.getName())) {
                    continue;
                }

                field.setAccessible(true);
                Object data = field.get(copyModel);
                field.set(clone, data);

                field.setAccessible(false);
            }
            return clone;
        } catch (Throwable e) {}

        return null;
    }

    /**
     * 상위 클래스를 포함한 모든 Field 반환
     * @param clazz class
     * @return List
     */
    public static <T> List<Field> getAllFields(Class<? super T> clazz){
        Objects.requireNonNull(clazz);

        List<Field> fields = new ArrayList<>();
        while(clazz != null){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
