package com.example.schoolshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.schoolshop.domain.Order;

import java.util.List;


public interface OrderService extends IService<Order> {

    List<Order> getAll();

    List<Order> getByUser();

    IPage<Order> getByPage(Integer start, Integer pageSize);

    IPage<Order> getPageByUser(Integer start, Integer pageSize);

}
