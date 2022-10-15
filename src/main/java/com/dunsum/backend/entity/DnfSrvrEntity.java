package com.dunsum.backend.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "DNF 서버목록")
public class DnfSrvrEntity extends BaseVO {

    @ApiModelProperty(value = "서버 ID")
    private String serverId;

    @ApiModelProperty(value = "서버명")
    private String serverName;

    @ApiModelProperty(value = "DB상태")
    private String dbStat;

}