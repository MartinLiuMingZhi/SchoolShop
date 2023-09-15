package com.example.schoolshop.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.domain.Manager;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.model.manager.ChangePassword;
import com.example.schoolshop.model.manager.ForgetPassword;
import com.example.schoolshop.model.manager.Request;
import com.example.schoolshop.model.user.LoginRequest;
import com.example.schoolshop.service.ManagerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j  //log的注解
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/login")
    public BaseResponse<Manager> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse resp){
        if (loginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (StringUtils.isAllBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Manager manager = managerService.login(username, password);
        return ResultUtils.success(manager);
    }

    @PostMapping("/register")
    public BaseResponse<Manager> register(@RequestBody Request request){
        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String username = request.getUsername();
        String password = request.getPassword();
        String checkPassword = request.getCheckPassword();
        if (StringUtils.isAllBlank(username, password,checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Manager manager = managerService.register(username,password,checkPassword);
        return ResultUtils.success(manager);
    }

    /**
     * 获取账号信息
     * @return
     */
    @GetMapping("/getManager")
    public BaseResponse<Manager> getLoginManager(){
        if (!StpUtil.isLogin()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        Manager manager = managerService.getManager();
        return ResultUtils.success(manager);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> Logout(HttpServletRequest request){
        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!StpUtil.isLogin()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        StpUtil.logout();
        return ResultUtils.success(true);
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<IPage> getPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
        IPage<Manager> list = managerService.page(page,pageSize);
        System.out.println(list.toString());
        return ResultUtils.success(list);
    }

    /**
     * 更新信息
     * @param manager
     * @return
     */
    @PutMapping("/update")
    public BaseResponse<Boolean> Update(@RequestBody Manager manager){
        if (!StpUtil.isLogin()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        Boolean flag = managerService.Update(manager);
        return ResultUtils.success(flag);
    }

    /**
     * 修改密码
     * @param changePassword
     * @return
     */
    @PutMapping("/changePassword")
    public BaseResponse<Boolean> UpdatePassword(@RequestBody ChangePassword changePassword){
        Boolean flag = managerService.UpdatePassword(changePassword);
        return ResultUtils.success(flag);
    }

    @PutMapping("/forgetPassword")
    public BaseResponse<Boolean> ForgetPassword(@RequestBody ForgetPassword forgetPassword){
        Boolean flag = managerService.resetPassword(forgetPassword);
        return ResultUtils.success(flag);
    }
}
