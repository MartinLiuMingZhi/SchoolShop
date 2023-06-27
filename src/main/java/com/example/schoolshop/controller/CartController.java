package com.example.schoolshop.controller;

import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.domain.Cart;
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
        AddResponse response = cartService.cartAdd(addRequest.getUsername(), addRequest.getProduct_id(), addRequest.getNum());
        return ResultUtils.success(response);
    }

    /**
     * 查询购物车
     * @param username
     * @return
     */
    @GetMapping("/query")
    public BaseResponse<List<Cart>> query(String username){
        List<Cart> list = cartService.cartQuery(username);
        return ResultUtils.success(list);
    }

    /**
     * 查询购物车
     * @param username
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<List<Cart>> page(String username,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize ){
        List<Cart> carts = cartService.cartPage(username, page, pageSize);
        return ResultUtils.success(carts);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> update(String username, Long productId, Long quantity){
        Boolean judge = cartService.cartUpdate(username, productId, quantity);
        return ResultUtils.success(judge);
    }
    /**
     * 删除购物车条目
     * @param username
     * @param product_id
     * @return
     */
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delete(String username,Long product_id){
        Boolean judge = cartService.cartDelete(username,product_id);
        return ResultUtils.success(judge);
    }

    @DeleteMapping("/deleteAll")
    public BaseResponse<Boolean> deleteAll(String username){
        Boolean judge = cartService.DeleteAll(username);
        return ResultUtils.success(judge);
    }
}
