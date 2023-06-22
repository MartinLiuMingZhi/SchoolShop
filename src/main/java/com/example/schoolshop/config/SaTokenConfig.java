package com.example.schoolshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 配置类
 */
@Configuration
public class SaTokenConfig {
    // Sa-Token 参数配置，参考文档：https://sa-token.cc
    // 此配置会与 application.yml 中的配置合并 （代码配置优先）
    @Autowired
    public void configSaToken(cn.dev33.satoken.config.SaTokenConfig config) {
        config.setTokenName("token");             // token名称 (同时也是cookie名称)
        config.setTimeout(30 * 24 * 60 * 60);       // token有效期，单位s 默认30天
        config.setActivityTimeout(-1);              // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setIsConcurrent(false);               // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsShare(true);                    // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token) 
        config.setTokenStyle("uuid");               // token风格 
        config.setIsLog(false);                     // 是否输出操作日志
    }
}
