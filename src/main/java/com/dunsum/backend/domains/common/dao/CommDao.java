package com.dunsum.backend.domains.common.dao;

import com.dunsum.backend.domains.entity.CodeEntity;
import com.dunsum.backend.domains.entity.CodeMpngEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommDao {

    /**
     * 코드 조회
     */
    List<CodeEntity> selCode(CodeEntity entt) throws Exception;

    /**
     * 코드 조회
     */
    List<CodeEntity> selCode(List<String> codeInis) throws Exception;

    /**
     * 코드 등록
     */
    int insCode(List<CodeEntity> list) throws Exception;

    /**
     * 코드 논리적 삭제 - useYn = 'N'
     */
    int delLogicCode(List<CodeEntity> list) throws Exception;

    /**
     * 매핑코드 조회
     */
    List<CodeMpngEntity> selCodeMpng(CodeMpngEntity entt) throws Exception;

    /**
     * 매핑코드 등록
     */
    int insCodeMpng(List<CodeMpngEntity> list) throws Exception;

    /**
     * 매핑코드 논리적 삭제 - useYn = 'N'
     */
    int delLogicCodeMpng(List<CodeMpngEntity> list) throws Exception;

}
