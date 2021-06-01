package com.xb.common.entity.model.member;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *  User对象  数据库映射实体类
 */
@Data
@ApiModel(value="User对象")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "使用状态(0：禁用 1：正常)")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$",message = "手机号码格式不正确")
    @ApiModelProperty(value = "电话号码",required = true,dataType = "String")
    private String phoneNumber;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "用户邮箱",required = true,dataType = "String")
    private String email;

    @ApiModelProperty(value = "微信ID")
    private String openId;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名称",required = true,dataType = "String")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度不对")
    @ApiModelProperty(value = "用户密码",required = true,dataType = "String")
    private String password;

    @ApiModelProperty(value = "用户性别（0：男 1：女）")
    private Integer sex;

    @ApiModelProperty(value = "用户年龄")
    private Integer age;

    @ApiModelProperty(value = "用户头像")
    private String pic;

    @ApiModelProperty(value = "是否是vip(0:普通用户 1：vip)")
    private Integer isVip;

    @ApiModelProperty(value = "用户积分")
    private Integer score;
}
