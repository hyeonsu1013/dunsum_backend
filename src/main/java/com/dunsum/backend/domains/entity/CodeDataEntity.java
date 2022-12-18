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
@ApiModel(description = "JV 데이터")
public class CodeDataEntity extends BaseVO {

    @ApiModelProperty(value = "데이터 ID")
    private String dataId;

    @ApiModelProperty(value = "데이터 ID명")
    private String dataIdName;

    @ApiModelProperty(value = "데이터 VALUE")
    private String dataVal;

    @ApiModelProperty(value = "데이터 VALUE명")
    private String dataValName;

    @ApiModelProperty(value = "데이터 설명")
    private String dataDesc;

}