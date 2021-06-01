package com.xb.common.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xb.common.base.ret.ApiResult;
import com.xb.common.entity.model.member.User;
import com.xb.common.exception.BusinessException;
import com.xb.common.jwt.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 从token中获取User对象
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    public User getCurrentUser(){
        String token = request.getHeader("TOKEN");
        if(StringUtils.isBlank(token)){
            throw new BusinessException("未获取到有效的token");
        }
        //解析token
        ApiResult rt = JWTUtils.verifyToken(token);
        if(rt.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException("token解析失败");
        }

        JSONObject user = JSONObject.parseObject(rt.getResult().get("user").toString());
        return JSON.toJavaObject(user, User.class);
    }

}
