package com.dunsum.backend.batch.model;

import com.dunsum.backend.batch.enums.BatchMgmtFactory;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchResultModel {

    public BatchResultModel(String resultCode, String msg) {
        super();
        this.resultCode = resultCode;
        if(BatchMgmtFactory.getSuccCode().equals(resultCode)){
            this.succMsg = msg;
        } else if(BatchMgmtFactory.getPartCode().equals(resultCode)){
            this.partMsg = msg;
        } else if(BatchMgmtFactory.getFailCode().equals(resultCode)){
            this.failMsg = msg;
        }
    }

    @ApiModelProperty(value="결과코드")
    private String resultCode;

    @ApiModelProperty(value="결과메시지_성공")
    private String succMsg;

    @ApiModelProperty(value="결과메시지_부분성공")
    private String partMsg;

    @ApiModelProperty(value="결과메시지_실패")
    private String failMsg;
}
