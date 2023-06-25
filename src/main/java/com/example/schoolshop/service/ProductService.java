package com.example.schoolshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.schoolshop.domain.Product;
import com.example.schoolshop.model.product.AddProductResponse;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  产品服务类
 * </p>
 *
 * @author martin liu  & sq
 */
public interface ProductService extends IService<Product> {
//    /**
//     *商品处理
//     *
//     * @param name 商品名称
//     * @param description 商品描述
//     * @param price 商品价格
//     * @param image 商品图片
//     * @param type  商品类型
//     * @param stock 商品库存
//     * @param date  入库日期
//     * @param start 当前页
//     * @param pageSize 页数
//     * @return id
//     */
    long count();

    List<Product> page(Integer start,Integer pageSize);

    AddProductResponse addProduct(String name, String description, Double price, String image, String type, Long stock);


}
