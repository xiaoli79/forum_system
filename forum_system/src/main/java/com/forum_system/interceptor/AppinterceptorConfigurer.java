package com.forum_system.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppinterceptorConfigurer implements WebMvcConfigurer {

//    @Autowired
//    private LoginInterceptor loginInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 添加登录拦截器
////        拦截器拦截的页面！！
//        registry.addInterceptor(loginInterceptor) // 添加用户登录拦截器
//                .addPathPatterns("/**")      // 拦截所有请求
//                .excludePathPatterns("/sign-in.html") // 排除登录HTML
//                .excludePathPatterns("/sign-up.html") // 排除注册HTML
//                .excludePathPatterns("/user/login")   // 排除登录api接口
//                .excludePathPatterns("/user/register")// 排除注册api接口
//                .excludePathPatterns("/user/logout")  // 排除退出api接口
//                .excludePathPatterns("/swagger*/**")  // 排除登录swagger下所有
//                .excludePathPatterns("/v3*/**")       // 排除登录v3下所有, 与swa
//                .excludePathPatterns("/dist/**")      // 排除所有静态文件
//                .excludePathPatterns("/image/**")
//                .excludePathPatterns("/**.ico")
//                .excludePathPatterns("/js/**")
//                .excludePathPatterns("/test/**");
//    }
}
