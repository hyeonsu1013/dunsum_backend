package com.dunsum.backend.common.security.model;

import com.dunsum.backend.domains.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenUserModel extends UserEntity {

    @ApiModelProperty(value = "권한")
    private String auth;

    @ApiModelProperty(value = "권한명")
    private String authNm;

    @JsonIgnore
    private List<String> roles;

}
