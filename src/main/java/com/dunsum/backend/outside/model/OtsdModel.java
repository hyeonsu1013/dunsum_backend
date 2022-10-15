package com.dunsum.backend.outside.model;

import com.dunsum.backend.common.vo.BaseVO;
import com.dunsum.backend.common.vo.environment.AppConnDataVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OtsdModel extends BaseVO {

    @ApiModelProperty(value="API 통신 Data")
    AppConnDataVO appConnDataVO;

}
