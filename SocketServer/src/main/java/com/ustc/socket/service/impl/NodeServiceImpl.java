package com.ustc.socket.service.impl;
/**
 * Author:Yu-Bar
 * Date:2023/12/30-13:29
 */

import com.ustc.socket.service.NodeService;
import com.ustc.socket.ws.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@ClassName NodeServiceImpl
 *@Description TODO
 *@Author Yu-Bar
 *@Date 2023/12/30 13:29
 *@Version 1.0
 **/
@Service
public class NodeServiceImpl implements NodeService {
    @Autowired
    WebSocketServer webSocketServer;

    @Override
    public void sendMessage() {
        webSocketServer.sendToAllClient("socket server的消息");
    }
}
