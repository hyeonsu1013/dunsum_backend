package com.dunsum.backend.batch;

import com.dunsum.backend.batch.service.BatchService;
import com.dunsum.backend.common.common.vo.environment.AppBatchVO;
import com.dunsum.backend.common.common.vo.environment.AppCronDataVO;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;

@Component
public class Batch {

    public static final Logger bachLogger = LoggerFactory.getLogger(Batch.class);

    /**
     *  ** Bach Class Singleton Pattern
     *
     *  목적 :: Batch 관리 화면에서 수동으로 Batch 동작 시
     *  Bach class Instance 새로운 생성을 방지하기 위함
     *
     *  다른 클래스에서 Batch 생성자(new) 또는 DI(Dependency Injection) 사용 불가능 처리
     *  1. 여러 객체 생성에 따른 Scheduler 오작동/중복동작, Batch 이상 동작 등을 사전 방지
     *      > 객체 수에 따라 스케쥴러에 영향은 없지만, 추후 Batch 기능에 따른 발생할 수 있는 문제를 방지
     *  2. Scheduler Bach class 객체와 uniqueInstance 동일
     */

    private static Batch uniqueInstance;
    public static Batch getInstance() {
        return uniqueInstance;
    }

    @Autowired
    private Batch(BatchService batchService, AppBatchVO appBatchVO) {
        super();
        this.batchService = batchService;
        this.cronDataMap = appBatchVO.getCronData();
        uniqueInstance = this;
    }
    // -** Bach Class Singleton Pattern

    @Value("${batch.active.host}")
    private String BACH_ACTV_HOST;

    // DI 대상
    private final BatchService batchService;
    @Getter
    private final Map<String, AppCronDataVO> cronDataMap;

    // 배치 실행 여부 - 호스트 체크
    private boolean BACH_ACTV = false;



    /* -------------------------------------------------------------------------------------------------------------------------*/
    /* ----------------------------------------- Chapter01. BATCH @PostConstruct -----------------------------------------------*/
    /* -------------------------------------------------------------------------------------------------------------------------*/

    @PostConstruct
    private void postConstruct() throws Exception {
        this.setBatchActive();
    }

    // 배치 실행 IP 체크
    private void setBatchActive() {
        // host check
        boolean active = false;
        try {
            Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces();
            while (niEnum.hasMoreElements()) {
                NetworkInterface ni = niEnum.nextElement();
                Enumeration<InetAddress> addressesEnum = ni.getInetAddresses();
                while (addressesEnum.hasMoreElements()) {
                    InetAddress inetAddress = addressesEnum.nextElement();
                    if(this.BACH_ACTV_HOST.equals(inetAddress.getHostAddress())) {
                        active = true;
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            bachLogger.error("Batch.setBatchActive Err :: {}", e.getMessage());
        }
        BACH_ACTV = active;
    }



    /* -------------------------------------------------------------------------------------------------------------------------*/
    /* ------------------------------------------------ Chapter02. BATCH run ---------------------------------------------------*/
    /* -------------------------------------------------------------------------------------------------------------------------*/

    /**
     * 배치 강제실행
     * @param methodName 메서드명
     * @throws Exception
     */
    public void run(boolean isBatch, String methodName) throws Exception {
        batchService.run(isBatch, methodName, this.cronDataMap.get(methodName));
    }



    /* -------------------------------------------------------------------------------------------------------------------------*/
    /* ------------------------------------------------ Chapter03. BATCH List --------------------------------------------------*/
    /* -------------------------------------------------------------------------------------------------------------------------*/

    @Scheduled(cron="${bach.cronData.dnfServer.cron}")
    public void dnfServer() throws Exception {
        if(this.BACH_ACTV){
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            this.run(true, methodName);
        }
    }
}
