package com.example.schoolshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.schoolshop.mapper")
@SpringBootApplication
public class SchoolShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolShopApplication.class, args);
    }

}
