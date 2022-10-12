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

    // 결과 타입에 따른 name 반환
    public static String getBatchResult(String codeId) throws Exception {
        return BatchResult.getInfo(codeId).name();
    }

    // 성공 코드
    public static String getSuccCode() {
        return BatchResult.SUCC.getCodeId();
    }

    // 부분 코드
    public static String getPartCode() {
        return BatchResult.PART_SUCC.getCodeId();
    }

    // 실패 코드
    public static String getFailCode() {
        return BatchResult.FAIL.getCodeId();
    }

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

    @Getter
    @AllArgsConstructor
    public enum BatchResult {
        SUCC("S", "전체 성공"),
        PART_SUCC("P", "일부 성공"),
        FAIL("F", "전체 실패");

        private static final Map<String, BatchResult> infos = Collections
                .unmodifiableMap(Stream
                        .of(values())
                        .collect(Collectors
                        .toMap(BatchResult::getCodeId, Function.identity())
                        ));

        private static BatchResult getInfo(String codeId) throws Exception {
            return Optional.ofNullable(infos.get(codeId))
                    .orElseThrow(() -> new Exception("매칭되는 BatchResult 없습니다."));
        }

        private final String codeId;
        private final String codeDesc;
    }
}
