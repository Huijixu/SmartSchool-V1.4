package com.huijixu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huijixu.bean.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */

@Repository
public interface AdminMapper extends BaseMapper<Admin> {  //继承BaseMapper就默认实现了crud接口
}
