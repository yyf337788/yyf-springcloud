package com.xb.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.common.entity.dto.member.UserDto;
import com.xb.common.entity.model.member.User;
import com.xb.member.mapper.UserMapper;
import com.xb.member.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author com.xb
 * @since 2021-05-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public IPage<User> getByPage(UserDto dto) {
        IPage<User> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        QueryWrapper<User> query = new QueryWrapper<>();
        query.orderByDesc(dto.getOrderby());
        //把性别也作为查询条件
        query.eq("sex", dto.getSex());
        return this.page(page, query);

    }


}
