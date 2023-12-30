package com.ustc.socket.service.impl;
/**
 * Author:Yu-Bar
 * Date:2023/12/30-13:29
 */

import com.ustc.command.Command;
import com.ustc.command.CommandType;
import com.ustc.socket.service.NodeService;
import com.ustc.socket.ws.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

    /**
     * 下线节点
     * @param nodeId
     */
    @Override
    public void offlineClientById(Long nodeId) {
        Command<Object> command = new Command<>();
        command.setCommandType(CommandType.OFFLINE);
        webSocketServer.sendCommand(nodeId,command);
    }
}
