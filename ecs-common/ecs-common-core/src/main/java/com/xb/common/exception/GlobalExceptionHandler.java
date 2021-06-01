package com.xb.common.exception;

import com.xb.common.base.ret.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult exceptionHandler(Exception e) {
        log.error("异常信息："+e.getMessage());
        e.printStackTrace();
        if(e instanceof BusinessException){
            return ApiResult.error().setMsg(e.getMessage());
        }
        //数据校验失败异常
        if(e instanceof MethodArgumentNotValidException || e instanceof BindException){
            BindingResult bindingResult = null;
            if(e instanceof MethodArgumentNotValidException){
                bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
            }else{
                bindingResult = ((BindException)e).getBindingResult();
            }
            //获取异常信息列表
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            //把所有异常信息拼接成字符串
            StringBuffer msg = new StringBuffer();
            for(int i = 0;i<fieldErrors.size();i++){
                msg.append(fieldErrors.get(i).getField());//获取参数名称
                msg.append(":");
                msg.append(fieldErrors.get(i).getDefaultMessage());//获取校验失败的消息
                if(i<fieldErrors.size()-1){
                    msg.append(",");
                }
            }
            return ApiResult.error(msg.toString());
        }

        if(e instanceof Exception){
            return ApiResult.error();
        }
        return ApiResult.error();
    }
}
