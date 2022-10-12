package com.dunsum.backend.batch.dao;

import com.dunsum.backend.entity.BachLogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchLogDao {

    /**
     * 배치로그 등록
     */
    void insBachLog(BachLogEntity bachLog) throws Exception;

    /**
     * 배치로그 수정
     */
    void updBachLog(BachLogEntity bachLog) throws Exception;

    /**
     * 배치로그 조회
     */
//    BachLogEntity getBachLog(BachLogEntity bachLog) throws Exception;

    /**
     * 배치로그 삭제
     */
//    int delBachLog(int dayIntv) throws Exception;
}
