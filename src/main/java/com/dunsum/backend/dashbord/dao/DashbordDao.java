package com.dunsum.backend.dashbord.dao;

import com.dunsum.backend.dashbord.model.DashbordSrchModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashbordDao {

    List<Object> sel(DashbordSrchModel srchModel) throws Exception;

    Integer getBool(DashbordSrchModel srchModel) throws Exception;
}
