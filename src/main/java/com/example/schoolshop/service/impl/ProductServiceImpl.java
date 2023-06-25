package com.example.schoolshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.domain.Product;
import com.example.schoolshop.domain.User;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.mappar.ProductMapper;
import com.example.schoolshop.model.product.AddProductResponse;
import com.example.schoolshop.model.user.RegisterResponse;
import com.example.schoolshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  产品服务类
 * </p>
 *
 * @author martin liu  & sq
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>implements ProductService {

    @Override
    public long count() {
//        IPage page = new Page();
//        return page.getTotal();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        return this.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<Product> page(Integer start, Integer pageSize) {
        IPage page = new Page(start,pageSize);

        return page.getRecords();
    }

    @Override
    public AddProductResponse addProduct(String name, String description, Double price, String image, String type, Long stock) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        if (this.baseMapper.selectOne(queryWrapper) !=  null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"商品已存在");
        }
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);
        product.setType(type);
        product.setStock(stock);
        Date date = new Date();
        product.setDate(date);
        boolean saveResult = this.save(product);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setId(product.getId());
        return addProductResponse;
    }
}
