package com.ustc.client;
/**
 * Author:Yu-Bar
 * Date:2023/12/30-13:17
 */

import com.ustc.client.ws.SocketClient;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.net.InetAddress;
import java.net.URI;


/**
 *@ClassName Client
 *@Description 客户端
 *@Author Yu-Bar
 *@Date 2023/12/30 13:17
 *@Version 1.0
 **/
@Component
@ClientEndpoint
public class Client implements ApplicationRunner {
    @Value("${sc.socket.url}")
    private String serverUrl;
    @Value("${server.port}")
    private String myPort;
    @Override
    public void run(ApplicationArguments args) throws Exception{
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress(); // 获取本机IP地址
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = serverUrl + ipAddress + "_" + myPort; // WebSocket 服务器端点
            container.connectToServer(SocketClient.class, URI.create(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
