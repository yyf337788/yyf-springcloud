package com.xb.common.base.ret;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:结果返回集
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
@Data //lombok 自动生成get、set方法
public class ApiResult implements Serializable {

    private static final long serialVersionUID = -8095207132002379030L;

    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE =500;
    public static final int ILLEGAL_CODE = 100;

    public static final String SUCCESS_MESSAGE = "操作成功";
    public static final String ERROR_MESSAGE = "服务器异常";
    public static final String ILLEGAL_MESSAGE = "参数非法";

    private int code;
    private String message;
    Map<String,Object> result = new HashMap<>();

    //请求成功
    public static ApiResult success(){
        ApiResult ret = new ApiResult();
        ret.code = SUCCESS_CODE;
        ret.message = SUCCESS_MESSAGE;
        return ret;
    }

    //服务器异常
    public static ApiResult error(){
        ApiResult ret = new ApiResult();
        ret.code = ERROR_CODE;
        ret.message = ERROR_MESSAGE;
        return ret;
    }

    //参数非法
    public static ApiResult illegal(){
        ApiResult ret = new ApiResult();
        ret.code = ILLEGAL_CODE;
        ret.message = ILLEGAL_MESSAGE;
        return ret;
    }

    //请求成功
    public static ApiResult success(String message){
        ApiResult ret = new ApiResult();
        ret.code = SUCCESS_CODE;
        ret.message = message;
        return ret;
    }

    //服务器异常
    public static ApiResult error(String message){
        ApiResult ret = new ApiResult();
        ret.code = ERROR_CODE;
        ret.message = message;
        return ret;
    }

    //参数非法
    public static ApiResult illegal(String message){
        ApiResult ret = new ApiResult();
        ret.code = ILLEGAL_CODE;
        ret.message = message;
        return ret;
    }

    public ApiResult put(String key,Object value){
        this.result.put(key,value);
        return this;
    }

    public ApiResult setMsg(String msg){
        this.message = msg;
        return this;
    }


}
