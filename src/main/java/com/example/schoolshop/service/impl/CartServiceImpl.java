package com.example.schoolshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.domain.Cart;
import com.example.schoolshop.domain.Product;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.mapper.CartMapper;
import com.example.schoolshop.mapper.ProductMapper;
import com.example.schoolshop.model.cart.AddResponse;
import com.example.schoolshop.service.CartService;
import com.example.schoolshop.service.ProductService;
import com.example.schoolshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper,Cart> implements CartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    @Override
    public Double sum() {
        List<Cart> carts = this.baseMapper.selectList(null);
        double sum = 0;
        for (Cart cat:carts) {
            sum+=cat.getTotal_price();
        }

        return sum;
    }

    @Override
    public AddResponse cartAdd(Long user_id, Long product_id,Long num) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user_id);
        queryWrapper.eq("product_id", product_id);
        if (this.getOne(queryWrapper) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品已存在");
        }

        Cart cart = new Cart();
        cart.setUser_id(user_id);
        cart.setProduct_id(product_id);

        // 查询其他表中的信息
        Product product = productService.getById(product_id);
        User user = userService.getById(user_id);
        // 设置其他属性
        cart.setQuantity(num);
        cart.setProduct_name(product.getName());
        cart.setUnit_price(product.getPrice());
        cart.setTotal_price(product.getPrice()*product.getStock());
        cart.setProduct_image(product.getImage());
        this.save(cart);



        // 创建 AddResponse 对象，并将查询到的信息赋值
        AddResponse response = new AddResponse();
        response.setCart_id(cart.getCart_id());
        response.setUser_id(cart.getUser_id());
        response.setProduct_id(cart.getProduct_id());
        response.setProduct_name(product.getName());
        response.setProduct_image(product.getImage());
        // 设置其他属性，如商品数量、商品单价等

        return response;


    }
}
