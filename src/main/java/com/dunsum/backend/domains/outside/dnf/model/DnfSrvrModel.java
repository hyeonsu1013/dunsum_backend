package com.dunsum.backend.domains.outside.dnf.model;

import com.dunsum.backend.domains.entity.DnfSrvrEntity;
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
public class DnfSrvrModel extends DnfModel {

    @ApiModelProperty(value="서버 목록")
    List<DnfSrvrEntity> rows;

}
