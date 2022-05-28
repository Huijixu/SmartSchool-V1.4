package com.huijixu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huijixu.bean.LoginForm;
import com.huijixu.bean.Student;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */

public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    IPage<Student> getStudentByOpr(Page<Student> studentPage, Student student);
}
