package com.huijixu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huijixu.bean.Grade;
import com.huijixu.service.GradeService;
import com.huijixu.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//document.querySelector("head > link:nth-child(7)")
/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */

@Api(tags = "This is Grade Controller")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    GradeService gradeService;

    /*
     localhost:9002/sms/gradeController/getGrades
     */
    @ApiOperation("获取年级列表")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> list = gradeService.list();
        return Result.ok(list);
    }

    /*
    path: "/sms/gradeController/deleteGrade"
     */
    @ApiOperation("删除与批量删除")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("批量删除的id 集合")
            @RequestBody
            List<Integer> gradeIdlist
            ){
        //
        gradeService.removeByIds(gradeIdlist);
        return Result.ok();
    }


    /*
    /sms/gradeController/saveOrUpdateGrade HTTP/1.1
     */
    @ApiOperation("更新年纪信息")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @RequestBody
            Grade grade
    ){
        //IServiceImpl实现
        /*
         public boolean saveOrUpdate(T entity) {
        if (null == entity) {
            return false;
        } else {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!", new Object[0]);
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!", new Object[0]);
            Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
            return !StringUtils.checkValNull(idVal) && !Objects.isNull(this.getById((Serializable)idVal)) ? this.updateById(entity) : this.save(entity);
        }
    }
         */
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }


    /*
        localhost:9002/sms/gradeController/getGrades/1/3?gradeName=%E4%B8%89
     */
    @ApiOperation("查询年级信息")
    @GetMapping("/getGrades/{pageStart}/{pageSize}")
    public Result getGrades(
            @ApiParam("起始页")    @PathVariable("pageStart")
            Integer pageStart,

            @ApiParam("每页展示条数") @PathVariable("pageSize")
            Integer pageSize,

            @ApiParam("条件查询参数")
//            @RequestParam("gradeName")
            String gradeName
    ){
        //mybatis-plus分页查询接口
        Page<Grade> page = new Page<>(pageStart, pageSize);

        //service返回分页结果
        IPage<Grade> gradeIPage = gradeService.getPageByCondition(page,gradeName);

        return Result.ok(gradeIPage);
    }
}
