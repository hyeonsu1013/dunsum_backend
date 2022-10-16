package com.dunsum.backend.outside.dnf.dao;

import com.dunsum.backend.entity.DnfSrvrEntity;
import com.dunsum.backend.outside.dnf.model.DnfSrvrModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DnfSrvrDao {

    /**
     *
     */
    List<DnfSrvrEntity> selDnfSrvr() throws Exception;

    /**
     * 던파 서버 목록 저장
     */
    void insDnfSrvr(List<DnfSrvrEntity> list) throws Exception;

    /**
     * 던파 서버 논리적 삭제
     */
    int delLogicDnfSrvr(DnfSrvrModel dnfSrvrModel) throws Exception;

}
