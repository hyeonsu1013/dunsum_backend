package com.dunsum.backend.domains.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "JV 공통코드")
public class CodeEntity extends BaseVO {

    @ApiModelProperty(value = "코드 이니셜")
    private String codeIni;

    @ApiModelProperty(value = "코드 이니셜명")
    private String codeIniName;

    @ApiModelProperty(value = "코드 ID")
    private String codeId;

    @ApiModelProperty(value = "코드 ID명")
    private String codeIdName;

    @ApiModelProperty(value = "상위코드 이니셜")
    private String prntIni;

    @ApiModelProperty(value = "상위코드 ID")
    private String prntId;

    @ApiModelProperty(value = "사용여부")
    private String useYn;

    @ApiModelProperty(value = "노출여부")
    private String dispYn;

    @ApiModelProperty(value = "정렬순서")
    private int dispOder;

    @ApiModelProperty(value = "코드 설명")
    private String codeDesc;

}