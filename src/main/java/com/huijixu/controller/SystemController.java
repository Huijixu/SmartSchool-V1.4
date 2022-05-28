package com.huijixu.controller;

import com.huijixu.bean.Admin;
import com.huijixu.bean.LoginForm;
import com.huijixu.bean.Student;
import com.huijixu.bean.Teacher;
import com.huijixu.service.AdminService;
import com.huijixu.service.StudentService;
import com.huijixu.service.TeacherService;
import com.huijixu.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.JettyHttpHandlerAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author HuiJixu
 * @version 2.0
 * @since 2021.9
 */
@Api(tags = "This is some system functions controller")
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;

    /*
        localhost:9002/sms/system/updatePwd/admin/123
     */
    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @RequestHeader("token")
                    String token,  // 从消息头中取出token值
            @PathVariable("oldPwd")
                    String oldPwd,
            @PathVariable("newPwd")
                    String newPwd
    ) {
        //校验token是否有效
        if (JwtHelper.isExpiration(token)) {
            return Result.fail().message("token失效 ，请重新提交表单");
        }
        //获取用户Id&userType
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);

        //密文转换
        oldPwd = MD5.encrypt(oldPwd);


        switch (userType) {
            case 1:
                Admin adminById = adminService.getAdminById(userId);
                //判断密码是否正确  别忘记明文转换
                if (adminById.getPassword().equals(oldPwd) ){
                    //判断新密码长度
                    if (newPwd.length() < 5 ){
                        return  Result.fail().message("密码长度过短");
                    }else {
                        adminById.setPassword(MD5.encrypt(newPwd) );
                        adminService.saveOrUpdate(adminById);
                    }

                }else {
                    return Result.fail().message("原密码错误！请重试");
                }
                break;
            case 2:
                Student studentByID = studentService.getStudentById(userId);
                //判断密码是否正确  别忘记明文转换
                if (studentByID.getPassword().equals(oldPwd) ){
                    if (newPwd.length() < 5 ){
                        return  Result.fail().message("密码长度过短");
                    }else {
                        studentByID.setPassword(MD5.encrypt(newPwd) );
                        studentService.saveOrUpdate(studentByID);
                    }

                }else {
                    return Result.fail().message("原密码错误！请重试");
                }
                break;
            case 3:
                Teacher teacherById = teacherService.getTeacherById(userId);
                //判断密码是否正确  别忘记明文转换
                if (teacherById.getPassword().equals(oldPwd) ){
                    if (newPwd.length() < 5 ){
                        return  Result.fail().message("密码长度过短");
                    }else {
                        teacherById.setPassword(MD5.encrypt(newPwd) );
                        teacherService.saveOrUpdate(teacherById);
                    }

                }else {
                    return Result.fail().message("原密码错误！请重试");
                }
                break;
        }

        return Result.ok();
    }


    /*
       localhost:9002/sms/system/headerImgUpload
    */
    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile multipartFile,
            HttpServletRequest request
    ) {

        //生成图片名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(i));

        // 保存文件 将文件发送到第三方/独立的图片服务器上,
        String targetPath =
                "。。。。。。。。。。。。。。".concat(newFileName);

        //将前端拿到的图片数据传到targetPath
        try {
            multipartFile.transferTo(new File(targetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 响应给前端图片的远程保存路径
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }

    @ApiOperation("响应首页信息")
    @GetMapping("/getInfo")
    public Result getHomeInfoByToken(
            @RequestHeader("token") //从请求头中获取json格式的token信息 ，辨别用户 ，返回对应用户信息
                    String token
    ) {

        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            //快速构建消息
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }

        //token未过期：
        /*
            从客户端token解析出UserId  ，为后面判断类型
            具体取出什么东西其实是按照接口文档来的
            其实就是根据前端展示的需要 ，后端从token解析出必要参数 ，从数据库查出来后响应给前端
         */
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        //分类返回相应内容

        //盛放相应结果
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType", 1);   //key名不能变，商量好的，前端根据这个名字写EI表达式展示
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType", 2);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType", 3);
                map.put("user", teacher);
                break;
        }
        return Result.ok(map);
    }

    @ApiOperation("登录验证")
    @PostMapping("/login")
    public Result login(
            @ApiParam("拿到用户输入的里的验证码")
            @RequestBody   //用json格式接受前端携带回来的值
                    LoginForm loginForm,

            @ApiParam(" 拿到session 里的验证码")
                    HttpServletRequest request) {
        //获取用户输入与 后端验证码
        HttpSession session = request.getSession();
        String sessionVerifiedCode = (String) session.getAttribute("verifiedCode");

        String userCdoe = loginForm.getVerifiedCode();

        //  判断session是否过期
        if ("".equals(sessionVerifiedCode) || null == sessionVerifiedCode) {   // session域默认 有效期为30min
            return Result.fail().message("验证码失效啦 ，请重试");
        }

        //判断用户输入验证码
        if (sessionVerifiedCode.equalsIgnoreCase(userCdoe))
        {
            return Result.fail().message("验证码错误 ，请重试");
        }

        //从session域移除验证码
        request.getSession().removeAttribute("verifiedCode");

        //分类查验用户
        int userType = loginForm.getUserType();

        //存储相应数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    //admin 非空表示有此用户
                    if (admin != null) {
                        //相应生成待用户id &用户 type token到客户端 ，便于前端展示
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    //用Result 封装相应信息
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());//  用户名或密码错误
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);

                    if (student != null) {
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }


            case 3:

                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (teacher != null) {
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("没有该用户！");
    }


    @ApiOperation("返回登录界面图片验证码值")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request,
                                   HttpServletResponse response) {
        /*
        先拿图片再拿验证码
        因为生成顺序在这里
        反了报空指针
         */
        // 获取验证图片
        BufferedImage verifiedCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();

        // 获取图片上的验证码
        String verifiedCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //获取session
        HttpSession session = request.getSession();
        // 将验证码文本放入session域,为下一次验证做准备
        session.setAttribute("verifiedCode", verifiedCode);  //后面取session里的验证码要和这里的名一致

        //图片响应到浏览器
        try {
            ImageIO.write(verifiedCodeImage, "PNG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
