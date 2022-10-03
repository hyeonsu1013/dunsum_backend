package com.dunsum.backend.dashbord.service;

import com.dunsum.backend.dashbord.model.DashbordSrchModel;

import java.util.List;

public interface DashbordService {

    public List<Object> sel(DashbordSrchModel srchModel) throws Exception;

    public Integer getBool(DashbordSrchModel srchModel) throws Exception;

}
