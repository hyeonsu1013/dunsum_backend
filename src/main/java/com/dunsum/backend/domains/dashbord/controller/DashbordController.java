package com.dunsum.backend.domains.dashbord.controller;

import com.dunsum.backend.domains.dashbord.model.DashbordSrchModel;
import com.dunsum.backend.domains.dashbord.service.DashbordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/${project.name}/api/dashbord")
@RestController
@RequiredArgsConstructor
@Api(value = "dashbord Controller - Swagger", description = "dashbord Controller 입니다.")
public class DashbordController {

    private final DashbordService dashbordService;

    @ApiOperation(value = "Mapper 테스트", notes = "")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Integer getBool(@RequestBody DashbordSrchModel srchModel) throws Exception {
        System.out.println(srchModel.getServerId());
        return dashbordService.getBool(srchModel);
    }

    @ApiOperation(value = "Mapper 테스트", notes = "")
    @RequestMapping(value = "/sel", method = RequestMethod.POST)
    public Object sel(@RequestBody DashbordSrchModel srchModel) throws Exception {
        System.out.println(srchModel.getServerId());
        return dashbordService.sel(srchModel);
    }

}
