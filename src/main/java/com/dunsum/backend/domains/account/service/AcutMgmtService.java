package com.dunsum.backend.domains.account.service;

import com.dunsum.backend.domains.entity.UserEntity;
import com.dunsum.backend.domains.entity.UserGustEntity;

public interface AcutMgmtService {

    /**
     * Guest 로그인 - 이미 계정이 존재하는 경우 get, 아닌 경우 ins
     */
    public UserEntity insGust(UserGustEntity entt) throws Exception;

    /**
     * User 회원가입
     */
    public UserEntity insUser(UserEntity entt) throws Exception;

    /**
     * User 정보조회
     */
    public UserEntity getUser(UserEntity entt) throws Exception;

}
