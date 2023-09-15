package com.example.schoolshop.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.log.Log;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Slf4j  //log的注解
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 登录
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse resp) {
        if (loginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
//        String code = loginRequest.getCode();
        if (StringUtils.isAllBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        if(request.getSession(true).getAttribute("verify_code")==null||!loginRequest.getCode().toUpperCase().equals(request.getSession(true).getAttribute("verify_code").toString().toUpperCase())){
//           throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码错误"+"/t生成的验证码："+request.getSession(true).getAttribute("verify_code")+"/t输入的验证码："+code);
//        }
//        log.info("生成的验证码："+request.getSession(true).getAttribute("verify_code"));
//        log.info("输入的验证码："+code);
        User user = userService.userLogin(username, password);

//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token", saTokenInfo.getTokenValue());
//        tokenMap.put("tokenHead", saTokenInfo.getTokenName());
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(saTokenInfo.getTokenValue());
//        loginResponse.setTokenHead(saTokenInfo.getTokenName());
        return ResultUtils.success(user);
    }

    /**
     * 注册
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<User> userRegister(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String checkPassword = registerRequest.getCheckPassword();
        String name     = registerRequest.getName();
        String sex      = registerRequest.getSex();
        String email    = registerRequest.getEmail();
        String phone    = registerRequest.getPhone();
        String avatar = registerRequest.getAvatar();
//        Date   birthday = registerRequest.getBirthday();
//        Integer state   = registerRequest.getState();
        if (StringUtils.isAllBlank(username, password,email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user = userService.userRegister(username,password,checkPassword,name,sex,email,phone,avatar);

//        RegisterResponse registerResponse = new RegisterResponse();
//        registerResponse.setToken(saTokenInfo.getTokenValue());
//        registerResponse.setTokenHead(saTokenInfo.getTokenName());
        return ResultUtils.success(user);
    }

    /**
     * 获取账号信息
     * @return
     */
    @GetMapping("/getUser")
    public BaseResponse<User> getLoginUser(){
        if (!StpUtil.isLogin()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        User user = userService.getLoginUser();
        return ResultUtils.success(user);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
   @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request){
        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!StpUtil.isLogin()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        StpUtil.logout();
        return ResultUtils.success(true);
   }
}
