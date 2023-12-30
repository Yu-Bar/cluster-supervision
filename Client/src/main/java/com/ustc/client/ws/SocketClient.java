package com.ustc.client.ws;
/**
 * Author:Yu-Bar
 * Date:2023/12/30-15:06
 */

import com.alibaba.fastjson.JSON;
import com.ustc.client.command.CommandExecutor;
import com.ustc.command.Command;
import com.ustc.domain.dto.ClientNodeRTM;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *@ClassName WenSocketClient
 *@Description TODO
 *@Author Yu-Bar
 *@Date 2023/12/30 15:06
 *@Version 1.0
 **/
@ClientEndpoint
@Component
@Slf4j
public class SocketClient {

    private static Session serverSession;

    private static CommandExecutor commandExecutor;

    @Autowired
    public void setCommandExecutor(CommandExecutor commandExecutor){
        SocketClient.commandExecutor = commandExecutor;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server");
        serverSession = session;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);
        // 处理接收到的消息
        Command command = JSON.parseObject(message, Command.class);
        switch (command.getCommandType()){
            case OFFLINE -> {
                commandExecutor.stopSpringBootApplication();
            }
            default -> {
                log.info("无效命令");
            }
        }
    }

    public void sendPing(ClientNodeRTM clientNodeRTM) throws IOException {
        serverSession.getBasicRemote().sendText(JSON.toJSONString(clientNodeRTM));
    }
}
