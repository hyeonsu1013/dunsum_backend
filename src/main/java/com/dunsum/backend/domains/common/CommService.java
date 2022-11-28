package com.dunsum.backend.domains.common;

import com.dunsum.backend.domains.common.dao.CommDao;
import com.dunsum.backend.domains.entity.CodeEntity;
import com.dunsum.backend.domains.entity.CodeMpngEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
public class CommService {

    private final CommDao commDao;

    // 코드 조회
    public List<CodeEntity> selCode(CodeEntity entt) throws Exception {
        return commDao.selCode(entt);
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
        return commDao.selCodeMpng(entt);
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
