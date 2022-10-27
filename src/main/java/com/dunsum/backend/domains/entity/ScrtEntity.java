package com.dunsum.backend.domains.entity;

import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "암호화 데이터")
public class ScrtEntity extends BaseVO {

    @ApiModelProperty(value = "ID")
    private String userId;

    @ApiModelProperty(value = "암호화 타입")
    private String scrtType;

    @ApiModelProperty(value = "인코딩 모드")
    private String encMode;

    @ApiModelProperty(value = "인코딩 타입")
    private String encType;

    @ApiModelProperty(value = "암호화 키")
    private String scrtKey;

    @ApiModelProperty(value = "초기화 값")
    private String initVal;

}