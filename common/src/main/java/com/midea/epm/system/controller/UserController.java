package com.midea.epm.system.controller;

import com.google.common.collect.Maps;
import com.midea.epm.common.entity.ResponseCode;
import com.midea.epm.common.entity.ResponseResult;
import com.midea.epm.common.util.JWTUtils;
import com.midea.epm.system.entity.User;
import com.midea.epm.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping(value = "/login")
    public ResponseResult userLogin(@RequestParam(name = "username", required = true) String userName,
                            @RequestParam(name = "password", required = true) String password, ServletResponse response) {

        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        boolean loginSuccess = false;
        // 将用户名和密码封装成 UsernamePasswordToken 对象
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login(token);
            msg = "登录成功。";
            loginSuccess = true;
        } catch (UnknownAccountException uae) { // 账号不存在
            msg = "用户名与密码不匹配，请检查后重新输入！账号不存在";
        } catch (IncorrectCredentialsException ice) { // 账号与密码不匹配
            msg = "用户名与密码不匹配，请检查后重新输入！";
        } catch (LockedAccountException lae) { // 账号已被锁定
            msg = "该账户已被锁定，如需解锁请联系管理员！";
        } catch (AuthenticationException ae) { // 其他身份验证异常
            msg = "登录异常，请联系管理员！";
        }
        if (loginSuccess) {
            // 若登录成功，签发 JWT token
            String jwtToken = JWTUtils.getInstance().getToken(userName,userName,1);
            // 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
            ((HttpServletResponse) response).setHeader(JWTUtils.AUTH_HEADER, jwtToken);
            return new ResponseResult(ResponseCode.SUCCESS.getCode(), msg,jwtToken);
        } else {
            return new ResponseResult(ResponseCode.BUSINESS_FAILE.getCode(), msg);
        }

    }

    @GetMapping("/logout")
    public ResponseResult logout() {

        return ResponseResult.ok();
    }


}
