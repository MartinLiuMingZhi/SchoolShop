package com.example.schoolshop;

import com.example.schoolshop.mappar.UserMapper;
import com.example.schoolshop.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SchoolShopApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void testGetAllUser() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

}
