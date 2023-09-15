package com.example.schoolshop.controller;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.domain.Order;
import com.example.schoolshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取所有订单信息
     * @return
     */
    @GetMapping("/getAll")
    public BaseResponse<List<Order>> getAll(){
         List<Order> orders = orderService.getAll();
        return ResultUtils.success(orders);
    }

    /**
     * 用户获取订单信息
     * @return
     */
    @GetMapping("/getByUser")
    public BaseResponse<List<Order>> getByUser(){
        List<Order> orders = orderService.getByUser();
        return ResultUtils.success(orders);
    }

    /**
     * 所有订单信息分页查询
     * @param start
     * @param pageSize
     * @return
     */
    @GetMapping("/getPage")
    public BaseResponse<IPage> getByPage(Integer start, Integer pageSize){
        IPage<Order> pages = orderService.getByPage(start,pageSize);
        return ResultUtils.success(pages);
    }

    /**
     * 用户订单信息分页查询
     * @param start
     * @param pageSize
     * @return
     */
    @GetMapping("/getPageByUser")
    public BaseResponse<IPage> getPageByUser(Integer start,Integer pageSize){
        IPage<Order> page = orderService.getPageByUser(start,pageSize);
        return ResultUtils.success(page);
    }
}
