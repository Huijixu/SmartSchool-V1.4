package com.huijixu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huijixu.bean.Admin;
import com.huijixu.bean.LoginForm;
import com.huijixu.bean.Teacher;
import com.huijixu.mapper.TeacherMapper;
import com.huijixu.service.TeacherService;
import com.huijixu.util.MD5;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Transactional
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {



    @Override
    public IPage<Teacher> getTeachersByOpr(Page<Teacher> paraParam, Teacher teacher) {
        QueryWrapper queryWrapper =new QueryWrapper();
        if (!StringUtils.isEmpty(teacher.getName())){
            queryWrapper.like("name",teacher.getName());
        }
        if (!StringUtils.isEmpty(teacher.getClazz_name())){
            queryWrapper.eq("clazz_name",teacher.getClazz_name());
        }
        queryWrapper.orderByDesc("id");

        Page<Teacher> page = baseMapper.selectPage(paraParam, queryWrapper);
        return page;
    }



    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Teacher login(LoginForm loginForm) {

        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();

        teacherQueryWrapper.eq("name", loginForm.getUsername());
        teacherQueryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        return baseMapper.selectOne(teacherQueryWrapper);


    }
}
