package com.dunsum.backend.outside.dnf.service;

import com.dunsum.backend.common.dto.SystemDTO;
import com.dunsum.backend.common.utils.DunsumObjectUtils;
import com.dunsum.backend.common.utils.RestUtils;
import com.dunsum.backend.entity.DnfSrvrEntity;
import com.dunsum.backend.outside.dnf.dao.DnfSrvrDao;
import com.dunsum.backend.outside.dnf.enums.DnfEnumsFactory;
import com.dunsum.backend.outside.dnf.model.DnfSrchModel;
import com.dunsum.backend.outside.dnf.model.DnfSrvrModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if(!DunsumObjectUtils.isBlank(list)){
            // TODO :: 유저번호 변경
            long rgstUserNo = SystemDTO.SYS_USER_NO;
            dnfSrvrModel.setRgstUserNo(rgstUserNo);

            dnfSrvrDao.insDnfSrvr(list);
            int delCnt = dnfSrvrDao.delLogicDnfSrvr(dnfSrvrModel);
            rtn.put("DEL", delCnt);
        }

        return rtn;
    }

}
