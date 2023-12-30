package com.ustc.socket.ws;




import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.ustc.command.Command;
import com.ustc.command.CommandType;
import com.ustc.constant.NodeStatus;
import com.ustc.domain.dto.ClientNodeDTO;
import com.ustc.domain.dto.ClientNodeRTM;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


/**
 *@ClassName WebSocketServer
 *@Description WebSocket服务
 *@Author Yu-Bar
 *@Date 2023/12/27 15:45
 *@Version 1.0
 **/
@Component
@Slf4j
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {
    private static RedisTemplate redisTemplate;
    //存放会话对象
    private static Map<Long, Session> sessionMap = new HashMap();
    private static Map<String, Long> clientIdMap = new HashMap();
    private static AtomicLong clientNum = new AtomicLong(0);

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate){
        WebSocketServer.redisTemplate = redisTemplate;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + "建立连接");
        long clientId = clientNum.addAndGet(1);
        sessionMap.put(clientId, session);
        clientIdMap.put(sid, clientId);
        String[] address = sid.split("_");
        ClientNodeDTO clientNodeDTO = ClientNodeDTO.builder()
                .id(clientId)
                .address(address[0] + ":" + address[1])
                .name("客户机节点" + clientId)
                .status(NodeStatus.OK)
                .version("1.01")
                .contactTime(LocalDateTime.now())
                .build();
        Map<String, Object> nodeMap = BeanUtil.beanToMap(clientNodeDTO);
        redisTemplate.opsForHash().putAll("node::" + clientId, nodeMap);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
//        JSONObject object = JSON.parseObject(message);
//        Map<String, Object> map = object.getInnerMap();
//        redisTemplate.opsForHash().putAll("node::" + clientIdMap.get(sid), map);
        ClientNodeRTM clientNodeRTM = JSON.parseObject(message, ClientNodeRTM.class);
        Map<String, Object> map = BeanUtil.beanToMap(clientNodeRTM);
        redisTemplate.opsForHash().putAll("node::" + clientIdMap.get(sid), map);
        System.out.println("收到来自客户端：" + sid + "的信息:" + message);
    }

    /**
     * 连接关闭调用的方法
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("连接断开:" + sid);
        Long clientId = clientIdMap.get(sid);
        if(sessionMap.containsKey(clientId)){
            redisTemplate.opsForHash().put("node::" + clientId, "status", NodeStatus.WRONG);
            sessionMap.remove(clientId);
        }
        clientIdMap.remove(sid);
    }

    /**
     * 向指定客户机发命令
     * @param clientId
     * @param command
     */
    public void sendCommand(Long clientId,Command command) throws IOException {
        Session session = sessionMap.get(clientId);
        if(command.getCommandType().equals(CommandType.OFFLINE)){
            sessionMap.remove(clientId);
        }
        session.getBasicRemote().sendText(JSON.toJSONString(command));
    }

}
