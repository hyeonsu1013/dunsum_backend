package com.dunsum.backend.outside.dnf.controller;

import com.dunsum.backend.outside.dnf.service.DnfService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/${project.name}/api/otsd/dnf")
@RestController
@RequiredArgsConstructor
public class DnfController {

    private final DnfService dnfService;

    @ApiOperation(value = "Mapper 테스트", notes = "")
    @RequestMapping(value = "/sel/srvr", method = RequestMethod.POST)
    public List<Object> serSrvr() throws Exception {
        dnfService.selServers();
        return null;
    }
}
