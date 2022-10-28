package com.dunsum.backend.domains.account.model;

import com.dunsum.backend.common.vo.SrchVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "USER, GUEST 조회 Model")
public class UserSrchModel extends SrchVO {

    /* USER */

    @ApiModelProperty(value = "사용인번호")
    private long userNo;

    @ApiModelProperty(value = "로그인ID")
    private String lginId;

    @ApiModelProperty(value = "개인이메일")
    private String idvlEmal;

    @ApiModelProperty(value = "게스트여부")
    private String gustYn;

    @ApiModelProperty(value = "성별")
    private String gndr;

    /* USER_GUST */

    @ApiModelProperty(value = "게스트 SEQ")
    private long gustSeq;

    @ApiModelProperty(value = "접속 IP")
    private String clntIp;

}
