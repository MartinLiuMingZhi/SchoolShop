package com.example.schoolshop;

import com.example.schoolshop.domain.User;
import com.example.schoolshop.mappar.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SchoolShopApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void select(){
		List<User> users = userMapper.selectList(null);
		System.out.println(users);
	}
	@Test
	void contextLoads() {
	}

}
