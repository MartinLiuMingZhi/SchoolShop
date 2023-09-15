package com.example.schoolshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *  用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     *密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 邮件
     */
    @TableField(value = "email")
    private String email;

    /**
     * 电话号码
     */
    @TableField(value = "phone")
    private String phone;

//    /**
//     * 出生日期
//     */
//    @TableField(value = "birthday")
//    private Date birthday;

    /**
     * 状态码
     */
    @TableField(value = "state")
    private Integer state;


    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;
}