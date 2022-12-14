package com.dunsum.backend.batch.service;

import com.dunsum.backend.batch.dao.BatchDao;
import com.dunsum.backend.batch.dao.BatchLogDao;
import com.dunsum.backend.batch.enums.BatchMgmtFactory;
import com.dunsum.backend.batch.model.BatchResultModel;
import com.dunsum.backend.common.dto.SystemDTO;
import com.dunsum.backend.common.utils.DunsumObjectUtils;
import com.dunsum.backend.common.utils.DunsumStringUtils;
import com.dunsum.backend.common.vo.environment.AppCronDataVO;
import com.dunsum.backend.common.vo.environment.AppOutsideVO;
import com.dunsum.backend.domains.entity.BachLogEntity;
import com.dunsum.backend.domains.outside.OutsideEnumFactory;
import com.dunsum.backend.domains.outside.dnf.model.DnfSrchModel;
import com.dunsum.backend.domains.outside.dnf.service.DnfSrvrService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    public static final Logger bachLogger = LoggerFactory.getLogger(BatchService.class);

    @Value("${project.name}")
    private String PRJT_NAME;
//    @Value("${spring.profiles}")
//    private String PROFILES;

    private final BatchDao batchDao;
    private final BatchLogDao batchLogDao;

    private final DnfSrvrService dnfService;
    private final AppOutsideVO appOutsideVO;

    private final String DNF_CONN_DATA_NAME = OutsideEnumFactory.OutApisEnum.DNF.getName();

    private final SimpleDateFormat SMPL_DATE_FORM = new SimpleDateFormat("yyyyMMddHHmmss");

    private final long SYS_USER_NO = SystemDTO.SYS_USER_NO;

    /* -----------------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------- Chapter01. BACH_LOG ?????? -------------------------------------------------*/
    /* -----------------------------------------------------------------------------------------------------------------------*/

    /*
     * LOG_BACH ????????? ?????? ??????
     */
    private String getMsg(BatchMgmtFactory.BatchMgmt info, boolean isStart) {
        return String.format("[%s][%s] %s (%s %s)", "local", PRJT_NAME, info.getBachDesc(), info.getMethodName(), (isStart ? "Begins" : "is Finished"));
    }

    private String getSubMsg(BatchResultModel result) throws Exception {
        String resultCode = result.getResultCode();
        String gubn = BatchMgmtFactory.getBatchResult(resultCode);

        String msg = "";
        if(BatchMgmtFactory.getSuccCode().equals(resultCode)){
            msg = result.getSuccMsg();
        } else if(BatchMgmtFactory.getPartCode().equals(resultCode)){
            msg = result.getPartMsg();
        } else if(BatchMgmtFactory.getFailCode().equals(resultCode)){
            msg = result.getFailMsg();
        }

        return String.format("%s Message :: %s", gubn, DunsumStringUtils.isBlank(msg) ? "" : msg);
    }

    // Batch runTime ??????
    private String procTimeMsg(long stTime, long edTime) {
        if(stTime == 0 || edTime == 0 || (stTime > edTime) ){
            return "";
        }
        long milliseconds = edTime - stTime;
        long hours = (milliseconds / 1000) / 60 / 60;
        long minutes = (milliseconds / 1000) / 60 % 60;
        long seconds = (milliseconds / 1000) % 60;
        return String.format("%d ms = %d h %d m %d s", milliseconds, hours, minutes, seconds);
    }

    private BachLogEntity insBachLog(boolean isBatch, String msg, long stTime, String logMgmtSeq) throws Exception {
        long rgstUserNo = this.getUserNo(isBatch);
        Timestamp timestamp = new Timestamp(stTime);

        BachLogEntity logBatch = BachLogEntity.builder()
                                    .msg(msg)
                                    .stDate(SMPL_DATE_FORM.format(timestamp))
                                    .bachMgmtId(logMgmtSeq)
                                    .runType(BatchMgmtFactory.getRunType(isBatch))
                                    .build();
        logBatch.setRgstUserNo(rgstUserNo);

        batchLogDao.insBachLog(logBatch);
        return logBatch;
    }

    private void updBachLogFnal(BachLogEntity logBatch, String msg, long stTime, long edTime) throws Exception {
        if(logBatch.getBachSeq() != 0) {
            logBatch.setMsg(msg);
            logBatch.setRunTime(this.procTimeMsg(stTime, edTime));
            logBatch.setEdDate(SMPL_DATE_FORM.format(new Timestamp(edTime)));
            batchLogDao.updBachLog(logBatch);
        }
    }



    /* -----------------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------- Chapter02. private Method -----------------------------------------------*/
    /* -----------------------------------------------------------------------------------------------------------------------*/

    // Batch ????????? USER_NO ??????
    private long getUserNo(boolean isBatch) throws Exception {
        long userNo = 0;
        if(isBatch) {
            userNo = SYS_USER_NO;
        } else {
            // TODO : ??????????????? ??????
            userNo = SYS_USER_NO;
        }
        return userNo;
    }



    /* -----------------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------------- Chapter03. run ----------------------------------------------------*/
    /* -----------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void run(boolean isBatch, String methodName, AppCronDataVO appCronDataVO) throws Exception {
        long stTime = 0;
        long errTime = 0;
        long edTime = 0;
        BatchResultModel result = new BatchResultModel();

        BachLogEntity logEntt = null;
        BatchMgmtFactory.BatchMgmt info = null;
        String subMsg = "";

        /*
         * 1. ???????????? ?????? ??? ????????? ??????
         * 2. ???????????? ?????? ??? Application.yml ??????
         */
        boolean flag = !isBatch || appCronDataVO.isUse();
        if(flag) {
            try {
                stTime = System.currentTimeMillis();
                info = BatchMgmtFactory.getBatchMgmt(methodName);

                String msg = this.getMsg(info, true);
                logEntt = this.insBachLog(isBatch, msg, stTime, info.getBachMgmtId());

                /*-- Batch ?????? --*/
                Class<?> bachClass = this.getClass();
                Object rtnEntt = null;

                Method bachMthd = bachClass.getDeclaredMethod(methodName, boolean.class);
                rtnEntt = bachMthd.invoke(this, isBatch);
                if(rtnEntt instanceof BatchResultModel){
                    result = (BatchResultModel) rtnEntt;
                    subMsg = this.getSubMsg(result);
                }
                /*-- Batch ?????? --*/

            } catch (InvocationTargetException e) {
                errTime = System.currentTimeMillis();
                Throwable th = e.getCause();
                String errCause = (th != null) ? th.getMessage() : "null";
                result = new BatchResultModel(BatchMgmtFactory.getFailCode(), errCause);
                subMsg = this.getSubMsg(result);
            } catch (Exception e) {
                errTime = System.currentTimeMillis();
                result = new BatchResultModel(BatchMgmtFactory.getFailCode(), e.getMessage());
                subMsg = this.getSubMsg(result);
            } finally {
                edTime = System.currentTimeMillis();
                if(logEntt != null){
                    if (!BatchMgmtFactory.getSuccCode().equals(result.getResultCode())) {
                        logEntt.setErrDate(SMPL_DATE_FORM.format(new Timestamp(errTime)));
                    }

                    if(!DunsumStringUtils.isBlank(subMsg)){
                        logEntt.setSubMsg(subMsg);
                    }

                    if(info != null){
                        String msg = this.getMsg(info, false);
                        logEntt.setEdDate(SMPL_DATE_FORM.format(new Timestamp(edTime)));
                        this.updBachLogFnal(logEntt, msg, stTime, edTime);
                    } else {
                        bachLogger.error("Batch run Error - info is null :: {}, {}, {}, {}", isBatch, methodName, appCronDataVO.toString(), subMsg);
                    }
                }
                // logEntt == null ?????? Logback ?????? ??????
                else {
                    bachLogger.error("Batch run Error - logEntt is null :: {}, {}, {}, {}", isBatch, methodName, appCronDataVO.toString(), subMsg);
                }
            }
        }
    }



    /* -----------------------------------------------------------------------------------------------------------------------*/
    /* ----------------------------------------------- Chapter04. Batch List -------------------------------------------------*/
    /* -----------------------------------------------------------------------------------------------------------------------*/

    @Transactional(rollbackFor = Exception.class)
    private BatchResultModel dnfServer(boolean isBach) throws Exception {
        DnfSrchModel srchModel = new DnfSrchModel();
        srchModel.setAppConnDataVO(this.appOutsideVO.getConnData(this.DNF_CONN_DATA_NAME));

        Map<String, Object> rtnMap = dnfService.updServers(srchModel);
        int insCnt = DunsumObjectUtils.convertInt(rtnMap.get("INS"));
        int delCnt = DunsumObjectUtils.convertInt(rtnMap.get("DEL"));
        String msg = String.format("?????? ?????? : %d, ?????? ?????? : %d", insCnt, delCnt);
        return new BatchResultModel(BatchMgmtFactory.getSuccCode(), msg);
    }
}
