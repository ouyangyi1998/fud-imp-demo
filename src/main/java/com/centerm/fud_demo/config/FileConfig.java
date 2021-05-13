//package com.centerm.fud_demo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///**
// * TODO
// *
// * @author ouyangyi
// * @date 2021/5/13 下午5:30
// */
//@Configuration
//public class FileConfig extends WebMvcConfigurationSupport {
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //这个是虚拟路径图片路径
//        registry.addResourceHandler("/Users/ouyangyi/Downloads/test/real/**")
//                //这个是图片真实路径
//                .addResourceLocations("file:/Users/ouyangyi/Downloads/test/real/");
//
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//    }
//}
