package com.ustc.properties;
/**
 * Author:Yu-Bar
 * Date:2023/12/29-12:06
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *@ClassName SocketProperties
 *@Description socket 服务器相关配置
 *@Author Yu-Bar
 *@Date 2023/12/29 12:06
 *@Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "cs.socket")
@Data
public class SocketProperties {
    private String host;
}
