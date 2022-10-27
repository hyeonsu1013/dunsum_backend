package com.dunsum.backend.common.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnVO {

    public ColumnVO(ResultSet resultSet) throws Exception {
        this();
        this.tableName = resultSet.getString("TABLE_NAME");
        this.columnName = resultSet.getString("COLUMN_NAME");
        this.dataType = resultSet.getString("DATA_TYPE");
        this.columnType = resultSet.getString("COLUMN_TYPE");
        this.columnComment = resultSet.getString("COLUMN_COMMENT");
        this.ordinalPosition = resultSet.getString("ORDINAL_POSITION");
    }

    @ApiModelProperty(value="테이블명")
    private String tableName;

    @ApiModelProperty(value="컬럼명")
    private String columnName;

    @ApiModelProperty(value="데이터타입")
    private String dataType;

    @ApiModelProperty(value="컬럼타입")
    private String columnType;

    @ApiModelProperty(value="컬럼설명")
    private String columnComment;

    @ApiModelProperty(value="컬럼순서")
    private String ordinalPosition;
}
