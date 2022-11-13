package com.dunsum.backend.domains.account.service;

import com.dunsum.backend.common.security.jwt.JwtProvider;
import com.dunsum.backend.common.security.model.TokenUserModel;
import com.dunsum.backend.common.utils.DunsumObjectUtils;
import com.dunsum.backend.common.utils.RandomUtils;
import com.dunsum.backend.domains.account.dao.AcutMgmtDao;
import com.dunsum.backend.domains.account.model.AccountDataModel;
import com.dunsum.backend.domains.account.model.UserSrchModel;
import com.dunsum.backend.domains.entity.UserEntity;
import com.dunsum.backend.domains.entity.UserGustEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
public class AcutMgmtServiceImpl implements AcutMgmtService {

    private final JwtProvider jwtProvider;

    private final AcutMgmtDao acutMgmtDao;

    @Override
    public TokenUserModel insGust(UserGustEntity entt) throws Exception {
        UserEntity rtnUser = null;
        UserSrchModel srchModel = new UserSrchModel();
        srchModel.setClntIp(entt.getClntIp());

        // 접속 IP로 조회
        UserGustEntity orgUserGust = acutMgmtDao.getUserGust(srchModel);

        // 기등록된 게스트인 경우
        if(orgUserGust != null){
            acutMgmtDao.updLastLgin(orgUserGust.getGustSeq());
            srchModel.setUserNo(orgUserGust.getUserNo());
        }
        // 신규 게스트 등록
        else {
            // 랜덤 게스트 아이디 생성 - 중복되지 않도록 생성
            UserEntity userEntt = null;
            String guestId = "";

            do {
                guestId = AccountDataModel.GUEST_PRE_ID + RandomUtils.getRndmPswd(8);
                srchModel.setLginId(guestId);
                userEntt = acutMgmtDao.getUser(srchModel);
            } while (userEntt != null);

            UserEntity userInsEntt = new UserEntity();
            userInsEntt.setGustYn(AccountDataModel.GUEST_O);
            userInsEntt.setLginId(guestId);

            // User 등록
            acutMgmtDao.insUser(userInsEntt);

            long userNo = userInsEntt.getUserNo();
            entt.setUserNo(userNo);
            // User Guest 등록
            acutMgmtDao.insUserGust(entt);
            // 등록된 User 조회
            srchModel.setUserNo(entt.getUserNo());
        }

        rtnUser = acutMgmtDao.getUser(srchModel);

        TokenUserModel tokenUserModel = DunsumObjectUtils.getCloneObject(TokenUserModel.class, rtnUser, true);

        if(tokenUserModel != null){
            String userToken = jwtProvider.generateAccessToken(tokenUserModel);
            tokenUserModel.setUserToken(userToken);
            tokenUserModel.setClntIp(entt.getClntIp());
        }

        return tokenUserModel;
    }

    @Override
    public UserEntity insUser(UserEntity entt) throws Exception {
        return null;
    }

    @Override
    public UserEntity getUser(UserEntity entt) throws Exception {
        return null;
    }
}
