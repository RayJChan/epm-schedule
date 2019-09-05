package com.midea.epm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.epm.system.entity.User;

public interface UserService  extends IService<User> {

    public User getUserByUserName(String username);

}
