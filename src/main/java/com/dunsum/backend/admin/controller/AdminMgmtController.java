package com.dunsum.backend.admin.controller;

import com.dunsum.backend.batch.Batch;
import com.dunsum.backend.common.vo.environment.AppBatchVO;
import com.dunsum.backend.outside.dnf.service.DnfServiceImpl;
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

    private final AppBatchVO appBatchVO;

    private final DnfServiceImpl dnfApis;

    @ApiOperation(value = "Mapper 테스트", notes = "")
    @RequestMapping(value = "/ins/entt", method = RequestMethod.POST)
    public void insEntt(@RequestBody BaseVO baseVO) throws Exception {
        modelUtils.createEntity();
        System.out.println();
    }

    @ApiOperation(value = "batch", notes = "")
    @RequestMapping(value = "/run/batch", method = RequestMethod.POST)
    public void runBatch() throws Exception {
        Batch batch = Batch.getInstance();
        batch.run(false, "dnfServer");
    }

    @ApiOperation(value = "test", notes = "")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test() throws Exception {
        dnfApis.call();
    }

    @ApiOperation(value = "test", notes = "")
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public void test2() throws Exception {
        System.out.println(appBatchVO.toString());
    }
}
