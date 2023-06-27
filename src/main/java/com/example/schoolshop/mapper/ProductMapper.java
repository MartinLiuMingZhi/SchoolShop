package com.example.schoolshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.schoolshop.domain.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
