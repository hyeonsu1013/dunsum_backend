package com.dunsum.backend.admin.controller;

import com.dunsum.backend.common.apis.DnfApis;
import com.dunsum.backend.common.utils.ModelUtils;
import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/${project.name}/api/admin")
@RestController
@RequiredArgsConstructor
@Api(value = "admin Controller - Swagger", description = "관리자전용 admin Controller 입니다.")
public class AdminMgmtController {

    private final ModelUtils modelUtils;

    private final DnfApis dnfApis;

    @ApiOperation(value = "Mapper 테스트", notes = "")
    @RequestMapping(value = "/ins/entt", method = RequestMethod.POST)
    public void insEntt(@RequestBody BaseVO baseVO) throws Exception {
        modelUtils.createEntity();
        System.out.println();
    }

    @ApiOperation(value = "test", notes = "")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test() throws Exception {
        dnfApis.call();
    }
}
