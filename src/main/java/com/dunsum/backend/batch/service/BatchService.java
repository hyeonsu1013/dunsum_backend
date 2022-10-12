package com.dunsum.backend.batch.service;

import com.dunsum.backend.common.vo.environment.AppCronDataVO;

public interface BatchService {

    /**
     * Batch Strategy Method
     * @param isBatch - Scheduler 의한 실행인지 판별
     * @param methodName - 실행할 Method 이름
     * @param appCronDataVO - Batch Method 정보
     * @throws Exception
     */
    void run(boolean isBatch, String methodName, AppCronDataVO appCronDataVO) throws Exception;
}
