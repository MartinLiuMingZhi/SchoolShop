package com.example.schoolshop.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.domain.Manager;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.mapper.ManagerMapper;
import com.example.schoolshop.model.manager.ChangePassword;
import com.example.schoolshop.model.manager.ForgetPassword;
import com.example.schoolshop.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.example.schoolshop.common.ErrorCode.NOT_FOUND_ERROR;
import static com.example.schoolshop.common.ErrorCode.PARAMS_ERROR;

@Service
@Slf4j
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Override
    public Manager login(String username, String password) {
        SaTokenInfo saTokenInfo = null;
        String md5_Password = SaSecureUtil.md5(password);

        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", md5_Password);
        Manager manager = this.baseMapper.selectOne(queryWrapper);
        if (manager == null) {
            throw new BusinessException(PARAMS_ERROR, "账号不存在或账号密码错误");
        }

        StpUtil.login(manager.getId()); //对话登录
//        StpUtil.getSession().set("manager",manager);
//        saTokenInfo = StpUtil.getTokenInfo();//获取当前用户token信息
        return manager;
    }

    @Override
    public Manager register(String username, String password,String checkPassword) {
        if (StringUtils.isAnyBlank(username,password,checkPassword)){
            throw new BusinessException(PARAMS_ERROR,"账号或密码为空");
        }
        if (!password.equals(checkPassword)){
            throw new BusinessException(PARAMS_ERROR,"两次输入密码不一致");
        }
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if (this.baseMapper.selectOne(queryWrapper) != null) {
            throw new BusinessException(PARAMS_ERROR, "账号已存在");
        }
        synchronized (username.intern()){
            String password_md5 = SaSecureUtil.md5(password);//md5加密
            Manager manager = new Manager();
            manager.setUsername(username);
            manager.setPassword(password_md5);
            manager.setState(1);
            manager.setAvatar("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");
            manager.setRole("employee");
            boolean saveResult = this.save(manager);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            SaTokenInfo saTokenInfo = null;
            StpUtil.login(manager.getId()); //对话登录
//            StpUtil.getSession().set("manager",manager);
//            saTokenInfo = StpUtil.getTokenInfo();//获取当前用户token信息
            return manager;
        }
    }

    @Override
    public Manager getManager() {
        //获取session中的信息
//        StpUtil.checkLogin();
//        Object userObject = StpUtil.getSession().get("satoken");
//        Manager current_manager = (Manager) userObject;
//        if (current_manager == null || current_manager.getId() == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//        }
        // 2. 用id从数据库查找用户信息，session因为网络等原因可能篡改
//        long managerId = current_manager.getId();
        StpUtil.checkLogin();
        log.info(StpUtil.getLoginId().toString());
        long managerId = Long.parseLong((String) StpUtil.getLoginId());
        Manager manager = this.baseMapper.selectById(managerId);
        if (manager == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return manager;
    }

    @Override
    public IPage<Manager> page(Integer start, Integer pageSize) {
        IPage<Manager> page = new Page<>(start,pageSize);
        IPage<Manager> resultPage = this.baseMapper.selectPage(page, null); // 执行数据库查询操作
        return resultPage;
    }

    @Override
    public Boolean Update(Manager manager) {
        long managerId = manager.getId();
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",managerId);
        int rows = this.baseMapper.update(manager,queryWrapper);
        return rows > 0;
    }

    @Override
    public Boolean UpdatePassword(ChangePassword changePassword) {
        if (!StpUtil.isLogin()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        long id = Long.parseLong((String) StpUtil.getLoginId());
        String password = changePassword.getPassword();
        String newPassword = changePassword.getNewPassword();
        String confirmPassword = changePassword.getConfirmPassword();
        if (StringUtils.isAnyBlank(password,newPassword,confirmPassword)){
            throw new BusinessException(PARAMS_ERROR,"密码为空");
        }
        if (password == newPassword || password == confirmPassword){
            throw new BusinessException(PARAMS_ERROR,"新旧密码一致，密码未改变");
        }
        Manager manager = this.baseMapper.selectById(id);
        String password_md5 = SaSecureUtil.md5(password);
        if (!manager.getPassword().equals(password_md5)){
            throw new BusinessException(PARAMS_ERROR,"原密码错误");
        }
        if (newPassword.equals(confirmPassword)){
            String newPassword_md5 = SaSecureUtil.md5(newPassword);
            UpdateWrapper<Manager> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",id);
            updateWrapper.set("password",newPassword_md5);
            int rows = this.baseMapper.update(null,updateWrapper);
            return rows>0;
        }else {
            throw new BusinessException(PARAMS_ERROR,"新密码不一致");
        }

    }

    @Override
    public Boolean resetPassword(ForgetPassword forgetPassword) {
        String username = forgetPassword.getUsername();
        String phone = forgetPassword.getPhone();
        if (StringUtils.isAnyBlank(username,phone)){
            throw new BusinessException(PARAMS_ERROR,"用户名或电话号码为空");
        }
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Manager manager = this.baseMapper.selectOne(queryWrapper);
        if (manager == null){
            throw  new BusinessException(NOT_FOUND_ERROR,"用户不存在");
        }
        if (!phone.equals(manager.getPhone())){
            throw new BusinessException(NOT_FOUND_ERROR,"验证错误");
        }
        manager.setPassword(SaSecureUtil.md5("12345678"));
        int rows = this.baseMapper.updateById(manager);
        return rows>0;
    }
}
