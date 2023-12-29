package com.ustc.web.config;
/**
 * Author:Yu-Bar
 * Date:2023/12/29-11:07
 */

import com.ustc.properties.SocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 *@ClassName HttpServiceConfiguration
 *@Description 提供 HttpServiceProxyFactory 用于 RPC
 *@Author Yu-Bar
 *@Date 2023/12/29 11:07
 *@Version 1.0
 **/
@Slf4j
@Configuration
public class HttpServiceConfiguration {

    @Autowired
    SocketProperties socketProperties;

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(){
        log.info("SocketBaseUrl:" + socketProperties);
        //1、创建客户端
        WebClient client = WebClient.builder()
                .baseUrl(socketProperties.getHost())
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer
                            .defaultCodecs()
                            .maxInMemorySize(256*1024*1024);
                    //响应数据量太大有可能会超出BufferSize，所以这里设置的大一点
                })
                .build();
        //2、创建工厂
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client)).build();
            return  factory;
    }
}
