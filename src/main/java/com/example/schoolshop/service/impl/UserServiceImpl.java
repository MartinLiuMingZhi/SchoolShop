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
import com.example.schoolshop.model.user.RegisterResponse;
import com.example.schoolshop.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;


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

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public RegisterResponse userRegister(String userName, String userPassword, String checkPassword) {
        // 1. 校验参数
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        if (userPassword.length() < UserConstant.USER_PASSWORD_MIN_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码过短");
        }

        // 2.分配随机Account
        String userAccount = RandomUtil.randomNumbers(UserConstant.USER_ACCOUNT_LENGTH);

        // 3.保存用户
        synchronized (userAccount.intern()) {
            // 3.1 加密
            String encryptPassword = DigestUtil.md5Hex((UserConstant.SALT_VALUE + userPassword).getBytes());
            // 3.2 保存
            User user = new User();
            user.setUsername(userAccount);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            RegisterResponse userRegisterResponse = new RegisterResponse();
            userRegisterResponse.setId(user.getId());
            userRegisterResponse.setUserAccount(userAccount);
            return userRegisterResponse;
        }
    }

    @Override
    public SaTokenInfo userLogin(String userAccount, String userPassword) {
        // 1. 校验参数
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() != UserConstant.USER_ACCOUNT_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < UserConstant.USER_PASSWORD_MIN_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

        // 2. 加密
        String encryptPassword = DigestUtil.md5Hex((UserConstant.SALT_VALUE + userPassword).getBytes());

        // 3. 查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userAccount);
        queryWrapper.eq("password", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或账号密码错误");
        }

        // 4. 保存session
        StpUtil.login(user.getId());
        StpUtil.getSession().set(UserConstant.USER_LOGIN_STATE, user);
        return StpUtil.getTokenInfo();
    }

    @Override
    public User getLoginUser() {
        // 1. 获取session中的信息
        StpUtil.checkLogin();
        Object userObject = StpUtil.getSession().get(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObject;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 2. 用id从数据库查找用户信息，session因为网络等原因可能篡改
        long userId = currentUser.getId();
        currentUser = this.baseMapper.selectById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

}






