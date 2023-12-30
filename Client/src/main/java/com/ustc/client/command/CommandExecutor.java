package com.ustc.client.command;
/**
 * Author:Yu-Bar
 * Date:2023/12/30-21:05
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *@ClassName CommandExecutor
 *@Description 命令执行器
 *@Author Yu-Bar
 *@Date 2023/12/30 21:05
 *@Version 1.0
 **/
@Component
public class CommandExecutor {
    @Autowired
    private ApplicationContext applicationContext;

    public void stopSpringBootApplication() {
        int exitCode = SpringApplication.exit(applicationContext, () -> 0);
        System.exit(exitCode);
    }
}
