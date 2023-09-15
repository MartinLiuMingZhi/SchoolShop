package com.example.schoolshop.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.domain.Cart;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.model.cart.AddRequest;
import com.example.schoolshop.model.cart.AddResponse;
import com.example.schoolshop.service.CartService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/sum")
    public BaseResponse<Double> sum(){
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        Double sum = cartService.sum();
        return ResultUtils.success(sum);
    }

    /**
     * 加入购物车
     * @param addRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<AddResponse> carAdd(@RequestBody AddRequest addRequest){
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        AddResponse response = cartService.cartAdd(addRequest.getProduct_id(), addRequest.getNum());
        return ResultUtils.success(response);
    }

    /**
     * 查询购物车
     * @return
     */
    @GetMapping("/query")
    public BaseResponse<List<Cart>> query(){
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        List<Cart> list = cartService.cartQuery();
        return ResultUtils.success(list);
    }

    /**
     * 查询购物车
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<List<Cart>> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize ){
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        List<Cart> carts = cartService.cartPage(page, pageSize);
        return ResultUtils.success(carts);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> update(Long productId, Long quantity){
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        Boolean judge = cartService.cartUpdate( productId, quantity);
        return ResultUtils.success(judge);
    }
    /**
     * 删除购物车条目
     * @param product_id
     * @return
     */
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delete(Long product_id){
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        Boolean judge = cartService.cartDelete(product_id);
        return ResultUtils.success(judge);
    }

    /**
     * 删除所有
     * @return
     */
    @DeleteMapping("/deleteAll")
    public BaseResponse<Boolean> deleteAll(){
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        Boolean judge = cartService.DeleteAll();
        return ResultUtils.success(judge);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/queryAll")
    public BaseResponse<List<Cart>> queryAll(){
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        List<Cart> carts = cartService.QueryAll();
        return ResultUtils.success(carts);
    }
}
