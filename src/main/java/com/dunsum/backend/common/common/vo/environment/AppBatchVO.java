package com.dunsum.backend.common.common.vo.environment;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Batch 환경변수 VO
 * @author javayaji
 */
@Data
@Component
@ConfigurationProperties("batch")
public class AppBatchVO {

    Map<String, AppCronDataVO> cronData;

    public AppCronDataVO getCronData(String name) {
        return cronData.get(name);
    }
}