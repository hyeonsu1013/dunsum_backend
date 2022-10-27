package com.dunsum.backend.domains.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "GUEST 사용자 테이블")
public class UserGustEntity extends BaseVO {

    @ApiModelProperty(value = "게스트 SEQ")
    private long gustSeq;

    @ApiModelProperty(value = "사용인번호")
    private long userNo;

    @ApiModelProperty(value = "접속 IP")
    private String clntIp;

    @ApiModelProperty(value = "DB상태")
    private String dbStat;

    @ApiModelProperty(value = "최종접속일")
    private Timestamp lastLginDate;

}