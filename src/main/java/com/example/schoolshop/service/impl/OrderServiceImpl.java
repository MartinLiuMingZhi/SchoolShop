package com.example.schoolshop.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.schoolshop.common.ErrorCode;
import com.example.schoolshop.domain.Order;
import com.example.schoolshop.exception.BusinessException;
import com.example.schoolshop.mapper.OrderMapper;
import com.example.schoolshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public List<Order> getAll() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(null);
        List<Order> orders= this.baseMapper.selectList(queryWrapper);
        return orders;
    }

    @Override
    public List<Order> getByUser() {
        if (!StpUtil.isLogin()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
        }
        Long userId = Long.parseLong(StpUtil.getLoginId().toString());
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        List<Order> orders = this.baseMapper.selectList(queryWrapper);
        return orders;
    }

    @Override
    public IPage<Order> getByPage(Integer start, Integer pageSize) {
        IPage<Order> page = new Page<>(start,pageSize);
        IPage<Order> list = this.baseMapper.selectPage(page,null);
        return list;
    }

    @Override
    public IPage<Order> getPageByUser(Integer start, Integer pageSize) {
        if (!StpUtil.isLogin()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"用户未登录");
        }
        Long userId = Long.parseLong(StpUtil.getLoginId().toString());
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        IPage<Order> page = new Page<>(start,pageSize);
        IPage<Order> list = this.baseMapper.selectPage(page,queryWrapper);
        return list;
    }
}
