package com.example.schoolshop.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.model.user.RegisterResponse;

/**
 * <p>
 *  用户服务类
 * </p>
 *
 * @author laterya
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userName 用户昵称
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     * @return 用户id
     */
    RegisterResponse userRegister(String userName, String userPassword, String checkPassword);

    SaTokenInfo userLogin(String userAccount, String userPassword);

    User getLoginUser();


}
