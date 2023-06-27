package com.example.schoolshop.controller;

import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.service.CartService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Slf4j  //log的注解
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    /**
     * 购物车商品总价
     * @return
     */
    public BaseResponse<Double> sum(){
        Double sum = cartService.sum();
        return ResultUtils.success(sum);
    }


}
