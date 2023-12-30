package com.ustc.socket;
/**
 * Author:Yu-Bar
 * Date:2023/12/30-12:56
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *@ClassName SocketServerApplication
 *@Description Socket 服务器
 *@Author Yu-Bar
 *@Date 2023/12/30 12:56
 *@Version 1.0
 **/
@SpringBootApplication
@EnableScheduling
public class SocketServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocketServerApplication.class, args);
    }
}
