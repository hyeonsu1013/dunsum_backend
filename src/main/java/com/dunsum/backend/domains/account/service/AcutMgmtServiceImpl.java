package com.dunsum.backend.domains.account.service;

import com.dunsum.backend.common.utils.RandomUtils;
import com.dunsum.backend.domains.account.dao.AcutMgmtDao;
import com.dunsum.backend.domains.entity.UserEntity;
import com.dunsum.backend.domains.entity.UserGustEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcutMgmtServiceImpl implements AcutMgmtService {

    private final AcutMgmtDao acutMgmtDao;

    @Override
    public UserEntity insGust(UserGustEntity entt) throws Exception {
        UserEntity rtnUser = null;

        UserGustEntity orgUserGust = acutMgmtDao.getUserGust(entt);

        // 기등록된 게스트인 경우
        if(orgUserGust != null){
            UserEntity userSrchEntt = new UserEntity();
            userSrchEntt.setUserNo(orgUserGust.getUserNo());
            rtnUser = acutMgmtDao.getUser(userSrchEntt);
        }
        // 신규 게스트 등록
        else {
            // 랜덤 게스트 아이디 생성
            String guestId = "GUEST_#" + RandomUtils.getRndmPswd(8);
            UserEntity userInsEntt = new UserEntity();
            userInsEntt.setGustYn("Y");
            userInsEntt.setLginId(guestId);

            // User 등록
            acutMgmtDao.insUser(userInsEntt);

            long userNo = userInsEntt.getUserNo();
            entt.setUserNo(userNo);
            // User Guest 등록
            acutMgmtDao.insUserGust(entt);
            // 등록된 User 조회
            rtnUser = acutMgmtDao.getUser(userInsEntt);
        }

        return rtnUser;
    }
}
