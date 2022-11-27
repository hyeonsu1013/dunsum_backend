package com.dunsum.backend.domains.outside.dnf.service;

import com.dunsum.backend.domains.outside.dnf.model.DnfJobModel;
import com.dunsum.backend.domains.outside.dnf.model.DnfSrchModel;
import com.dunsum.backend.domains.outside.dnf.model.DnfSrvrModel;

import java.util.Map;

public interface DnfSrvrService {

    /**
     * DNF 서버 조회
     */
    DnfSrvrModel selServers(DnfSrchModel srchModel) throws Exception;

    /**
     * DNF 서버 업데이트
     * 신규 : 등록, 삭제 :논리적 삭제
     */
    Map<String, Object> updServers(DnfSrchModel srchModel) throws Exception;

    /**
     * DNF 직업 조회
     */
    DnfJobModel selJobs(DnfSrchModel srchModel) throws Exception;

    /**
     * DNF 서버 업데이트
     * 신규 : 등록, 삭제 :논리적 삭제
     */
    Map<String, Object> updJobs(DnfSrchModel srchModel) throws Exception;

}
