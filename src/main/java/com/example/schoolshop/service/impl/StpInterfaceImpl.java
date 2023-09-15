//package com.example.schoolshop.service.impl;
//
//import cn.dev33.satoken.stp.StpInterface;
//import com.example.schoolshop.domain.Manager;
//import com.example.schoolshop.mapper.ManagerMapper;
//import com.example.schoolshop.service.ManagerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 自定义权限加载接口实现类
// */
//@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
//public class StpInterfaceImpl implements StpInterface {
//
//    @Autowired
//    private ManagerMapper mapper;
//    /**
//     * 返回一个账号所拥有的权限码集合
//     */
//    @Override
//    public List<String> getPermissionList(Object loginId, String loginType) {
//
//        return null;
//    }
//
//    /**
//     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
//     */
//    @Override
//    public List<String> getRoleList(Object loginId, String loginType) {
//        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
//
//        Integer id= Integer.parseInt((String) loginId);
//        String role = mapper.selectById(id).getRole();
//
//        List<String> list = new ArrayList<String>();
//        list.add(role);
//
//        return list;
//    }
//
//}
