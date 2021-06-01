package com.xb.common.entity.view.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xb.common.entity.model.member.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:返回给前端的数据
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
@Data
public class UserView implements Serializable {
    private static final long serialVersionUID = -5871068017784413362L;

    public UserView(){}

    public UserView(User user){
        this.id = user.getId();
        this.status = user.getStatus();
        this.createTime = user.getCreateTime();
        this.modifyTime = user.getModifyTime();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.openId = user.getOpenId();
        this.password = user.getPassword();
        this.sex = user.getSex();
        this.name = user.getName();
        this.age =user.getAge();
        this.pic = user.getPic();
        this.isVip = user.getIsVip();
        this.score = user.getScore();
    }

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
    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;
    @ApiModelProperty(value = "用户邮箱")
    private String email;
    @ApiModelProperty(value = "微信ID")
    private String openId;
    @ApiModelProperty(value = "用户密码")
    private String password;
    @ApiModelProperty(value = "用户性别（0：男 1：女）")
    private Integer sex;
    @ApiModelProperty(value = "用户性别")
    private String sexStr;
    public String getSexStr() {
        if(this.sex != null){
            return this.sex==0?"男":"女";
        }
        return sexStr;
    }
    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "用户年龄")
    private Integer age;
    @ApiModelProperty(value = "用户头像")
    private String pic;
    @ApiModelProperty(value = "是否是vip(0:普通用户 1：vip)")
    private Integer isVip;
    @ApiModelProperty(value = "用户积分")
    private Integer score;

}
