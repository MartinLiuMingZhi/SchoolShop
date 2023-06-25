package com.example.schoolshop.controller;

import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.domain.Product;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.model.product.AddProductRequest;
import com.example.schoolshop.model.product.AddProductResponse;
import com.example.schoolshop.model.product.DeleteProductResponse;
import com.example.schoolshop.service.ProductService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j  //log的注解
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 分页
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<List<Product>> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
        List<Product> list = productService.page(page, pageSize);
        return ResultUtils.success(list);
    }

    /**
     * 添加商品
     * @param addProductRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<AddProductResponse> add(@RequestBody AddProductRequest addProductRequest){
        if (addProductRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = addProductRequest.getName();
        String description = addProductRequest.getDescription();
        Double price = addProductRequest.getPrice();
        String image = addProductRequest.getImage();
        String type = addProductRequest.getType();
        Long stock = addProductRequest.getStock();
        AddProductResponse addProductResponse = productService.addProduct(name,description,price,image,type,stock);
        return ResultUtils.success(addProductResponse);
    }

    /**
     * 批量删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public BaseResponse<DeleteProductResponse> add(@PathVariable List<Integer> id){
        return ResultUtils.success(null);
    }
}
