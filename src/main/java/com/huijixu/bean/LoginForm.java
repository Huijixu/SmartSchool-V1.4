package com.huijixu.bean;

import lombok.Data;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */

/**
 * 登录界面表单信息封装类
 */
@Data
public class LoginForm {

    private String  username;
    private String  password;
    private Integer  userType;
    private String  verifiedCode;  //图片验证码


}
