package com.huijixu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huijixu.bean.Admin;
import com.huijixu.bean.LoginForm;
import com.huijixu.mapper.AdminMapper;
import com.huijixu.service.AdminService;
import com.huijixu.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Service(value = "adminServiceImpl")
@Transactional
/*
由于mybatis-plus接口的实现类太复杂 ，涉及底层 ，
所以有了ServiceImpl ,它实现了AdminService的接口方法
ServiceImpl类接受连个泛型参数：
        AdminMapper : 之前在spring 中的自动注入 ， Admin： 关联的数据库实体类
 */
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public IPage<Admin> getAdminsByOpr(Page<Admin> pageParam, String adminName) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)) {
            queryWrapper.like("name",adminName);
        }
        queryWrapper.orderByDesc("id");

        Page<Admin> page = baseMapper.selectPage(pageParam, queryWrapper);

        return page;
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);

        return baseMapper.selectOne(queryWrapper);
    }

    /*
          1、该实现类extend的 ServiceImpl接口实现了baseMapper接口 ，所以可以直接用CRUD
          2、baseMapper 的 查询方法selectOne需要实现类QueryWrapper接口的被封装条件的参数
          3、QueryWrapper的eq 犯法封装了select方法 ，只需提供数据库字段与前端参数 ，即可以做比较
         */
    @Override
    public Admin login(LoginForm loginForm) {

        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("name",loginForm.getUsername());
        //密码加密后在于数据库对比
        adminQueryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        /*
        注意这个baseMapper 是AdminMapper 里的 method
         */
         return  baseMapper.selectOne(adminQueryWrapper);

    }
}
