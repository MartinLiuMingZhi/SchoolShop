package com.example.schoolshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.schoolshop.domain.Cart;
import com.example.schoolshop.domain.Product;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.model.cart.AddResponse;

import java.util.List;

/**
 * <p>
 *  购物车服务类
 * </p>
 *
 * @author martin liu  & sq
 */
public interface CartService extends IService<Cart> {
    Double sum();

    AddResponse cartAdd(String username,Long product_id,Long num);

    List<Cart> cartQuery(String username);

    List<Cart> cartPage(String username,Integer start,Integer pageSize);

    Boolean cartUpdate(String username,Long product_id,Long quantity);

    Boolean cartDelete(String username,Long product_id);

    Boolean DeleteAll(String username);
}
