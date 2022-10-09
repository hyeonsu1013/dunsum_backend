package com.dunsum.backend.outside.dnf.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DnfSrvrModel extends DnfModel {

    @ApiModelProperty(value="서버ID")
    String serverId;

    @ApiModelProperty(value="서버명")
    String serverName;

}
