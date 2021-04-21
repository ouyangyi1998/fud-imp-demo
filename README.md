   # 基于Spring Boot2.0开发的文件上传项目
## 1.技术汇总

- 项目使用的框架或者插件
  - 后端方向：SpringBoot，Mybatis，Swagger2，Ehcache，Shiro，FastJson
  - 数据库方向：MySQL，Druid
  - 前端方向：Thymeleaf，BootStrap4，Ajax
  - css模块：SweetAlert2.css，Spinner.css，Animate.css，Font-awesome.css，Bootsnav.css，Perfect-scrollbar.css
  - 日志模块：SLF4J，Logback
  - js模块：JQuery，C3.js，Morris.js，Raphael.js
  - 其他：https，上传文件采用混合加密（aes+rsa）

- 具体技术
  - Apache Shiro权限开发,包括前台shiro隐藏拦截+后台shiro注解开发
  - Spring Boot2.0异常全局处理(前台基于ajax开发+后台json回写)
  - 基于SimpleHash实现的MD5数据库写入密码加密，通过监听session生成销毁简单统计在线人数
  - 通过swagger2实现可视化接口测试，通过Druid实现后台SQL分析
  - c3.js，morris.js开发前台折线图+饼图，提升前台页面美观度+数据直观度
  - 利用aes加密文件分片，再使用rcs对于aes秘钥进行加密，在后端把文件分片合为multipartfile
  - 利用font-awesome.css，animate.css，spinner.css，sweetalert2.css等开发前台页面
  - 基于echache实现权限，登录信息缓存，利用自定义shiro filter实现单点登录


## 2.项目配置
- 项目数据库放在sql文件里面，跑一下1.sql
- application-test.properties 数据库账号密码改成自己的
- applcation-test.properties 文件上传位置和备份位置基于自己需求进行修改
- 启动SpringBoot项目


## 3.图片
![登录](https://github.com/ouyangyi1998/MyPostPicture/blob/master/picture/%E6%88%AA%E5%B1%8F2021-04-02%2017.51.58.png)

