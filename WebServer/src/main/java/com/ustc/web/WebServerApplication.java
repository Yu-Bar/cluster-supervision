package com.ustc.web;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-15:21
 */

import com.ustc.properties.JwtProperties;
import com.ustc.properties.SocketProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *@ClassName WebServerApplication
 *@Description 主类
 *@Author Yu-Bar
 *@Date 2023/12/27 15:21
 *@Version 1.0
 **/
@EnableScheduling //开启定时事务注解
@SpringBootApplication
@EnableConfigurationProperties({SocketProperties.class, JwtProperties.class})
public class WebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }
}
