package com.dunsum.backend.domains.account.controller;

import com.dunsum.backend.common.security.model.TokenUserModel;
import com.dunsum.backend.domains.account.service.AcutMgmtService;
import com.dunsum.backend.domains.entity.UserEntity;
import com.dunsum.backend.domains.entity.UserGustEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/${project.name}/api/acut")
@RestController
@RequiredArgsConstructor
@Api(value = "account Controller - Swagger", description = "계정관리 Controller 입니다.")
public class AcutMgmtController {

    private final AcutMgmtService acutMgmtService;

    @ApiOperation(value = "Guest 로그인", notes = "")
    @RequestMapping(value = "/ins/gust", method = RequestMethod.POST)
    public TokenUserModel insGust(@RequestBody  UserGustEntity entt) throws Exception {
        return acutMgmtService.insGust(entt);
    }

    @ApiOperation(value = "회원가입", notes = "")
    @RequestMapping(value = "/ins/user", method = RequestMethod.POST)
    public UserEntity insUser(@RequestBody  UserEntity entt) throws Exception {
        return acutMgmtService.insUser(entt);
    }

    @ApiOperation(value = "유저정보 조회", notes = "")
    @RequestMapping(value = "/get/user", method = RequestMethod.POST)
    public UserEntity getUser(@RequestBody  UserEntity entt) throws Exception {
        return acutMgmtService.getUser(entt);
    }
}
