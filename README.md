## 项目说明

### 前言

- 适合springboot初学者练手。
- 项目注释较为清晰 ，其中不乏部分新手易犯错误部分的注释提请和解析，对新手友好

### 项目内容和功能

- 应用场景：校园情景下学生、教师、管理员的CURD
- 验证登录，级部，班级，师生和管理员的curd

### 框架、数据库版本

- springboot 2.7.0
- mybatis-plus 3.3.1
- mysql 8

### 项目启动

倒入到本地idea后

- application.yml 中修改数据库链接信息
- 建库建表，本项目只提供建表源码，未提供数据插入源码，请读者自行编写
- 注释掉pom.xml里的 排除tomcat配置
- jdk设置为1.8
- 全局Unicode最好设为utf-8
- 修改com.huijixu.controller.StudentController目录下 headerImgUpload()的targetPath值为你本地项目target/classes/public/upload/的绝对路径

### 项目可能存在的bug

头像为简单上传采用保存到项目中，未使用第三方图片库 图片请求路径为本地绝对路径。可能导致图像无法加载

### 其他信息

- api文档地址

http://localhost:9002/swagger-ui.html
- 来自网络资源的前端代码，并做了部分修改 
