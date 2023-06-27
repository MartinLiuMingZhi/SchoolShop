package com.example.schoolshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
        for (Cart cart:carts) {
            sum+=cart.getTotalPrice();
        }

        return sum;
    }

    @Override
    public AddResponse cartAdd(String username, Long product_id,Long num) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userService.getBaseMapper().selectOne(userQueryWrapper);

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", user.getId());
        queryWrapper.eq("productId", product_id);
        if (this.baseMapper.selectOne(queryWrapper) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品已存在");
        }

        Cart cart = new Cart();
        cart.setUserid(user.getId());
        cart.setProductId(product_id);

        // 查询其他表中的信息
        Product product = productService.getById(product_id);

        // 设置其他属性
        cart.setQuantity(num);
        cart.setProductName(product.getName());
        cart.setUnitPrice(product.getPrice());
        cart.setTotalPrice(product.getPrice()*product.getStock());
        cart.setProductImage(product.getImage());
        boolean saveResult= this.save(cart);

        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }

        // 创建 AddResponse 对象，并将查询到的信息赋值
        AddResponse response = new AddResponse();
        response.setCart_id(cart.getCartId());
        response.setUser_id(cart.getUserid());
        response.setProduct_id(cart.getProductId());
        response.setProduct_name(product.getName());
        response.setProduct_image(product.getImage());
        response.setUnit_price(product.getPrice());
        response.setTotal_price(product.getPrice()*product.getStock());
        response.setQuantity(num);
        // 设置其他属性，如商品数量、商品单价等

        return response;

    }

    @Override
    public List<Cart> cartQuery(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userService.getBaseMapper().selectOne(userQueryWrapper);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",user.getId());

        List<Cart> list = this.baseMapper.selectList(queryWrapper);
        System.out.println(list.toString());
        return list;
    }

    @Override
    public List<Cart> cartPage(String username, Integer start, Integer pageSize) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userService.getBaseMapper().selectOne(userQueryWrapper);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",user.getId());
        IPage<Cart> page = new Page<>(start,pageSize);
        IPage<Cart> resultPage = this.baseMapper.selectPage(page, queryWrapper); // 执行数据库查询操作
        return resultPage.getRecords();
    }

    @Override
    public Boolean cartUpdate(String username, Long productId, Long quantity) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userService.getBaseMapper().selectOne(userQueryWrapper);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", user.getId());
        queryWrapper.eq("productId", productId);
        Cart cart = this.baseMapper.selectOne(queryWrapper);

        if (cart == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"购物车条目不存在");
        }

        Double unitPrice = cart.getUnitPrice();
        Double total = cart.getTotalPrice()*quantity;
        Date updateTime = new Date();

        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", user.getId());
        updateWrapper.eq("productId", productId);
        updateWrapper.set("quantity", quantity);
        updateWrapper.set("totalPrice", total);
        updateWrapper.set("updatedTime",updateTime);

        int rows = this.baseMapper.update(null, updateWrapper);
        return rows > 0;
    }

    @Override
    public Boolean cartDelete(String username, Long product_id) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userService.getBaseMapper().selectOne(userQueryWrapper);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",user.getId());
        queryWrapper.eq("productId",product_id);
        Cart cart = this.baseMapper.selectOne(queryWrapper);
        if (cart == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"购物车条目不存在");
        }
        int rows = this.baseMapper.delete(queryWrapper);
        return rows > 0;
    }

    @Override
    public Boolean DeleteAll(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userService.getBaseMapper().selectOne(userQueryWrapper);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",user.getId());
        Cart cart = this.baseMapper.selectOne(queryWrapper);
        if (cart == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"购物车不存在");
        }
        int rows = this.baseMapper.delete(queryWrapper);
        return rows > 0;
    }


}
