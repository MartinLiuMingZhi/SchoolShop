package com.example.schoolshop.model.user;

import lombok.Data;

@Data
public class RegisterRequest {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;

}
