package com.example.schoolshop.controller;

import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.model.user.LoginRequest;
import com.example.schoolshop.model.user.LoginResponse;
import com.example.schoolshop.model.user.RegisterRequest;
import com.example.schoolshop.model.user.RegisterResponse;
import com.example.schoolshop.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Slf4j  //log的注解
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    /*
    登录
     */
    @PostMapping("/login")
    public BaseResponse<LoginResponse> userLogin(@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (StringUtils.isAllBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LoginResponse result = userService.userLogin(username, password);
        return ResultUtils.success(result);
    }

    /*
    注册
     */
    @PostMapping("/register")
    public BaseResponse<RegisterResponse> userRegister(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String name     = registerRequest.getName();
        String sex      = registerRequest.getSex();
        String email    = registerRequest.getEmail();
        String phone    = registerRequest.getPhone();
        Date   birthday = registerRequest.getBirthday();
//        Integer state   = registerRequest.getState();
        if (StringUtils.isAllBlank(username, password,email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        RegisterResponse userRegisterResponse = userService.userRegister(username,password,name,sex,email,phone,birthday);
        return ResultUtils.success(userRegisterResponse);
    }

    /*
    获取账号信息
     */
    @GetMapping("/getUser")
    public BaseResponse<User> getLoginUser(Long id){
        User user = userService.getLoginUser(id);
        return ResultUtils.success(user);
    }
}
