package com.example.schoolshop.controller;

import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.domain.Product;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.model.product.AddProductRequest;
import com.example.schoolshop.model.product.AddProductResponse;
import com.example.schoolshop.model.product.DeleteProductResponse;
import com.example.schoolshop.model.product.UpdateProductRequest;
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
     * 获取商品总数
     * @return
     */
    @GetMapping("/count")
    public BaseResponse<Long> count(){
        Long l = productService.count();
        return ResultUtils.success(l);
    }
    /**
     * 分页
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<List<Product>> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
        List<Product> list = productService.page(page, pageSize);
        System.out.println(list.toString());
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
    public BaseResponse<DeleteProductResponse> delete(@PathVariable List<Integer> id){
        DeleteProductResponse deleteProductResponse = productService.deleteProduct(id);
        return ResultUtils.success(deleteProductResponse);
    }

    /**
     * 批量查询
     * @param id
     * @return
     */
    @GetMapping("/query/{id}")
    public BaseResponse<List<Product>> query(@PathVariable List<Integer> id){
        List<Product> product = productService.query(id);
        return  ResultUtils.success(product);
    }

    /**
     * 商品名称查询
     * @param name
     * @return
     */
    @GetMapping("/query/name")
    public BaseResponse<List<Product>> queryName(String name){
        List<Product> product = productService.queryName(name);
        return ResultUtils.success(product);
    }

    /**
     * 模糊查询
     * @param name
     * @return
     */
    @GetMapping("/query/fuzzy")
    public BaseResponse<List<Product>> fuzzy_query(String name){
        List<Product> products = productService.fuzzy_query(name);
        return ResultUtils.success(products);
    }
    /**
     * 更新商品
     * @param updateProductRequest
     * @return
     */
    @PutMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody UpdateProductRequest updateProductRequest){
        String name = updateProductRequest.getName();
        String description = updateProductRequest.getDescription();
        Double price = updateProductRequest.getPrice();
        String image = updateProductRequest.getImage();
        String type = updateProductRequest.getType();
        Long stock = updateProductRequest.getStock();
        Boolean judge = productService.update(name,description,price,image,type,stock);
        return ResultUtils.success(judge);
    }
}
