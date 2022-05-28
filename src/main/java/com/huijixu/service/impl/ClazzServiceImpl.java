package com.huijixu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huijixu.bean.Clazz;
import com.huijixu.bean.Grade;
import com.huijixu.mapper.ClazzMapper;
import com.huijixu.service.ClazzService;
import freemarker.template.utility.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */

@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper , Clazz> implements ClazzService  {

    @Override
    public IPage<Clazz> getClazzsByOpr(Clazz clazz, Page<Clazz> clazzPage) {

        QueryWrapper<Clazz> clazzQueryWrapper = new QueryWrapper<>();

        //从json格式封装中中取出班级名
        String grade_name = clazz.getGrade_name();
        //班级查询条件非空按条件查询
        if (!StringUtils.isEmpty(grade_name)) {
            clazzQueryWrapper.like("name",grade_name);
        }
        //按id升序
        clazzQueryWrapper.orderByDesc(false,"id");
        //向数据库查询
        Page<Clazz> clazzPage1 = baseMapper.selectPage(clazzPage, clazzQueryWrapper);

        return clazzPage1;
    }
}
