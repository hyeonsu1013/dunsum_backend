package com.dunsum.backend.outside;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OutsideEnumFactory {

    @Getter
    @AllArgsConstructor
    public enum OutApisEnum {

        DNF("dnf", "df");

        private final String name;
        private final String apiName;
    }
}
