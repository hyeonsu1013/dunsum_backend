package com.dunsum.backend.common.vo.environment;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Outside(otsd) 환경변수 VO
 * @author javayaji
 */

@Data
@Component
@ConfigurationProperties("outside")
public class AppOutsideVO {

    private Map<String, AppConnDataVO> connData;

    public AppConnDataVO getConnData(String name) {
        return connData.get(name);
    }
}