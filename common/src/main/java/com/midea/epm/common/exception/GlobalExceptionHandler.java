package com.midea.epm.common.exception;

import com.midea.epm.common.entity.ResponseCode;
import com.midea.epm.common.entity.ResponseResult;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShiroException.class)
    public ResponseResult handleShiroException(ShiroException e) {
        String eName = e.getClass().getSimpleName();
        log.error("shiro执行出错：{}",eName);
        return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(),ResponseCode.SERVER_ERROR.getMsg());
    }

    @ExceptionHandler(AccountException.class)
    public ResponseResult handleAccountException(ShiroException e) {
        String eName = e.getClass().getSimpleName();
        log.error("token参数异常：{}",eName);
        return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(),ResponseCode.SERVER_ERROR.getMsg());
    }


    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseResult page401(UnauthenticatedException e) {
        log.error("未经授权:{}",e.getMessage());
        return ResponseResult.fail(ResponseCode.NO_AUTH.getCode(),ResponseCode.NO_AUTH.getMsg());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseResult page403(UnauthorizedException e) {
        log.error("无权限访问:{}",e.getMessage());
        return ResponseResult.fail(ResponseCode.NO_AUTH.getCode(),ResponseCode.NO_AUTH.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult error(Exception e) {
        log.error("系统异常:{}",e.getMessage());
        return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(),ResponseCode.SERVER_ERROR.getMsg());
    }

}
