package com.dunsum.backend.common.apis;

import com.dunsum.backend.common.apis.enums.OutApisEnum;
import com.dunsum.backend.common.apis.interfaces.DunsumApis;
import com.dunsum.backend.common.utils.RestUtils;
import com.dunsum.backend.common.vo.environment.AppConnDataVO;
import com.dunsum.backend.common.vo.environment.AppOutsideVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DnfApis extends DunsumApis {

    private final OutApisEnum oae = OutApisEnum.DNF;


    // DI 대상
    private final RestUtils restUtils;
    private final AppOutsideVO outVO;

    @Autowired
    public DnfApis(RestUtils restUtils, AppOutsideVO outVO) {
        this.outVO = outVO;
        this.restUtils = restUtils;
        this.setConnData();
    }

    @Override
    public void setConnData() {
        this.connData = outVO.getConnData(oae.getName());
        if (this.connData == null) {
            this.connData = new AppConnDataVO();
        }
    }

    public void call() throws Exception {
        System.out.println(this.connData);
    }
}
