package com.dunsum.backend.common.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * datasource 환경변수 VO
 * @author javayaji
 */
@Data
@Component
@ConfigurationProperties("spring.datasource")
public class AppDataSourceVO {

    private String username;
    private String password;
    private String url;
    private String driver;

}
