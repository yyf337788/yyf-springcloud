package com.xb.member.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.common.base.controller.BaseController;
import com.xb.common.base.ret.ApiResult;
import com.xb.common.entity.dto.member.UserDto;
import com.xb.common.entity.model.member.User;
import com.xb.member.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  获取用户信息
 * </p>
 *
 * @author com.xb
 * @since 2021-05-13
 */
@Slf4j
@Api(tags = "获取用户信息接口")
@RestController
@RequestMapping("member/api/v2")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "从token中获取用户信息")
    @PostMapping("getCurrUser")
    public ApiResult getCurrUser(){
        User user = super.getCurrentUser();
        return ApiResult.success().put("user", user);
    }

    @ApiOperation(value = "根据id获取单条用户信息")
    @ApiImplicitParam(name = "id",value = "用户ID",required = true)
    @GetMapping("getUserById")
    public ApiResult getUserById(Long id){
        User user = userService.getById(id);
        return ApiResult.success().put("user", user);
    }

    @ApiOperation(value = "获取用户信息列表")
    @GetMapping("getUserList")
    public ApiResult getUserList(UserDto userDto){
        /*倒序
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> query = new QueryWrapper<>();
        query.orderByDesc("id");
        IPage<User> page1 = userService.page(page, query);*/
        //默认正序
        IPage<User> page = new Page<>(userDto.getPageNum(),userDto.getPageSize());
        IPage<User> page1 = userService.page(page);
        return ApiResult.success().put("page", page1);
    }
}
