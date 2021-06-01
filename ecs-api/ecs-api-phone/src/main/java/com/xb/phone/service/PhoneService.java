package com.xb.phone.service;

import com.xb.common.base.ret.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:手機短信服務接口
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
@Api(tags = "手机短信对外服务API接口")
@RestController
@RequestMapping("phone/api/v1")
public interface PhoneService {

    /**
     * 发送注册码
     * @param phoneNumber
     * @return
     */
    @PostMapping("sendRegCode")
    @ApiOperation(value = "发送手机短信验证码",notes = "")
    @ApiImplicitParam(name = "phoneNumber",value = "手机号码",required = true)
    ApiResult sendRegCode(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 注册码校验
     * @param phoneNuber
     * @param regCode
     * @return
     */
    @PostMapping("verifyRegCode")
    @ApiOperation(value = "对发送的手机短信验证码校验")
    @ApiImplicitParams({@ApiImplicitParam(name = "phoneNumber",value = "手机号码",required = true
                        ),@ApiImplicitParam(name = "regCode",value = "短信校验码",required = true
                        )})
    ApiResult verifyRegCode(@RequestParam("phoneNumber") String phoneNuber,@RequestParam("regCode") String regCode);


}
