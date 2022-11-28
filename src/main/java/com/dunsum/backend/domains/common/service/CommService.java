package com.dunsum.backend.domains.common.service;

import com.dunsum.backend.common.utils.DunsumObjectUtils;
import com.dunsum.backend.domains.common.dao.CommDao;
import com.dunsum.backend.domains.entity.CodeEntity;
import com.dunsum.backend.domains.entity.CodeMpngEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
public class CommService {

    private final CommDao commDao;

    // 코드 조회
    public List<CodeEntity> selCode(CodeEntity entt) throws Exception {
        return DunsumObjectUtils.wrapping(commDao.selCode(entt));
    }

    // 코드 조회
    public Map<String, List<CodeEntity>> selCode(List<String> codeInis) throws Exception {
        List<CodeEntity> codes = DunsumObjectUtils.wrapping(commDao.selCodeFromIni(codeInis));
        Map<String, List<CodeEntity>> mapping = codes.stream().collect(Collectors.groupingBy(CodeEntity::getCodeIni));
        Map<String, List<CodeEntity>> rtnMap = new HashMap<>();
        for(String key : codeInis){
            rtnMap.put(key, DunsumObjectUtils.wrapping(mapping.get(key)));
        }
        return rtnMap;
    }

    // 코드 등록
    public int insCode(List<CodeEntity> list) throws Exception {
        return commDao.insCode(list);
    }

    // 코드 논리적 삭제
    public int delLogicCode(List<CodeEntity> list) throws Exception {
        return commDao.delLogicCode(list);
    }

    // 매핑코드 조회
    public List<CodeMpngEntity> selCodeMpng(CodeMpngEntity entt) throws Exception {
        return DunsumObjectUtils.wrapping(commDao.selCodeMpng(entt));
    }

    // 매핑코드 등록
    public int insCodeMpng(List<CodeMpngEntity> list) throws Exception {
        return commDao.insCodeMpng(list);
    }

    // 매핑코드 논리적 삭제
    public int delLogicCodeMpng(List<CodeMpngEntity> list) throws Exception {
        return commDao.delLogicCodeMpng(list);
    }

}
