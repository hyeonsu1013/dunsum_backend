package com.dunsum.backend.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "DNF 서버목록")
public class UserEntity extends BaseVO {

    @ApiModelProperty(value = "사용인번호")
    private long userNo;

    @ApiModelProperty(value = "로그인ID")
    private String lginId;

    @ApiModelProperty(value = "비밀번호")
    private String webPswd;

    @ApiModelProperty(value = "개인이메일")
    private String idvlEmal;

    @ApiModelProperty(value = "성별")
    private String gndr;

    @ApiModelProperty(value = "DB상태")
    private String dbStat;

    @ApiModelProperty(value = "비밀번호변경일")
    private Timestamp pswdChngDate;

}