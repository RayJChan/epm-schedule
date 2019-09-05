package com.midea.epm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.epm.system.entity.User;
import com.midea.epm.system.mapper.UserMapper;
import com.midea.epm.system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserByUserName(String username) {
        QueryWrapper ew=new QueryWrapper();
        ew.eq("user_name",username);
        return baseMapper.selectOne(ew);
    }
}
