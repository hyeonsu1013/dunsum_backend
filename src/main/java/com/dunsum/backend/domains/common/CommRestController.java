package com.dunsum.backend.domains.common;

import com.dunsum.backend.common.utils.ModelUtils;
import com.dunsum.backend.common.vo.BaseVO;
import com.dunsum.backend.domains.common.service.CommService;
import com.dunsum.backend.domains.entity.CodeEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/${project.name}/api/comm")
@RestController
@RequiredArgsConstructor
@Api(value = "Comm Controller - Swagger", description = "공통 Controller 입니다.")
public class CommRestController {

    private final ModelUtils modelUtils;
    private final CommService commService;

    @ApiOperation(value = "Entity 생성", notes = "")
    @RequestMapping(value = "/ins/entt", method = RequestMethod.POST)
    public void insEntt(@RequestBody BaseVO vo) throws Exception {
        modelUtils.createEntity();
    }

    @ApiOperation(value = "공통코드 생성", notes = "")
    @RequestMapping(value = "/sel/code", method = RequestMethod.POST)
    public Map<String, List<CodeEntity>> selCode(@RequestBody List<String> codeInis) throws Exception {
        return commService.selCode(codeInis);
    }


}
