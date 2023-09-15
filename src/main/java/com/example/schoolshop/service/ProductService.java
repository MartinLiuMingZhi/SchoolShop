package com.example.schoolshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.schoolshop.domain.Product;
import com.example.schoolshop.model.product.AddProductResponse;
import com.example.schoolshop.model.product.DeleteProductResponse;


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

    long count();

    IPage<Product> page(Integer start, Integer pageSize);

    /**
     *
     * @param name
     * @param description
     * @param price
     * @param image
     * @param type
     * @param stock
     * @return
     */
    AddProductResponse addProduct(String name, String description, Double price, String image, String type, Long stock);

    DeleteProductResponse deleteProduct(List<Integer> id);

    List<Product> query(List<Integer> id);

    Boolean update(Long id,String name, String description, Double price, String image, String type, Long stock);

    List<Product> queryName(String name);

    List<Product> fuzzy_query(String name);

    Boolean deleteProduct(Long id);

    List<Product> queryAll();
}
