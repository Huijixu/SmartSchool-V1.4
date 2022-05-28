package com.huijixu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huijixu.bean.Grade;
import com.huijixu.mapper.GradeMapper;
import com.huijixu.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Transactional
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper , Grade> implements GradeService {
    @Override
    public IPage<Grade> getPageByCondition(Page<Grade> page, String gradeName) {

        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        //用户带条件查询
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name",gradeName);
        }

        //返回结果按照升序排序
        queryWrapper.orderByDesc(false,"id");

        //向数据库查询
        Page<Grade> gradePage = baseMapper.selectPage(page, queryWrapper);

        return gradePage;
    }
}
