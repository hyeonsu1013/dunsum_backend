package com.dunsum.backend.outside.dnf.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnfModel {

    @ApiModelProperty(value="서버통신시간")
    long timeSeconds;

}
