package com.dunsum.backend.domains.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "사용자 테이블")
public class UserEntity extends BaseVO {

    @ApiModelProperty(value = "사용인번호")
    private long userNo;

    @ApiModelProperty(value = "사용인별명")
    private String userNknm;

    @ApiModelProperty(value = "로그인ID")
    private String lginId;

    @ApiModelProperty(value = "비밀번호")
    private String webPswd;

    @ApiModelProperty(value = "개인이메일")
    private String idvlEmal;

    @ApiModelProperty(value = "게스트 여부")
    private String gustYn;

    @ApiModelProperty(value = "성별")
    private String gndr;

    @ApiModelProperty(value = "DB상태")
    private String dbStat;

    @ApiModelProperty(value = "비밀번호변경일")
    private String pswdChngDate;

}