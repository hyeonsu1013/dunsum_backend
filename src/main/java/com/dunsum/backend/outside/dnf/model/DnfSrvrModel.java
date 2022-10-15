package com.dunsum.backend.outside.dnf.model;

import com.dunsum.backend.common.vo.BaseVO;
import com.dunsum.backend.entity.DnfSrvrEntity;
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
public class DnfSrvrModel extends BaseVO {

    @ApiModelProperty(value="서버 목록")
    List<DnfSrvrEntity> rows;

}