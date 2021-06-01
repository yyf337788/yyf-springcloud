package com.xb.common.entity.dto.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:用来接收前端传过来的数据
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 712813730345340383L;

    private Integer pageNum;
    private Integer pageSize;
    private String orderby;
    private Long id;
    private Integer status;
    private Date createTime;
    private Date modifyTime;
    private String phoneNumber;
    private String email;
    private String openId;
    private String password;
    private Integer sex;
    private String name;
    private Integer age;
    private String pic;
    private Integer isVip;
    private Integer score;


}
