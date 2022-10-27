package com.dunsum.backend.domains.dashbord.service;

import com.dunsum.backend.domains.dashbord.dao.DashbordDao;
import com.dunsum.backend.domains.dashbord.model.DashbordSrchModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashbordServiceImpl implements DashbordService {

    private final DashbordDao dashBordDao;

    @Override
    public List<Object> sel(DashbordSrchModel srchModel) throws Exception {
        List<Object> list = new ArrayList<>();

        List<Object> list2 = dashBordDao.sel(srchModel);
        list.addAll(list2);

        System.out.println(list.get(0));

        return list;
    }

    @Override
    public Integer getBool(DashbordSrchModel srchModel) throws Exception {
        Integer a = dashBordDao.getBool(srchModel);
        return a;
    }
}
