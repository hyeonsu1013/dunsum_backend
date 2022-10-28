package com.dunsum.backend.domains.account.dao;

import com.dunsum.backend.domains.account.model.UserSrchModel;
import com.dunsum.backend.domains.entity.UserEntity;
import com.dunsum.backend.domains.entity.UserGustEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcutMgmtDao {

    // UserGuestEntity 조회
    public UserGustEntity getUserGust(UserSrchModel userSrchModel) throws Exception;

    // UserGuestEntity 등록
    public void insUserGust(UserGustEntity userGustEntity) throws Exception;

    // UserEntity 조회
    public UserEntity getUser(UserSrchModel userSrchModel) throws Exception;

    // UserEntity 등록
    public void insUser(UserEntity userEntity) throws Exception;

}
