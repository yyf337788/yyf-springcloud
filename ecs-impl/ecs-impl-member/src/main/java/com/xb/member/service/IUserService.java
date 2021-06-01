package com.xb.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xb.common.entity.dto.member.UserDto;
import com.xb.common.entity.model.member.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author com.xb
 * @since 2021-05-13
 */
public interface IUserService extends IService<User> {

    public IPage<User> getByPage(UserDto dto);
}
