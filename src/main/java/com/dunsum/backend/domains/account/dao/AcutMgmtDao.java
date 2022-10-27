package com.dunsum.backend.domains.account.dao;

import com.dunsum.backend.domains.entity.UserEntity;
import com.dunsum.backend.domains.entity.UserGustEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcutMgmtDao {

    // UserGuestEntity 조회
    public UserGustEntity getUserGust(UserGustEntity userGustEntity) throws Exception;

    // UserGuestEntity 등록
    public void insUserGust(UserGustEntity userGustEntity) throws Exception;

    // UserGuestEntity 조회
    public UserEntity getUser(UserEntity userEntity) throws Exception;

    // UserEntity 등록
    public void insUser(UserEntity userEntity) throws Exception;

}
