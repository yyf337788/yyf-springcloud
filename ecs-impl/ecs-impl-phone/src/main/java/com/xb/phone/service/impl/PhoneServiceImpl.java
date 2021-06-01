package com.xb.phone.service.impl;

import com.xb.phone.service.PhoneService;
import com.xb.common.base.ret.ApiResult;
import com.xb.common.exception.BusinessException;
import com.xb.phone.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * @description:
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public ApiResult sendRegCode(String phoneNumber) {

        //1、生成临时注册码
        Random random = new Random();
        int code = random.nextInt(8999) + 1000;
        log.error("code :" + code);
        //2、把临时注册码保存在redis里面，有效时间为3min
        if(!redisUtils.set(phoneNumber+"_reg_code", code,180)){
            log.error(phoneNumber + ":注册码缓存失败");
            throw new BusinessException("注册码缓存失败！");
        }
        log.info(phoneNumber+"_reg_code"+redisUtils.get(phoneNumber+"_reg_code"));
        //3、调用短信服务发送验证码



        return ApiResult.success();
    }

    @Override
    public ApiResult verifyRegCode(String phoneNumber, String regCode) {
        //1、获取缓存中的注册码
        String strCode = redisUtils.get(phoneNumber + "_reg_code").toString();
        if(strCode == null){
            throw new BusinessException("手机注册码已失效");
        }
        //2、判断注册码是否正确
        if(!strCode.equals(regCode)){
            throw new BusinessException("手机注册码不正确");
        }
        //3、返回结果集
        return ApiResult.success();
    }
}
