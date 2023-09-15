package com.example.schoolshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.schoolshop.domain.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
