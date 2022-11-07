package com.dunsum.backend.common.security.model;

import com.dunsum.backend.domains.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenUserModel extends UserEntity implements Cloneable {

    @ApiModelProperty(value = "권한")
    private String auth;

    @ApiModelProperty(value = "권한명")
    private String authNm;

    @ApiModelProperty(value = "토큰")
    private String userToken;

    @JsonIgnore
    private List<String> roles;


    @Override
    public TokenUserModel clone() throws CloneNotSupportedException {
        return (TokenUserModel) super.clone();
    }
}
