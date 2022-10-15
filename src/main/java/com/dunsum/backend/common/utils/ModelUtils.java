package com.dunsum.backend.common.utils;

import com.dunsum.backend.common.vo.environment.AppDataSourceVO;
import com.dunsum.backend.common.vo.ColumnVO;
import com.dunsum.backend.common.vo.TableVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * ModelUtils.java
 * @author javayaji
 */
@Component
@RequiredArgsConstructor
public class ModelUtils {

    private final AppDataSourceVO appDataSourceVO;

    private static final String dbSchema = "dunsum"; // DB 스키마
    private static final String packagePath = "com.dunsum.backend.entity"; // 패키지 경로
    private static final String filePath = "D:\\real_project\\dunsum\\backend\\src\\main\\java\\com\\dunsum\\backend\\entity\\"; // 파일 저장 경로
    private static final String[] baseColumns = new String[]{"RGST_USER_NO", "MDFT_USER_NO", "RGST_DATE", "MDFT_DATE"};

    private static final String tablePrefix = "jv_";

    /*********************
     *** static Method ***
     *********************/

    // DB 데이터 타입에 따른 Model Field Class 설정
    public static String getDataType(String dbDataType) {
        String dataType;

        switch (dbDataType) {
            case "int":
                dataType = "long";
                break;
            case "bigint":
                dataType = "BigInteger";
                break;
            case "tinyint":
            case "smallint":
                dataType = "int";
                break;
            case "float":
            case "decimal":
                dataType = "BigDecimal";
                break;
            case "datetime":
            case "timestamp":
                dataType = "Timestamp";
                break;
            default:
                dataType = "String";
                break;
        }
        return dataType;
    }



    /***********************
     *** instance Method ***
     ***********************/

    // DB 데이터를 바탕으로 Entity 자동 생성
    public void createEntity() throws Exception {

        List<TableVO> tableList = this.getTableList();

        if(CollectionUtils.isEmpty(tableList)) {
            throw new Exception("DB 정보 조회 실패");
        }

        this.setColumnData(tableList);

        for(TableVO tableVO : tableList) {
            this.createTableToFile(tableVO);
        }
    }



    /**********************
     *** private Method ***
     **********************/

    // Entity File 생성
    private void createTableToFile(TableVO tVO) throws Exception{
        String fileName = DunsumStringUtils.getCamelStr(DunsumStringUtils.RemovePrefix(tVO.getTableName(), tablePrefix), true) + "Entity.java";
        File file = new File(filePath + fileName);
        String fileContents = getFileContents(tVO);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(fileContents);
            writer.close();
        } catch (IOException e) {
            System.out.println("tableToModelFileCreate Error : ["+ tVO.getTableName() +"] " + e);
        }
    }

    // File Contents 작성
    private String getFileContents(TableVO tVO) {
        StringBuilder sb = new StringBuilder();
        List<String> importList = new ArrayList<>();

        List<ColumnVO> colList = tVO.getColList();
        Optional<ColumnVO> timestampList = colList.stream()
                                            .filter(e ->
                                                    "datetime,timestamp".contains(e.getDataType())
                                                    && !Arrays.asList(baseColumns).contains(e.getColumnName())
                                                    )
                                            .findFirst();
        boolean hasTimestamp = timestampList.isPresent();

        importList.add("import com.dunsum.backend.common.vo.BaseVO;");
        importList.add("import io.swagger.annotations.ApiModel;");
        importList.add("import io.swagger.annotations.ApiModelProperty;");
        importList.add("import lombok.Data;");
        importList.add("import lombok.EqualsAndHashCode;");

        if(hasTimestamp){
            importList.add("\r\nimport java.sql.Timestamp;");
        }

        List<ColumnVO> sortColList = colList.stream()
                .sorted((e1, e2) -> Integer.compare(Integer.parseInt(e1.getOrdinalPosition())
                        , Integer.parseInt(e2.getOrdinalPosition())))
                .collect(Collectors.toList());

        sb.append("package ");
        sb.append(packagePath);
        sb.append(';');
        sb.append("\r\n\r\n");
        sb.append(String.join("\r\n", importList));
        sb.append("\r\n\r\n");

        sb.append("@Data");
        sb.append("\r\n");
        sb.append("@EqualsAndHashCode(callSuper=false)");
        sb.append("\r\n");
        sb.append("@ApiModel(description = \"");
        sb.append(tVO.getTableComment());
        sb.append("\")");
        sb.append("\r\n");
        sb.append("public class ");
        sb.append(DunsumStringUtils.getCamelStr(DunsumStringUtils.RemovePrefix(tVO.getTableName(), tablePrefix), true));
        sb.append("Entity extends BaseVO {");
        sb.append("\r\n\r\n");

        // Field 생성
        for(ColumnVO cVO : sortColList) {

            if(Arrays.asList(baseColumns).contains(cVO.getColumnName())) continue;

            sb.append("    @ApiModelProperty(value = \"");
            sb.append(cVO.getColumnComment());
            sb.append("\")");
            sb.append("\r\n");
            sb.append("    private ");
            sb.append(getDataType(cVO.getDataType()));
            sb.append(' ');
            sb.append(DunsumStringUtils.getCamelStr(cVO.getColumnName(), false));
            sb.append(';');
            sb.append("\r\n\r\n");
        }

        sb.append("}");

        return sb.toString();
    }

    // column List 내에 파라미터의 column 들어있는지 체크
    private boolean chkColContain(List<ColumnVO> list, String... colNames) {
        for(String colName : colNames) {
            for(ColumnVO columnVO : list) {
                if(colName.equals(columnVO.getColumnName())) {
                    return true;
                }
            }
        }

        return false;
    }

    // DB Table 조회
    private List<TableVO> getTableList() {
        StringBuilder sb = new StringBuilder();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TableVO tableInfo = null;

        List<TableVO> tableList = new ArrayList<>();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Query
            sb.append("SELECT TABLE_NAME, TABLE_COMMENT");
            sb.append("  FROM information_schema.TABLES");
            sb.append(" WHERE TABLE_SCHEMA = '");
            sb.append(dbSchema);
            sb.append("'");

            conn = DriverManager.getConnection(appDataSourceVO.getUrl(), appDataSourceVO.getUsername(), appDataSourceVO.getPassword());
            stmt = conn.prepareStatement(sb.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {
                tableInfo = new TableVO(rs);
                tableList.add(tableInfo);
            }

            if(!rs.isClosed()) rs.close();
            if(!stmt.isClosed()) stmt.close();
            if(!conn.isClosed()) conn.close();
        }catch (SQLException e) {
            System.err.format("Query Error: %s\n%s", e.getSQLState(), e.getMessage());
        }catch (Exception e) {
            System.out.println("Error : " + e);
        }
        return tableList;
    }

    // Table Column 조회
    private void setColumnData(List<TableVO> tableList) {
        StringBuilder sb = new StringBuilder();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ColumnVO colInfo = null;

        Map<String, List<ColumnVO>> tableMap = new HashMap<>();
        for(TableVO tVO : tableList){
            tableMap.put(tVO.getTableName(), new ArrayList<>());
        }

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Query
            sb.append("SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_TYPE, COLUMN_COMMENT, ORDINAL_POSITION");
            sb.append("  FROM information_schema.columns");
            sb.append(" WHERE TABLE_SCHEMA = '");
            sb.append(dbSchema);
            sb.append("'");
            // 테이블 목록
            sb.append("   AND TABLE_NAME IN ('");
            sb.append(String.join("','", tableMap.keySet()));
            sb.append("')");

            conn = DriverManager.getConnection(appDataSourceVO.getUrl(), appDataSourceVO.getUsername(), appDataSourceVO.getPassword());
            stmt = conn.prepareStatement(sb.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {
                colInfo = new ColumnVO(rs);
                tableMap.get(colInfo.getTableName()).add(colInfo);
            }

            for(TableVO tVO : tableList){
                tVO.setColList(tableMap.get(tVO.getTableName()));
            }

            if(!rs.isClosed()) rs.close();
            if(!stmt.isClosed()) stmt.close();
            if(!conn.isClosed()) conn.close();
        }catch (SQLException e) {
            System.err.format("Query Error: %s\n%s", e.getSQLState(), e.getMessage());
        }catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }
}