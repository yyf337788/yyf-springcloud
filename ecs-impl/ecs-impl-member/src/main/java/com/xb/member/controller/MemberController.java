package com.xb.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.common.base.ret.ApiResult;
import com.xb.common.entity.model.member.User;
import com.xb.common.exception.BusinessException;
import com.xb.common.jwt.JWTUtils;
import com.xb.common.utils.RegexUtils;
import com.xb.member.feign.PhoneServiceFeign;
import com.xb.member.service.IUserService;
import com.xb.member.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description: 會員服務處理類
 * @author:
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
@Slf4j
@Api(tags = "会员注册登录服务API")
@RestController
@RequestMapping("member/api/v1")
public class MemberController {

    @Autowired
    private PhoneServiceFeign phoneServiceFeign;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发送手机注册码
     * @param phoneNumber
     * @return
     */
    @ApiOperation(value = "发送手机注册码" ,notes = "用户注册时发送到手机的验证码作为用户注册时的注册码")
    @ApiImplicitParam(name = "phoneNumber",value = "手机号码",required = true)
    @PostMapping("sendRegCode")
    public ApiResult sendRegCode(String phoneNumber){

        //1、校驗手機號碼
        if(!RegexUtils.isMobileExact(phoneNumber)){
            throw new BusinessException("手机号码格式不正确");
        }

        //2、檢測手機號碼是否已存在
        if(userService.getOne(new QueryWrapper<User>().eq("phone_number", phoneNumber)) != null){
            throw new BusinessException("手机号码已存在");
        }

        //3、調用手機服務發送驗證碼
        ApiResult result = phoneServiceFeign.sendRegCode(phoneNumber);

        return result;

    }

    /**
     *会员注册
     * @RequestBody 将json对象转换成java对象
     *
     * @return
     */
    @ApiOperation(value = "会员注册")
    @ApiImplicitParam(name = "regCode",value = "手机注册码",required = true)
    @PostMapping ("register")
    public ApiResult register(@Validated @RequestBody User user, String regCode){
        //1、参数校验 user对象使用Validated进行校验
        log.info("regCode:   "+regCode);
        if(regCode == null || !regCode.matches("\\d{4}")){
            throw new BusinessException("短信验证码格式不正确");
        }

        //2、手机号码是否已经被注册
        if(userService.getOne(new QueryWrapper<User>().eq("phone_number", user.getPhoneNumber())) != null){
            throw new BusinessException("手机号码已经被注册");
        }

        //3、邮箱是否已经被使用
        if(userService.getOne(new QueryWrapper<User>().eq("email", user.getEmail())) != null){
            throw new BusinessException("邮箱已经被指用");
        }
        //4、注册码校验,因为没有调用短信系统，这里也使用redis里面的regCode
        ApiResult result = phoneServiceFeign.verifyRegCode(user.getPhoneNumber(), redisUtils.get(user.getPhoneNumber() + "_reg_code").toString());
        if(result.getCode() != ApiResult.SUCCESS_CODE){
            throw new BusinessException("手机注册码校验失败");
        }
        //5、调用注册业务接口保存会员注册信息
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        //对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if(!userService.save(user)){
            throw new BusinessException("注册失败，请稍后再试！");
        }

        return ApiResult.success("注册成功");
    }

    @ApiOperation(value="账号密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber",value = "账号",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @PostMapping("login")
    public ApiResult login(String phoneNumber,String password){
        //1、账号密码不能为空
        if(phoneNumber == null || password == null){
            throw new BusinessException("账号密码不能为空");
        }
        //2、账号密码正确
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", phoneNumber);
        queryWrapper.eq("password", DigestUtils.md5DigestAsHex(password.getBytes()));
        if(userService.getOne(queryWrapper) == null){
            throw new BusinessException("账号密码不正确");
        }
        //3、用户状态正常，1（正常）
        if(!userService.getOne(queryWrapper).getStatus().equals(1)){
            throw new BusinessException("用户状态不正常");
        }
        //4、创建token并返回
        return JWTUtils.createToken(userService.getOne(queryWrapper));
    }
}
