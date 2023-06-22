package com.example.schoolshop.constant;

/**
 * 用户常量
 */
public interface UserConstant {
    /**
     * 用户账号长度
     */
    int USER_ACCOUNT_LENGTH = 8;

    /**
     * 用户密码最小长度
     */
    int USER_PASSWORD_MIN_LENGTH = 8;

    /*
    密码盐值
     */
    String SALT_VALUE = "laterya";

    /*
    登陆状态
     */
    String USER_LOGIN_STATE = "user_login";
    /*
    手机号码长度
     */
    int USER_PHONE_LENGTH = 11;

    /*
    redis存储验证码
     */
    String LOGIN_CODE_KEY = "login:code:";

    /*
    验证码失效时间
     */
    int CODE_EXPIRE_TIME = 3;
    /*
    验证码长度
     */
    int CODE_LENGTH = 4;
}
