package com.dunsum.backend.batch.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BatchMgmtFactory {

    // Batch Info 조회
    public static BatchMgmt getBatchMgmt(String methodName) throws Exception {
        return BatchMgmt.getInfo(methodName);
    }

    // Batch 실행타입 구분
    public static String getRunType(boolean isBatch) {
        return isBatch ? BatchRunType.AUTOMATIC.getCodeId() : BatchRunType.MANUAL.getCodeId();
    }

    @Getter
    @AllArgsConstructor
    public enum BatchMgmt {

        DNF_SERVER("dnfServer", "001_000000", "DNF Server 목록 조회");

        private final String methodName;
        private final String bachMgmtId;
        private final String BachDesc;

        private static final Map<String, BatchMgmt> infos = Collections
                .unmodifiableMap(Stream
                        .of(values())
                        .collect(Collectors
                                .toMap(BatchMgmt::getMethodName, Function.identity())
                        ));

        private static BatchMgmt getInfo(String methodName) throws Exception {
            return Optional.ofNullable(infos.get(methodName))
                    .orElseThrow(() -> new Exception("매칭되는 BatchMgmt 없습니다."));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum BatchRunType {
        AUTOMATIC("A", "Scheduler 의한 실행"),
        MANUAL("M", "사용자에 의한 실행");

        private final String codeId;
        private final String codeDesc;
    }
}
