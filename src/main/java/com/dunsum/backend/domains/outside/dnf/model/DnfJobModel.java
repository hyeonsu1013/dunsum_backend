package com.dunsum.backend.domains.outside.dnf.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DnfJobModel extends DnfModel {

    @ApiModelProperty(value="직업 ID")
    private String jobId;

    @ApiModelProperty(value="직업명")
    private String jobName;

    @ApiModelProperty(value="전직 ID")
    private String jobGrowId;

    @ApiModelProperty(value="전직명")
    private String jobGrowName;

    @ApiModelProperty(value="전직")
    private DnfJobModel next;

    @ApiModelProperty(value="직업 목록")
    private List<DnfJobModel> rows;

}
