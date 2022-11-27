package com.dunsum.backend.domains.common;

import com.dunsum.backend.common.utils.ModelUtils;
import com.dunsum.backend.common.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/${project.name}/api/swgr")
@RestController
@RequiredArgsConstructor
@Api(value = "Swagger Controller - Swagger", description = "스웨거 Controller 입니다.")
public class SwaggerIgnoreController {

    private final ModelUtils modelUtils;

    @ApiOperation(value = "Entity 생성", notes = "")
    @RequestMapping(value = "/ins/entt", method = RequestMethod.POST)
    public void insEntt(@RequestBody BaseVO vo) throws Exception {
        modelUtils.createEntity();
    }
}
