package com.example.schoolshop.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.constant.UserConstant;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.mapper.UserMapper;
import com.example.schoolshop.model.user.LoginResponse;
import com.example.schoolshop.model.user.RegisterResponse;
import com.example.schoolshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 用户服务实现类
 * </p>
 *
 * @author martin liu   & sq
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Override
    public User userLogin(String userName, String userPassword) {
        SaTokenInfo saTokenInfo = null;
        String md5_userPassword = SaSecureUtil.md5(userPassword);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        queryWrapper.eq("password", md5_userPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或账号密码错误");
        }
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setUsername(user.getUsername());
//        loginResponse.setId(user.getId());

        StpUtil.login(user.getId()); //对话登录
//        StpUtil.getSession().set(UserConstant.USER_LOGIN_STATE,user);
//        saTokenInfo = StpUtil.getTokenInfo();//获取当前用户token信息
        return user;
    }

    @Override
    public User getLoginUser() {
        //获取session中的信息
        StpUtil.checkLogin();
        Object userObject = StpUtil.getSession().get(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObject;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 2. 用id从数据库查找用户信息，session因为网络等原因可能篡改
        long userId = currentUser.getId();
        User user = this.baseMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }

    @Override
    public User userRegister(String username, String password,String checkPassword, String name, String sex, String email, String phone,String avatar) {
        //校验参数
        if (StringUtils.isAnyBlank(username,password,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名或密码为空");
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if (this.baseMapper.selectOne(queryWrapper) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已存在");
        }
        synchronized (username.intern()){
            String password_md5 = SaSecureUtil.md5(password);//md5加密
            User user = new User();
            user.setUsername(username);
            user.setPassword(password_md5);
            user.setName(name);
            user.setSex(sex);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAvatar(avatar);
//        user.setBirthday(birthay);
            user.setState(0);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
//        RegisterResponse registerResponse = new RegisterResponse();
//        registerResponse.setId(user.getId());
//        return registerResponse;
            SaTokenInfo saTokenInfo = null;
            StpUtil.login(user.getId()); //对话登录
//            StpUtil.getSession().set(UserConstant.USER_LOGIN_STATE,user);
//            saTokenInfo = StpUtil.getTokenInfo();//获取当前用户token信息
            return user;
        }
    }

}






