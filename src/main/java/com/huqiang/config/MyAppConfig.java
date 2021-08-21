package com.huqiang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/***************************************************************************************
 *功能介绍：
 *@date 2021/8/1620:24
 ***************************************************************************************/
@Configuration
@ImportResource(locations = {"classpath:applicationContext.xml"})
public class MyAppConfig {
}
