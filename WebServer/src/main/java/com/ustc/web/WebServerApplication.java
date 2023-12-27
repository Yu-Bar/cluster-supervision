package com.ustc.web;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-15:21
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class WebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }
}
