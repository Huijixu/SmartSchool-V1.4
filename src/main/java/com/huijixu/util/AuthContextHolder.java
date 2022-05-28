package com.huijixu.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */

/*
  解析request请求中的 token口令
 */
public class AuthContextHolder {

    //从请求头token获取userid
    public static Long getUserIdToken(HttpServletRequest request) {
        //从请求头获取token
        String token = request.getHeader("token");
        //调用工具类
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }

    //从请求头token获取name
    public static String getUserName(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //调用工具类
        String userName = JwtHelper.getUserName(token);
        return userName;
    }
}
