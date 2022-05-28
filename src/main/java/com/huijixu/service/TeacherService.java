package com.huijixu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huijixu.bean.LoginForm;
import com.huijixu.bean.Teacher;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage<Teacher> getTeachersByOpr(Page<Teacher> paraParam, Teacher teacher);

}
