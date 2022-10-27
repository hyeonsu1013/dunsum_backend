package com.dunsum.backend.common.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseVO {

    @ApiModelProperty(value="등록자")
    private long rgstUserNo = 1L;

    @ApiModelProperty(value="등록일")
    private String rgstDate;

    @ApiModelProperty(value="수정자")
    private long mdftUserNo = 1L;

    @ApiModelProperty(value="수정일")
    private String mdftDate;

    public boolean isEmpty() {
        return true;
    }
}
