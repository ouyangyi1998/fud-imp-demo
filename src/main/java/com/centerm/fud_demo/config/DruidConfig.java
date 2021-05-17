package com.centerm.fud_demo.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Druid连接池配置
 * @author ouyangyi
 * @time 2020.2.23
 */
@Configuration
@Slf4j
public class DruidConfig {
    /**
     * 进行Druid登录账户 IP控制
     * @return Durid登录模块控制
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet()
    {
        ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        servletRegistrationBean.addInitParameter("deny","127.0.0.2");
        servletRegistrationBean.addInitParameter("loginUsername","jerry");
        servletRegistrationBean.addInitParameter("loginPassword","1234");
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    /**
     * 配置Druid URL过滤器
     * @return Druid 过滤器
     */
    @Bean
    public FilterRegistrationBean druidStatFilter()
    {
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        log.info("druid初始化完成");
        return filterRegistrationBean;
    }
}
