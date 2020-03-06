## 1.方向汇总

- 项目使用的框架或者插件
  - 后端方向：SpringBoot，Mybatis，PageHelper，Swagger2，Ehcache，Shiro，FastJson
  - 数据库方向：MySQL，Druid
  - 前端方向：Thymeleaf，BootStrap4
  - css模块：SweetAlert2.css，Spinner.css，Animate.css，Font-awesome.css，Bootsnav.css，Perfect-scrollbar.css
  - js模块：JQuery，C3.js，Morris.js，Raphael.js，WebUpload.js

- 技术要求
  - Apache Shiro权限开发,包括前台shiro隐藏拦截+后台shiro注解开发
  - Spring Boot2.0异常全局处理(前台基于ajax开发+后台json回写)
  - 基于SimpleHash实现的md5数据库写入密码加密，通过监听session生成销毁简单统计在线人数
  - 通过swagger2实现可视化接口测试，通过Druid实现后台SQL分析
- 预研
  - c3.js，morris.js开发前台折线图+饼图，提升前台页面美观度+数据直观度
  - webupload.js文件上传下载模块，实现加密备份完整性校验等要求的功能
  - 利用font-awesome.css，animate.css，spinner.css，sweetalert2.css等开发前台页面
  - 基于echache实现权限，登录信息缓存，利用自定义shiro filter实现单点登录



## 2.技术要求

- Apache Shiro开发

  - 后台shiro注解开发
    - Spring Boot中集成shiro需要两个类：一个是shiroConfig类，一个是CustomRealm类
    - shiroConfig配置shiro过滤链，注解开发，密码加密等
    - CustomRealm继承`AuthorizingRealm`。并且重写父类中的`doGetAuthorizationInfo`（权限相关）、doGetAuthenticationInfo`（身份认证）这两个方法
    - 在shiroConfig之中加入authorizationAttributeSourceAdvisor方法与defaultAdvisorAutoProxyCreator代理实现注解开发

  - 前台shiro隐藏拦截
    - 导入thymeleaf-extras-shiro包，这个组件需要thymeleaf3.0+版本，spring Boot2.0符合要求
    - 在ShiroConfig加入ShiroDialect组件，前台加入xmlns:shiro="http://wbasePackageww.pollix.at/thymeleaf/shiro"实现注解开发

- Spring Boot2.0异常全局处理
  - 建立异常处理类
    - 通过注解方法实现异常类@ControllerAdvice实现全局异常控制和全局数据绑定
    - 通过在ShiroConfig中注册HandlerExceptionResolver也可以实现全局异常处理
  - 建立自定义异常(包括注册账号重复，登录账号或者密码未输入等自定义异常)
  - 表单提交通过Ajax访问后台，当发生目标异常情况 throw new exception(),在异常处理之后再返还给前台显示。在之前写的项目中抛出异常直接页面跳转，用户体验不佳，通过Ajax回写方式防止了页面异常跳转可能出现的用户体验度太低的问题。。。
  - fd对于常见Http状态码（404,405,500等）异常捕获之后直接跳转到指定的404/405/500页面
  - 对于自定义异常捕获之后，返还给前台ajax：succes，利用SweetAlert2.css显示具体信息
- SimpleHash工具与Shiro加密模块
  - 在注册表单提交之后，信息调用到后端提交给数据库，先进行md5或者其他形式的加密，再duiy输出到数据库存储。通过new SimpleHash（）可以实现撒盐，选择加密算法，选择迭代次数的操作。。。
  - 在登录的时候。。。Shiro源代码阅读
    - 在controller调用Subject subject=SecurityUtils();  subject.login();
    - Subject subject=this.securityManager.login(this.token)登录过程交给SecurityManager来代理
    - 调用authenticate(token)方法，SecurityManager将authenticate()代理给Authenticator去实现，
    - 接着由ModularRealmAuthenticator（实现类）实现doAuthenticate()
    - doAuthenticate()调用doSingleRealmAuthentication方法，再由其中的Realm来调用getAuthenticationInfo()方法
    - 我们继承的AuthenticatingRealm对getAuthenticationInfo()做了实现，并且回调我们的doGetAuthenticationInfo
  - HashedCredentialsMatcher 继承自 SimpleCredentialsMatcher并进行了扩展
    - 支持Hash算法：md5，sha256。。支持多次迭代
    - 在ShiroConfig注册即可在doGetAuthenticationInfo，调用new SimpleAuthenticationInfo()进行相应次数的哈希加密检验
- Swagger2实现接口的可视化校验，简化接口文档维护
  - maven依赖：springfox-swagger2，springfox-swagger-ui
  - 在Config开启@EnableSwagger2，new Docker().build()，在basePackage输入接口路径，在apilnfo展示文档页面信息
  - 构建api文档详细信息函数，new ApilnfoBuilder().build()，配置版本号以及title等元素
  - 在controller层加入注解@ApiOperation()说明方法的用途和作用，@ApiImplicitParam()说明一组的参数说明，@ApiResponses：用于请求的方法上，表示一组响应

## 3.预研

- c3.js，morris.js开发前台折线图+饼图，提升前台页面美观度+数据直观度
  - c3.js开发饼图：C3.js是基于D3.js开发的JavaScript库。通过C3，只需要往 generate 函数中传入数据对象就可以轻松baoc的绘制出图表，方便开发者使用
    - 构建c3需要c3.js，c3.css，d3.js，(c3依赖于d3，所以也需要d3)
    - c3通过传递参数调用generate()方法来生成图表，并且图表中的元素将会被作为bindto的指定选择器
    - 利用Jquery在页面加载时发起ajax请求获取图表数据，数据被封装在HashMap里面，回写到success
    - 从data中读取map数据，data['user']等，放入columns之中，type：donut输出饼图
    - 适配点击，鼠标接触等多种页面特效，以及饼图说明，颜色等元素
  - morris.js开发折线图：Morris.js 是一个轻量级的 JS 库，使用  jQuery 和 Raphael 来生成各种时序图
    - 构建 morris 需要 raphael.js 与 morris.js，raphael为构建矢量图提供支持
    - morris通过构造morris.area({})来实现折线图绘制，所以也需要发送ajax回写数据
    - morris.data获取json数据，在element绑定前台id，在xkey，ykey设置横轴纵轴坐标
    - 在morris.area还可以调节宽度，自动y轴坐标线，以及折线图颜色
- webupload.js文件上传下载模块，实现加密备份完整性校验等要求的功能
  - 配置webupload.js需要引入
    - bootstrap的css，js文件
    - webupload文件包，包括css，js等文件
    - 实现自己业务的js
  - 实现大文件分片上传功能
    - 在div中设置id=picker，在pick='#picker' 会自动编译成一个文件选择的input
    - 在文件上传之前，给每一个文件创建一个guid，然后把封装guid与ruid的push进入文件队列
    - 在文件上传过程中创建进度条实时显示（通过bootstrap实现）
    - 在controller实现对于文件的分块，通过guid和给文件分成不同的部分".part"
    - 再把文件转化为byte[]，进行数据上传加密
    - 最后进行文件合并，把文件.part，逐一导入主文件之中

- 利用font-awesome.css，animate.css，spinner.css，sweetalert2.css等开发前台页面
  - font-awesome.css实现页面图标效果，提升页面可视化操作程度
    - 引入font-awesome开发包，在li class中fa fa×××调用图标元素
  - animate.css实现页面运动的动画效果，用于登录注册页面
    - 引入animate.css，在div class中调用animate fade×××动画效果
  - spinner.css实现页面载入之前的加载动画效果
    - 通过Jquery设置页面加载时的backgound：#fff 配合spinner.css素材库来实现页面加载特效
  - sweetalert2.css实现页面弹窗提醒，提升用户交互的友好度
    - 提交表单或者点击超链接时，统一通过ajax访问后端，后端数据返回或者抛出异常。在前端通过sweetalert弹窗进行显示，在删除文件或者删除用户这种不同级别的操作，进行info-warn不同级别的弹窗，操作成功同时也进行提示
    - 引入sweetalert2.js与css模块，触发代码swal（{。。。});
      - 一种是选择是否操作，swal().then(function(data){获取data的操作值，判断操作情况，在执行对应的提示})
      - 一种是单纯的提示，比如说登录成功或者失败提示



- 基于echache实现权限，登录信息缓存，利用自定义shiro filter实现单点登录
  - pom添加依赖 shiro-ehcache 版本号与shiro-core同步
  - 在ShiroConfig添加缓存管理器-ehcacheManager
  - 在config中构造ehcache-shiro.xml缓存，这里分为default与authentication/authorization
  - 将缓存管理器添加到securityManager中，再在shirofilter配置securityManager，完成缓存注册
  - 利用自定义filter实现session队列，以及多用户在线踢下线功能，通过重写isAccessAllowed和onAccessDenied方法实现
    - isAccessAllowed表示是否允许访问，返回true表示允许，这里可以处理多次请求登录的业务场景
    - onAccessDenied表示访问拒绝时是否自己处理，在这里构造一个用户队列，实现单点踢下线
  - 在sessionListener实现简单的记录在线人数，记录session的创建与销毁
  - shiro-ehcache核心业务场景
    - 用户多开页面导致的多账号同时登录的问题
      - 当用户已经打开一个页面，再打开一个登录页面时，直接进入index，避免用户多开账号导致的session校验混乱的问题
      - 当用户关闭页面，一段时间内再打开页面（这里要设置一下session的过期时间），直接进入index，让用户从业务场景logout下线，避免多次登录，以及非正常上线
      - 当用户已经登录一个账号admin，再打开一个页面下线，再登录一个user账号，这里要对于所有admin前台页面的内容加锁，避免user账号可以访问admin账号内容
  - 在实现单点登录时，对于对于先后顺序进行控制，给后一个账号登录权限，前一个账号执行下线处理
