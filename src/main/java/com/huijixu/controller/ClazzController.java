package com.huijixu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huijixu.bean.Clazz;
import com.huijixu.bean.Grade;
import com.huijixu.service.ClazzService;
import com.huijixu.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import java.util.List;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Api(tags = "This is ClazzController")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    ClazzService clazzService;

    /*
        localhost:9002/sms/clazzController/getClazzs
     */
    @ApiOperation("获取班级列表")
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        //mybatis-plus IService接口方法
        List<Clazz> list = clazzService.list();
        return Result.ok(list);
    }
    /*
        localhost:9002/sms/clazzController/deleteClazz
     */
    @ApiOperation("删除批量删除")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(
            @RequestBody
                    /** 从前端请求得知是传入的一组班级id实现的删除功能
                     *  [1, 2]
                     *   0: 1
                     *   1: 2
                     */
                    List<Integer> ids
    ) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    /*
        localhost:9002/sms/clazzController/saveOrUpdateClazz
     */
    @ApiOperation("更新班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @RequestBody  // 解析json格式的数据
                    Clazz clazz
    ) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }


    /*
     localhost:9002/sms/clazzController/getClazzsByOpr/1/3
     */
    @ApiOperation("班级管理界面的年级按条件查询")
    @GetMapping("/getClazzsByOpr/{pageStart}/{pageSize}")
    public Result getClazzsByOpr(
            @PathVariable("pageStart")
                    Integer pageStart,
            @PathVariable("pageSize")
                    Integer pageSize,

            @ApiParam("条件查询参数")
                    Clazz clazz
    ) {
        //分页查询
        Page<Clazz> clazzPage = new Page<>(pageStart, pageSize);


        IPage<Clazz> gradeIPage = clazzService.getClazzsByOpr(clazz, clazzPage);

        return Result.ok(gradeIPage);
    }

}
