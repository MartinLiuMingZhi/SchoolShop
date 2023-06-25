package com.example.schoolshop.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.model.user.LoginResponse;
import com.example.schoolshop.model.user.RegisterResponse;

import java.util.Date;

/**
 * <p>
 *  用户服务类
 * </p>
 *
 * @author martin liu  & sq
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param username 用户昵称
     * @param password 用户密码
     * @param name     姓名
     * @param sex      性别
     * @param email    邮箱
     * @param phone    电话号码
     * @param birthay  出生日期
     * @return 用户id
     */
    RegisterResponse userRegister(String username, String password, String name, String sex, String email, String phone, Date birthay);

    LoginResponse userLogin(String username, String password);

    User getLoginUser(Long id);

}
