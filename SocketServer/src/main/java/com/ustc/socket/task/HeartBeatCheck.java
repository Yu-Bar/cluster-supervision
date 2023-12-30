package com.ustc.socket.task;
/**
 * Author:Yu-Bar
 * Date:2023/12/30-21:32
 */

import com.ustc.constant.NodeStatus;
import com.ustc.socket.ws.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 *@ClassName UpdateNodeTask
 *@Description 定时更新节点信息
 *@Author Yu-Bar
 *@Date 2023/12/27 23:11
 *@Version 1.0
 **/
@Component
@Slf4j
public class HeartBeatCheck {
    @Autowired
    private RedisTemplate<String,Long> redisTemplate;
    @Autowired
    WebSocketServer webSocketServer;

    /**
     * 定时处理故障节点
     */
    @Scheduled(cron = "0/5 * * * * ? ") //每 5s 触发一次
    public void checkHeartBeat(){
        log.info("检查故障节点");
        long currentTimeMillis = System.currentTimeMillis();
        // 超过 5 s 的节点就是在尝试重连的
        Set<Long> retryClientIdSet = redisTemplate.opsForZSet().rangeByScore("heart", currentTimeMillis - 10000, currentTimeMillis - 5000);
        if(retryClientIdSet != null){
            retryClientIdSet.forEach(clientId -> {
                log.info("存在重试节点:{}",clientId);
                redisTemplate.opsForHash().put("node::" + clientId,"status", NodeStatus.RETRY);
            });
        }
        // 超过 10 s 的节点就应该关闭连接了
        Set<Long> wrongClientIdSet = redisTemplate.opsForZSet().rangeByScore("heart", currentTimeMillis - 100000, currentTimeMillis - 10000);
        if(wrongClientIdSet != null){
            wrongClientIdSet.forEach(clientId -> {
                log.info("存在错误节点:{}",clientId);
                redisTemplate.opsForHash().put("node::" + clientId,"status", NodeStatus.WRONG);
                redisTemplate.opsForZSet().remove("heart", clientId);
                webSocketServer.closeConnection(clientId);
            });
        }
    }
}
