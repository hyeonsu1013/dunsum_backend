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
@ApiModel(description = "DNF 서버목록")
public class DnfSrvrEntity extends BaseVO {

    @ApiModelProperty(value = "서버 ID")
    private String serverId;

    @ApiModelProperty(value = "서버명")
    private String serverName;

    @ApiModelProperty(value = "정렬순서")
    private int prntOder;

    @ApiModelProperty(value = "DB상태")
    private String dbStat;

}