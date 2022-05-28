package com.huijixu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huijixu.bean.Clazz;
import com.huijixu.bean.Grade;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazzsByOpr(Clazz clazz, Page<Clazz> clazzPage);
}
