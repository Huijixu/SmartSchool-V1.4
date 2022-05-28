package com.huijixu.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_student")
public class Student {

    @TableId(value = "id",type = IdType.AUTO)
    private  Integer id;
    private  String name;
    private  String gender;
    private  String sno;
    private  String password;
    private  String telephone;
    private  String email;
    private  String address;
    private  String introducation;
    private  String portrait_path;
    private  String clazz_name;
}
