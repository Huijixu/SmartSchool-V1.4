package com.huijixu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huijixu.bean.Admin;
import com.huijixu.bean.LoginForm;
import com.huijixu.bean.Student;
import com.huijixu.mapper.StudentMapper;
import com.huijixu.service.StudentService;
import com.huijixu.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Transactional
@Service

public class StudentServiceImpl extends ServiceImpl<StudentMapper , Student> implements StudentService {

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> studentPage, Student student) {

        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();

        //按名字查学生
        if (!StringUtils.isEmpty(student.getName())) {
            studentQueryWrapper.like("name",student.getName());
        }

        //按班级查
        if (!StringUtils.isEmpty(student.getClazz_name())) {
            studentQueryWrapper.like("name",student.getClazz_name());
        }
        studentQueryWrapper.orderByDesc(false,"id");

        IPage<Student> studentIPage = baseMapper.selectPage(studentPage,studentQueryWrapper);
      return studentIPage;

    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("name",loginForm.getUsername());
        //密码加密后在于数据库对比
        studentQueryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        return  baseMapper.selectOne(studentQueryWrapper);

    }
}
