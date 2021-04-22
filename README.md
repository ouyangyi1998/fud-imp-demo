   # 基于Spring Boot2.0开发的文件上传项目
## 技术汇总

- 项目使用的框架或者插件
  - 后端方向：SpringBoot，Mybatis，Swagger2，Ehcache，Shiro，FastJson
  - 数据库方向：MySQL，Druid
  - 前端方向：Thymeleaf，BootStrap4，Ajax
  - css模块：SweetAlert2.css，Spinner.css，Animate.css，Font-awesome.css，Bootsnav.css，Perfect-scrollbar.css
  - 日志模块：SLF4J，Logback
  - js模块：JQuery，C3.js，Morris.js，Raphael.js
  - 其他：HTTPS，上传文件采用混合加密（AES+RSA）

- 具体技术
  - Apache Shiro权限开发,包括前台shiro隐藏拦截+后台Shiro注解开发
  - Spring Boot2.0异常全局处理(前台基于Ajax开发+后台json回写)
  - 基于SimpleHash实现的MD5数据库写入密码加密，通过监听session生成销毁简单统计在线人数
  - 通过Swagger2实现可视化接口测试，通过Druid实现后台SQL分析
  - c3.js，morris.js开发前台折线图+饼图，提升前台页面美观度+数据直观度
  - 利用AES加密文件分片，再使用RSA对于AES秘钥进行加密，在后端把文件分片合为Multipartfile
  - 利用font-awesome.css，animate.css，spinner.css，sweetalert2.css等开发前台页面
  - 基于Echache实现权限，登录信息缓存，利用自定义Shiro filter实现单点登录


## 启动配置
- 推荐使用Intellij IDEA调试该项目(IDEA可以去官网通过学生邮箱注册学生账号，免费使用旗舰版，不要下载社区版）eclispe不会用。。。
- Java版本推荐1.8(openJDK)，MySQL 推荐5.7版本 ，可以去官网下载 MySQL(GPL) openJDK(GPL) 
- 后台启动MySQL5.7(注意记录一下MySQL账号密码）
- 在Github项目页面点击code，复制HTTPS的链接，在IDEA中点击File->New->Project from Version Control,在URL中输入复制的链接，点击clone
- 等待Github代码Pull完成，点击IDEA右侧的Maven，再点击左上角的Reload All Maven Project，Maven会自动导入项目框架（如果Maven拉取代码速度慢，去setting.xml改一下镜像）
- Maven导入成功之后，点击右侧的Database，点击左上角的+，选择Data Source->MySQL数据库 输入数据库user和password点击OK连接
- 打开项目主目录下面的sql文件夹，点击1.sql，然后CTRL-A全选 右键点击Excute，跑数据库代码
- 进入项目下面src->main->resource 进入application-test.xml 把Spring.datasource.username和password进行修改，改为自己的数据库账号密码
- 进入项目下面src->main->java->com->centerm->fud_demo->FudDemoApplication 点击右键 再点击Run'FudDemoApplication' 等待项目运行
- 打开浏览器进入localhost:8088/index 进入项目主页 默认的超级管理员账号为admin 密码admin(密码数据库保存为密文）

## 项目代码分块
- 建议在二次开发项目之前系统学习SpringBoot，Apache Shiro与Mybatis基础知识！！！前端看看Jquery语法和Bootstrap用法！！
- src-main-java中主要为后端代码 src-main-resources主要为前端代码与静态资源
- java-com-centerm-fud_demo下面分为几个具体模块
 - controller 响应前端URL层
 - config 配置文件
 - cache 论坛主题缓存
 - constant 项目常量
 - dao 操作MySQL层
 - entity 实体类
 - enums 枚举类型
 - exception 自定义异常管理
 - filter 实现单点登录
 - listener 用户在线监听器
 - mapper 操作数据库
 - service 服务层级，在dao层与controller之间
 - shiro 项目登录权限控制模块
 - utils 工具类
-  


## 项目页面

**登录页面**
![登录](https://github.com/ouyangyi1998/MyPostPicture/blob/master/picture/%E6%88%AA%E5%B1%8F2021-04-02%2017.51.58.png)

**注册页面**
![注册](https://github.com/ouyangyi1998/MyPostPicture/blob/master/picture/%E6%88%AA%E5%B1%8F2021-04-02%2018.13.51.png)

**后台页面**
![主页](https://github.com/ouyangyi1998/MyPostPicture/blob/master/picture/%E6%88%AA%E5%B1%8F2021-04-02%2018.25.10.png)

**文件管理**
![文件管理](https://github.com/ouyangyi1998/MyPostPicture/blob/master/picture/%E6%88%AA%E5%B1%8F2021-04-03%2020.09.51.png)

**管理员页面**
![管理员](https://github.com/ouyangyi1998/MyPostPicture/blob/master/picture/%E6%88%AA%E5%B1%8F2021-04-02%2018.58.03.png)

**论坛模块**

论坛模块基于 https://github.com/OliverLiy/MyBlog 论坛模块开发 感谢！
![论坛](https://github.com/ouyangyi1998/MyPostPicture/blob/master/picture/%E6%88%AA%E5%B1%8F2021-04-03%2011.31.47.png)

## License

 - MIT License 



