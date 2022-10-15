package com.dunsum.backend.outside.dnf.controller;

import com.dunsum.backend.common.vo.environment.AppOutsideVO;
import com.dunsum.backend.outside.dnf.model.DnfSrchModel;
import com.dunsum.backend.outside.dnf.service.DnfSrvrService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/${project.name}/api/otsd/dnf")
@RestController
@RequiredArgsConstructor
public class DnfController {

    private final AppOutsideVO appOutsideVO;
    private final DnfSrvrService dnfService;

    @ApiOperation(value = "Mapper 테스트", notes = "")
    @RequestMapping(value = "/srvr/sel", method = RequestMethod.POST)
    public List<Object> selSrvr(@RequestBody DnfSrchModel srchModel) throws Exception {
        dnfService.selServers(srchModel);
        return null;
    }
}
