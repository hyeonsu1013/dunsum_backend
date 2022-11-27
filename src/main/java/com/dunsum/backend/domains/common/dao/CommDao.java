package com.dunsum.backend.domains.common.dao;

import com.dunsum.backend.domains.entity.CodeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommDao {

    /**
     * 코드 조회
     * @param entt
     * @return
     * @throws Exception
     */
    List<CodeEntity> selCode(CodeEntity entt) throws Exception;

    /**
     * 코드 등록
     * @param list
     * @return
     * @throws Exception
     */
    int insCode(List<CodeEntity> list) throws Exception;

    /**
     * 코드 논리적 삭제 - useYn = 'N'
     * @param list
     * @return
     * @throws Exception
     */
    int delLogicCode(List<CodeEntity> list) throws Exception;

}
