package com.dunsum.backend.domains.outside.dnf.model;

import com.dunsum.backend.common.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DnfModel extends BaseVO {

    @ApiModelProperty(value="조회 타입")
    String selType;

    @ApiModelProperty(value="조회 실패 사유")
    String failRson;
}
