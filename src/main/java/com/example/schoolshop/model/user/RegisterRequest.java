package com.example.schoolshop.model.user;

import lombok.Data;

import java.sql.Date;

@Data
public class RegisterRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String checkPassword;
    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

//    /**
//     * 出生日期
//     */
//    private Date birthday;

//    /**
//     * 状态码
//     */
//    private Integer state;

    /**
     * 头像
     */
    private String avatar;

}
