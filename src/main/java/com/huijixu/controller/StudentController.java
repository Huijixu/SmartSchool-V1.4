package com.huijixu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huijixu.bean.Student;
import com.huijixu.service.StudentService;
import com.huijixu.util.MD5;
import com.huijixu.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.List;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Api(tags = "This is Student Controller")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    StudentService studentService;

    /*
        localhost:9002/sms/studentController/addOrUpdateStudent
     */
    @ApiOperation("修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
            @RequestBody
            Student student
    ){
        /*
        这里要注意添加有填写密码选项 ，后端要做加密处理 ，修改则不用 ，两者通过是否提供id来区别
         */
        //新增学生由于是主键自增所以json数组没有id
        if (student.getId() == null){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }
    /*
        localhost:9002/sms/studentController/delStudentById
     */
    @ApiOperation("删除学生")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @RequestBody
            List<Integer> ids
    ){
        studentService.removeByIds(ids);
        return Result.ok();
    }

    /*
       localhost:9002/sms/studentController/getStudentByOpr/1/3
     */
    @ApiOperation("获取班级全部学生")
    @GetMapping("/getStudentByOpr/{pageStart}/{pageSize}")
    public Result getStudentByOpr(
            @PathVariable("pageStart")
            Integer pageStart,
            @PathVariable("pageSize")
            Integer pageSize,

            Student student
    ){
        Page<Student> studentPage = new Page<>(pageStart,pageSize);

        IPage<Student> studentIPage = studentService.getStudentByOpr(studentPage,student);

        return Result.ok(studentIPage);
    }
}
