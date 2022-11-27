package com.dunsum.backend.domains.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "JV 매핑코드")
public class CodeMpngEntity extends BaseVO {

    @ApiModelProperty(value = "매핑 이니셜")
    private String mpngIni;

    @ApiModelProperty(value = "매핑 이니셜명")
    private String mpngIniName;

    @ApiModelProperty(value = "매핑 그룹")
    private String mpngGrup;

    @ApiModelProperty(value = "매핑 그룹명")
    private String mpngGrupName;

    @ApiModelProperty(value = "매핑 ID")
    private String mpngId;

    @ApiModelProperty(value = "매핑 ID명")
    private String mpngIdName;

    @ApiModelProperty(value = "매핑값")
    private String mpngVal;

    @ApiModelProperty(value = "매핑명")
    private String mpngValName;

    @ApiModelProperty(value = "사용여부")
    private String useYn;

    @ApiModelProperty(value = "노출여부")
    private String dispYn;

    @ApiModelProperty(value = "정렬순서")
    private int dispOder;

    @ApiModelProperty(value = "코드 설명")
    private String mpngDesc;

}