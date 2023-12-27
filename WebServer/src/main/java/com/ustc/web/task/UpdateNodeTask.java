package com.ustc.web.task;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-23:11
 */

import com.ustc.domain.vo.ClientNodeVO;
import com.ustc.web.service.NodeService;
import com.ustc.web.ws.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *@ClassName UpdateNodeTask
 *@Description 定时更新节点信息
 *@Author Yu-Bar
 *@Date 2023/12/27 23:11
 *@Version 1.0
 **/
@Component
@Slf4j
public class UpdateNodeTask {
    @Autowired
    private NodeService nodeService;
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 定时更新节点信息
     */
    @Scheduled(cron = "0/3 * * * * ? ") //每 3s 触发一次
    public void updateClientNodeList(){
        List<ClientNodeVO> clientNodeList = nodeService.getClientNodeList();
        webSocketServer.updateNodeInfo(clientNodeList);
//        log.info("更新节点信息：{}",clientNodeList);
    }
}
