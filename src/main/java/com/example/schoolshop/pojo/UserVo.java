package com.example.schoolshop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
public class UserVo {
    private String id;          //id
    private String username;    //用户名
    private String password;    //密码
    private String name;        //姓名
    private String sex;         //性别
    private String email;       //邮件
    private String phone;       //电话号码
    private Date birthday;      //出生日期
    private Integer state;      //状态码

}
