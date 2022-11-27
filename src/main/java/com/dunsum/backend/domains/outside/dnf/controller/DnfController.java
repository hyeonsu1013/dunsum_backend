package com.dunsum.backend.domains.outside.dnf.controller;

import com.dunsum.backend.domains.outside.dnf.model.DnfJobModel;
import com.dunsum.backend.domains.outside.dnf.model.DnfSrchModel;
import com.dunsum.backend.domains.outside.dnf.model.DnfSrvrModel;
import com.dunsum.backend.domains.outside.dnf.service.DnfSrvrService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/${project.name}/api/otsd/dnf")
@RestController
@RequiredArgsConstructor
public class DnfController {

    private final DnfSrvrService dnfSrvrService;

    @ApiOperation(value = "DNF 서버 목록 조회", notes = "")
    @RequestMapping(value = "/srvr/sel", method = RequestMethod.POST)
    public DnfSrvrModel selSrvr(@RequestBody DnfSrchModel srchModel) throws Exception {
        return dnfSrvrService.selServers(srchModel);
    }

    @ApiOperation(value = "직업 조회", notes = "")
    @RequestMapping(value = "/sel/job", method = RequestMethod.POST)
    public DnfJobModel selJob(@RequestBody DnfSrchModel srchModel) throws Exception {
        return dnfSrvrService.selJobs(srchModel);
    }

    @ApiOperation(value = "직업 조회", notes = "")
    @RequestMapping(value = "/upd/job", method = RequestMethod.POST)
    public Map<String, Object> updJob(@RequestBody DnfSrchModel srchModel) throws Exception {
        return dnfSrvrService.updJobs(srchModel);
    }
}
