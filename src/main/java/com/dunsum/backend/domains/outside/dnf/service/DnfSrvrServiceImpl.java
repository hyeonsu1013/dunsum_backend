package com.dunsum.backend.domains.outside.dnf.service;

import com.dunsum.backend.common.dto.SystemDTO;
import com.dunsum.backend.common.utils.DunsumObjectUtils;
import com.dunsum.backend.common.utils.DunsumStringUtils;
import com.dunsum.backend.common.utils.RestUtils;
import com.dunsum.backend.domains.common.service.CommService;
import com.dunsum.backend.domains.entity.CodeEntity;
import com.dunsum.backend.domains.entity.CodeMpngEntity;
import com.dunsum.backend.domains.entity.DnfSrvrEntity;
import com.dunsum.backend.domains.outside.dnf.dao.DnfSrvrDao;
import com.dunsum.backend.domains.outside.dnf.enums.DnfEnumsFactory;
import com.dunsum.backend.domains.outside.dnf.model.DnfJobModel;
import com.dunsum.backend.domains.outside.dnf.model.DnfSrchModel;
import com.dunsum.backend.domains.outside.dnf.model.DnfSrvrModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
public class DnfSrvrServiceImpl implements DnfSrvrService {

    public static final Logger dnfLogger = LoggerFactory.getLogger(DnfSrvrService.class);

    public static final String API = "API";
    public static final String DATABASE = "Database";
    public static final String REDIS = "Redis";

    // DI 대상
    private final RestUtils restUtils;
    private final DnfSrvrDao dnfSrvrDao;
    private final CommService commService;

    @Override
    public DnfSrvrModel selServers(DnfSrchModel srchModel) throws Exception {
        DnfSrvrModel srvrModel = null;

        try {
            DnfEnumsFactory.DnfApiEnums dae = DnfEnumsFactory.DnfApiEnums.SEL_SERVERS;

            String url = DnfEnumsFactory.getApiUrl(srchModel.getAppConnDataVO(), dae);
            Map<String, Object> params = DnfEnumsFactory.getApiParams(srchModel.getAppConnDataVO(), dae);
            srvrModel = restUtils.sendRestApi(url, params, DnfSrvrModel.class, dae.getHttpMethod());
            srvrModel.setSelType(DnfEnumsFactory.getSelTypeCode(API));
        }
        // API 통신 실패 시 DB 조회
        catch (Exception e) {
            srvrModel = new DnfSrvrModel();
            srvrModel.setFailRson(API + " :: " + e.getMessage());
            srvrModel.setSelType(DATABASE);
            srvrModel.setRows(dnfSrvrDao.selDnfSrvr());
            // TOTO : 실패로그 기록
        }

        return srvrModel;
    }

    @Override
    public Map<String, Object> updServers(DnfSrchModel srchModel) throws Exception {
        DnfSrvrModel dnfSrvrModel = this.selServers(srchModel);
        Map<String, Object> rtn = new HashMap<>();

        List<DnfSrvrEntity> list = dnfSrvrModel.getRows();
        rtn.put("INS", list == null ? 0 : list.size());

        if(DunsumObjectUtils.isNotBlank(list)){
            // TODO :: 유저번호 변경
            long rgstUserNo = SystemDTO.SYS_USER_NO;
            dnfSrvrModel.setRgstUserNo(rgstUserNo);

            dnfSrvrDao.insDnfSrvr(list);
            int delCnt = dnfSrvrDao.delLogicDnfSrvr(dnfSrvrModel);
            rtn.put("DEL", delCnt);
        }

        return rtn;
    }

    @Override
    public DnfJobModel selJobs(DnfSrchModel srchModel) throws Exception {
        DnfJobModel jobModel = null;

        try {
            DnfEnumsFactory.DnfApiEnums dae = DnfEnumsFactory.DnfApiEnums.SEL_JOBS;

            String url = DnfEnumsFactory.getApiUrl(srchModel.getAppConnDataVO(), dae);
            Map<String, Object> params = DnfEnumsFactory.getApiParams(srchModel.getAppConnDataVO(), dae);
            jobModel = restUtils.sendRestApi(url, params, DnfJobModel.class, dae.getHttpMethod());
            jobModel.setSelType(DnfEnumsFactory.getSelTypeCode(API));
        }
        // API 통신 실패 시 DB 조회
        catch (Exception e) {
            jobModel = new DnfJobModel();
            jobModel.setFailRson(API + " :: " + e.getMessage());
            jobModel.setSelType(DATABASE);
//            jobModel.setRows(dnfSrvrDao.selDnfSrvr());
            // TOTO : 실패로그 기록
        }
        return jobModel;
    }

    @Override
    public Map<String, Object> updJobs(DnfSrchModel srchModel) throws Exception {
        DnfJobModel dnfJobModel = this.selJobs(srchModel);
        Map<String, Object> rtn = new HashMap<>();

        List<CodeEntity> list = new ArrayList<>();
        List<CodeMpngEntity> mpngTrgtList = new ArrayList<>();
        this.makeJobCodeList(list, mpngTrgtList, dnfJobModel, null);

        mpngTrgtList.forEach(System.out::println);

        rtn.put("INS_CODE", list.size());
        rtn.put("INS_MPNG", mpngTrgtList.size());

        if(DunsumObjectUtils.isNotBlank(list)){
            commService.insCode(list);

            int delCnt = commService.delLogicCode(list);
            rtn.put("DEL_CODE", delCnt);
        }

        if(DunsumObjectUtils.isNotBlank(mpngTrgtList)){
            commService.insCodeMpng(mpngTrgtList);

            int delCnt = commService.delLogicCodeMpng(mpngTrgtList);
            rtn.put("DEL_MPNG", delCnt);
        }

        return rtn;
    }
    private void makeJobCodeList(List<CodeEntity> list, List<CodeMpngEntity> mpngTrgtList, DnfJobModel job, DnfJobModel parent) {
        String CODE_INI = "DNF_JOB";
        String CODE_INI_NAME = "DNF 직업";

        if(DunsumStringUtils.isNotBlank(job.getJobId())){
            CodeEntity code = CodeEntity.builder()
                                .codeIni(CODE_INI)
                        .codeIniName(CODE_INI_NAME)
                        .codeId(job.getJobId())
                        .codeIdName(job.getJobName())
                        .build();
            code.setRgstUserNo(SystemDTO.SYS_USER_NO);
            list.add(code);
        }

        if(DunsumObjectUtils.isNotBlank(job.getRows())){
            job.getRows().forEach(el ->
                    this.makeJobCodeList(list, mpngTrgtList, el, job)
            );
        }

        if(job.getNext() != null){
            DnfJobModel grow = new DnfJobModel();
            grow.setJobId(parent.getJobId());
            grow.setJobName(parent.getJobName());
            grow.setNext(job);
            mpngTrgtList.addAll(this.convert2CodeMpng(grow));
        }
    }

    private List<CodeMpngEntity> convert2CodeMpng(DnfJobModel grow) {
        List<CodeMpngEntity> list = new ArrayList<>();
        String MPNG_INI = "DNF_JOB_GROW";
        String MPNG_INI_NAME = "DNF 전직";
        String MPNG_GRUP = grow.getJobId();
        String MPNG_GRUP_NAME = grow.getJobName();

        DnfJobModel next = grow.getNext();
        String MPNG_ID = next.getJobGrowId();
        String MPNG_ID_NAME = next.getJobGrowName();
        int dispOrder = 1;

        do {
            next = next.getNext();
            dispOrder = next == null ? 0 : dispOrder;

            CodeMpngEntity mpng = CodeMpngEntity.builder()
                                    .mpngIni(MPNG_INI)
                                    .mpngIniName(MPNG_INI_NAME)
                                    .mpngGrup(MPNG_GRUP)
                                    .mpngGrupName(MPNG_GRUP_NAME)
                                    .mpngId(MPNG_ID)
                                    .mpngIdName(MPNG_ID_NAME)
                                    .mpngVal(next == null ? "" : next.getJobGrowId())
                                    .mpngValName(next == null ? "" : next.getJobGrowName())
                                    .mpngDesc(String.format("%s-%s-전직", MPNG_GRUP_NAME, MPNG_ID_NAME))
                                    .dispOder(dispOrder++)
                                    .build();
            list.add(mpng);
        } while (next != null);

        return list;
    }
}
