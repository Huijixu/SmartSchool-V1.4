package com.huijixu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huijixu.bean.Admin;
import com.huijixu.service.AdminService;
import com.huijixu.util.MD5;
import com.huijixu.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Api(tags = "This is AdminController")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    AdminService adminService;

    /*
        localhost:9002/sms/adminController/getAllAdmin/1/3?    adminName=a
     */
    @ApiOperation("分页带条件查询管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("管理员名字") String adminName
    ){
        Page<Admin> pageParam =new Page<Admin>(pageNo,pageSize);

        IPage<Admin> iPage=adminService.getAdminsByOpr(pageParam,adminName);
        return Result.ok(iPage);
    }

    @ApiOperation("增加或修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result  saveOrUpdateAdmin(
            @ApiParam("JSON格式的Admin对象") @RequestBody Admin admin
    ){
        Integer id = admin.getId();
        if (id==null || 0 ==id) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();

    }

    /*
        localhost:9002/sms/adminController/deleteAdmin List<Integer> ids
     */
    @ApiOperation("删除管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @ApiParam("要删除的管理员的多个ID的JSON集合") @RequestBody List<Integer> ids
    ){
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
