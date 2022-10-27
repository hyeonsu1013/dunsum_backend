package com.dunsum.backend.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableVO {

    public TableVO(ResultSet resultSet) throws Exception {
        this();
        this.tableName = resultSet.getString("TABLE_NAME");
        this.tableComment = resultSet.getString("TABLE_COMMENT");
    }

    @ApiModelProperty(value="테이블이름")
    private String tableName;

    @ApiModelProperty(value="테이블설명")
    private String tableComment;

    @ApiModelProperty(value="컬럼데이터")
    private List<ColumnVO> colList;
}
