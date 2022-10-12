package com.dunsum.backend.outside.dnf.service;

import com.dunsum.backend.common.utils.RestUtils;
import com.dunsum.backend.common.vo.environment.AppConnDataVO;
import com.dunsum.backend.common.vo.environment.AppOutsideVO;
import com.dunsum.backend.outside.OutsideEnumFactory;
import com.dunsum.backend.outside.dnf.enums.DnfEnumsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor=Exception.class)
public class DnfServiceImpl implements DnfService {

    // DI 대상
    private final AppConnDataVO outVO;
    private final RestUtils restUtils;

    @Autowired
    public DnfServiceImpl(AppOutsideVO outVO, RestUtils restUtils) {
        this.outVO = outVO.getConnData(OutsideEnumFactory.OutApisEnum.DNF.getName());
        this.restUtils = restUtils;
    }

    @Override
    public String selServers() throws Exception {
        DnfEnumsFactory.DnfApiEnums dae = DnfEnumsFactory.DnfApiEnums.SEL_SERVERS;
        String url = DnfEnumsFactory.getApiUrl(outVO, dae);
        Map<String, Object> params = DnfEnumsFactory.getApiParams(outVO, dae);
        System.out.println("params :: " + params);

        Object rtn = restUtils.sendRestApi(url, params, Object.class, dae.getHttpMethod());
        System.out.println("rtn :: " + rtn);

        return rtn.toString();
    }

    public void call() throws Exception {
        System.out.println("this.connData :: " + this.outVO);
        String url = this.outVO.getDomain();
        url += "/df/servers";
        Map<String, Object> map = new HashMap<>();
        map.put(this.outVO.getKeyName(), this.outVO.getKey());
        Object rtn = restUtils.sendRestApi(url, map, Object.class, HttpMethod.GET, null, false);
        System.out.println("rtn :: " + rtn);
    }


}
