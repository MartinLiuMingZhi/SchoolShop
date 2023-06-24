package com.example.schoolshop.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.constant.UserConstant;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.mappar.UserMapper;
import com.example.schoolshop.model.user.LoginResponse;
import com.example.schoolshop.model.user.RegisterResponse;
import com.example.schoolshop.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;


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
    public LoginResponse userLogin(String userName, String userPassword) {


        // 2. 加密
//        String encryptPassword = DigestUtil.md5Hex((UserConstant.SALT_VALUE + userPassword).getBytes());

        // 3. 查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        queryWrapper.eq("password", userPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或账号密码错误");
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(user.getUsername());
        loginResponse.setId(user.getId());
        return loginResponse;
    }

    @Override
    public RegisterResponse userRegister(String username, String password, String name, String sex, String email, String phone, Date birthay) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhone(phone);
        user.setBirthday(birthay);
        user.setState(1);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUsername(username);
        return registerResponse;
    }
}






