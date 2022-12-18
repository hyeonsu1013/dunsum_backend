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
@ApiModel(description = "DNF 유저별 캐릭터 목록")
public class DnfCharEntity extends BaseVO {

    @ApiModelProperty(value = "사용인번호")
    private long userNo;

    @ApiModelProperty(value = "서버 ID")
    private String serverId;

    @ApiModelProperty(value = "캐릭터 ID")
    private String charId;

    @ApiModelProperty(value = "캐릭터명")
    private String charName;

    @ApiModelProperty(value = "레벨")
    private int charLev;

    @ApiModelProperty(value = "직업 ID")
    private String jobId;

    @ApiModelProperty(value = "전직 ID")
    private String growId;

    @ApiModelProperty(value = "DB상태")
    private String dbStat;

    @ApiModelProperty(value = "정렬순서")
    private int prntOder;

}