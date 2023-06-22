package com.example.schoolshop.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.model.user.LoginRequest;
import com.example.schoolshop.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Resource
    private UserService userService;
    //登录
    @PostMapping("/login")
    public BaseResponse<SaTokenInfo> userLogin(@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (StringUtils.isAllBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        SaTokenInfo result = userService.userLogin(username, password);
        return ResultUtils.success(result);
    }



}
