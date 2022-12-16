package com.dunsum.backend.domains.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "시스템 접근 로그 테이블")
public class SstmLogEntity extends BaseVO {

    @ApiModelProperty(value = "게스트 SEQ")
    private long sstmLogSeq;

    @ApiModelProperty(value = "접근 IP")
    private String acptIp;

    @ApiModelProperty(value = "접근 URL")
    private String acptUrl;

}