package com.dunsum.backend.outside.dnf.enums;

import com.dunsum.backend.batch.enums.BatchMgmtFactory;
import com.dunsum.backend.common.utils.DunsumObjectUtils;
import com.dunsum.backend.common.utils.DunsumUrlUtils;
import com.dunsum.backend.common.vo.environment.AppConnDataVO;
import com.dunsum.backend.outside.OutsideEnumFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DnfEnumsFactory {

    private static final OutsideEnumFactory.OutApisEnum oae = OutsideEnumFactory.OutApisEnum.DNF;

    public static String getSelTypeCode(String selType) throws Exception {
        return DnfSelectType.getInfo(selType).getCodeId();
    }

    public static String getApiUrl(AppConnDataVO appConnDataVO, DnfApiEnums dnfApiEnums) throws Exception {
        return getApiUrl(appConnDataVO, dnfApiEnums, null);
    }
    public static String getApiUrl(AppConnDataVO appConnDataVO, DnfApiEnums dnfApiEnums, Object o) throws Exception {
        String url = appConnDataVO.getDomain() + DunsumUrlUtils.URL_SEPARATOR + oae.getApiName();
        url += dnfApiEnums.getUrl().startsWith(DunsumUrlUtils.URL_SEPARATOR) ? dnfApiEnums.getUrl() : DunsumUrlUtils.URL_SEPARATOR + dnfApiEnums.getUrl();

        // Path 바인딩 변수 있을 시
        if(dnfApiEnums.isPathVariable()){
            url = DunsumUrlUtils.bindPathValUrl(url, o);
        }

        return url;
    }

    public static Map<String, Object> getApiParams(AppConnDataVO appConnDataVO, DnfApiEnums dnfApiEnums) throws Exception {
        return getApiParams(appConnDataVO, dnfApiEnums, null);
    }
    public static Map<String, Object> getApiParams(AppConnDataVO appConnDataVO, DnfApiEnums dnfApiEnums, Object o) throws Exception {
        String url =  DunsumUrlUtils.URL_SEPARATOR + oae.getApiName();
        url += dnfApiEnums.getUrl().startsWith(DunsumUrlUtils.URL_SEPARATOR) ? dnfApiEnums.getUrl() : DunsumUrlUtils.URL_SEPARATOR + dnfApiEnums.getUrl();

        Map<String, Object> map = null;

        // Path 바인딩 변수 있을 시
        if(dnfApiEnums.isPathVariable()){
            map = DunsumUrlUtils.eraseMapKeys(url, o, false);
        } else {
            map = DunsumObjectUtils.convertMap(o);
            DunsumObjectUtils.eraseMapIfBlack(map);
        }

        // Api Key 항상 포함한다.
        map.put(appConnDataVO.getKeyName(), appConnDataVO.getKey());

        return map;
    }

    @Getter
    @AllArgsConstructor
    public enum DnfSelectType {
        API_CONNECT("A", "API"),
        DB_CONNECT("D", "Database"),
        REDIS_CONNECT("R", "Redis");


        private final String codeId;
        private final String CodeDesc;

        private static final Map<String, DnfSelectType> infos = Collections.unmodifiableMap(Stream
                        .of(values())
                        .collect(Collectors
                        .toMap(DnfSelectType::getCodeDesc, Function.identity())
                        ));

        private static DnfSelectType getInfo(String selType) throws Exception {
            return Optional.ofNullable(infos.get(selType))
                            .orElseThrow(() -> new Exception("매칭되는 DnfSelectType 없습니다."));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum DnfApiEnums {
        SEL_SERVERS("/servers", HttpMethod.GET, false);

        private final String url;
        private final HttpMethod httpMethod;
        private final boolean isPathVariable;
    }


}
