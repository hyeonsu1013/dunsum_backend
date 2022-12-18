package com.dunsum.backend.domains.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "사용인별 캐릭터 등록")
public class UserCharEntity extends BaseVO {

    @ApiModelProperty(value = "사용인번호")
    private long userNo;

    @ApiModelProperty(value = "서버 ID")
    private String srvrId;

    @ApiModelProperty(value = "캐릭터 ID")
    private String charId;

    @ApiModelProperty(value = "등록실패횟수")
    private int failCnt;

    @ApiModelProperty(value = "등록실패일자")
    private String failDate;

    @ApiModelProperty(value = "DB 상태")
    private String dbStat;

}