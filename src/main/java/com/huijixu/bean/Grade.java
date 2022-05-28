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
//@AllArgsConstructor
//@NoArgsConstructor
@TableName(value = "tb_grade")
public class Grade {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String manager;
    private String introducation;
    private String email;
    private String telephone;
}
