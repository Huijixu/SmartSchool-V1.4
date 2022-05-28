package com.huijixu.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Data   //get set 方法
@AllArgsConstructor  // 有参数构造
@NoArgsConstructor  //无参构造
@TableName("tb_admin")  // Mybatis  关联数据库表名
public class Admin {

    //主键关联
    /**
     * 这里一定要注意！！！
     * j
     * 在数据库有主键自增时
     * 一定要在注解里声明自增
     * 不然在执行    update&Save     操作时
     * mybatis-plus 的IService接口的saveOrUpdate方法会根据有无数据库提供的主来选择操作
     * 但是当注解未设置主键时 ，id == null
     * 于是报ava.lang.IllegalArgumentException
     */
    @TableId(value = "id",type = IdType.AUTO)
    private  Integer id;
    private  String name;
    private  String gender;
    private  String password;
    private  String email;
    private  String telephone;
    private  String address;
    private  String portraitPath;    //头像路径
}
