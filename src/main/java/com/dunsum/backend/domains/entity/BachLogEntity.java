package com.dunsum.backend.domains.entity;

import com.dunsum.backend.common.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "배치로그관리")
public class BachLogEntity extends BaseVO {

    @ApiModelProperty(value = "LOG순번")
    private long bachSeq;

    @ApiModelProperty(value = "LOG 구분")
    private String bachMgmtId;

    @ApiModelProperty(value = "시작일")
    private Timestamp stDate;

    @ApiModelProperty(value = "종료일")
    private Timestamp edDate;

    @ApiModelProperty(value = "에러일")
    private Timestamp errDate;

    @ApiModelProperty(value = "실행 타입")
    private String runType;

    @ApiModelProperty(value = "실행 시간")
    private String runTime;

    @ApiModelProperty(value = "메시지")
    private String msg;

    @ApiModelProperty(value = "보조 메시지")
    private String subMsg;

}