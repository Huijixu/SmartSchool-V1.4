package com.huijixu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huijixu.bean.Admin;
import com.huijixu.bean.LoginForm;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
public interface AdminService extends IService<Admin> {  //实现IService就实现了curd接口

    Admin login(LoginForm loginForm);

    /**
     * 返回首页信息
     * @param userId
     * @return
     */
    Admin getAdminById(Long userId);

    IPage<Admin> getAdminsByOpr(Page<Admin> pageParam, String adminName);
}
