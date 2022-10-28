package com.dunsum.backend.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SrchVO {

    @ApiModelProperty(value="조회자")
    private long srchUserNo;

    @ApiModelProperty(value="조회권한")
    private long srchAuth;

}
