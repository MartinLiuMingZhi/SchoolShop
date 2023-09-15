package com.example.schoolshop.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.schoolshop.domain.Manager;
import com.example.schoolshop.model.manager.ChangePassword;
import com.example.schoolshop.model.manager.ForgetPassword;

public interface ManagerService extends IService<Manager> {

    Manager login(String username, String password);

    Manager register(String username, String password,String checkPassword);

    Manager getManager();

    IPage<Manager> page(Integer start, Integer pageSize);

    Boolean Update(Manager manager);

    Boolean UpdatePassword(ChangePassword changePassword);

    Boolean resetPassword(ForgetPassword forgetPassword);
}
