package com.example.schoolshop.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.schoolshop.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper //在运行时，会自动生成该接口的实现类对象(代理对象)，并且将该对象交给IOC容器管理
public interface UserMapper extends BaseMapper<User> {
}
