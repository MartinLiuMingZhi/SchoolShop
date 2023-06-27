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
    public BaseResponse<AddResponse> carAdd(AddRequest addRequest){
        AddResponse response = cartService.cartAdd(addRequest.getUser_id(), addRequest.getProduct_id(), addRequest.getNum());
        return ResultUtils.success(response);
    }

    /**
     * 查询购物车
     * @param id
     * @return
     */
    @GetMapping("/query")
    public BaseResponse<List<Cart>> query(Long id){
        List<Cart> list = cartService.cartQuery(id);
        return ResultUtils.success(list);
    }

    /**
     * 查询购物车
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<List<Cart>> page(Long id,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize ){
        List<Cart> carts = cartService.cartPage(id, page, pageSize);
        return ResultUtils.success(carts);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> update(Long userId, Long productId, Long quantity){
        Boolean judge = cartService.cartUpdate(userId, productId, quantity);
        return ResultUtils.success(judge);
    }
    /**
     * 删除购物车条目
     * @param user_id
     * @param product_id
     * @return
     */
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delete(Long user_id,Long product_id){
        Boolean judge = cartService.cartDelete(user_id,product_id);
        return ResultUtils.success(judge);
    }

    @DeleteMapping("/deleteAll")
    public BaseResponse<Boolean> deleteAll(Long user_id){
        Boolean judge = cartService.DeleteAll(user_id);
        return ResultUtils.success(judge);
    }
}
