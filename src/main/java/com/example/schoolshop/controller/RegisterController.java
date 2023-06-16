package com.example.schoolshop.controller;

import com.example.schoolshop.pojo.Result;
import com.example.schoolshop.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    //注册
    @RequestMapping("/register")
    public Result<User> Register(@RequestBody User user){
        System.out.println(user);
        return new Result<>(200,"success",user);
    }


}
