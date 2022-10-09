package com.dunsum.backend.outside.dnf.enums;

import com.dunsum.backend.common.utils.DunsumObjectUtils;
import com.dunsum.backend.common.utils.DunsumUrlUtils;
import com.dunsum.backend.common.vo.environment.AppConnDataVO;
import com.dunsum.backend.outside.OutsideEnumFactory;
import com.dunsum.backend.outside.dnf.model.DnfSrvrModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Map;

public class DnfEnumsFactory {

    private static final OutsideEnumFactory.OutApisEnum oae = OutsideEnumFactory.OutApisEnum.DNF;

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
    @SuppressWarnings({ "rawtypes"})
    public enum DnfApiEnums {
        SEL_SERVERS("/servers", HttpMethod.GET, false, DnfSrvrModel.class);

        private final String url;
        private final HttpMethod httpMethod;
        private final boolean isPathVariable;
        private final Class rtnModel;
    }
}
