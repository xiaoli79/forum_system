package com.forum_system.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;



//配置扫描路径
@Configuration
@MapperScan("com.forum_system.dao")
public class MybatisConfig {

}
