package com.dunsum.backend.common.apis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
@AllArgsConstructor
public enum OutApisEnum {

    DNF("dnf", "df", HttpMethod.GET);

    private final String name;
    private final String apiName;
    private final HttpMethod httpMethod;
}
