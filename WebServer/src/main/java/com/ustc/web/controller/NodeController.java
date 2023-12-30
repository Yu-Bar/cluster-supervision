package com.ustc.web.controller;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-15:22
 */

import cn.hutool.core.bean.BeanUtil;
import com.ustc.domain.dto.ClientNodeDTO;
import com.ustc.domain.vo.ClientNodeDetailVO;
import com.ustc.result.Result;
import com.ustc.web.service.NodeService;
import com.ustc.web.ws.WebSocketServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

/**
 *@ClassName NodeController
 *@Description 节点相关操作
 *@Author Yu-Bar
 *@Date 2023/12/27 15:22
 *@Version 1.0
 **/
@CrossOrigin(origins="*",maxAge=3600)
@Tag(name = "节点",description = "节点相关接口")
@RestController
@RequestMapping("/node")
@Slf4j
public class NodeController {

    @Autowired
    WebSocketServer webSocketServer;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    NodeService nodeService;


    @Operation(description = "测试websocket")
    @PostMapping("/websocket")
    public Result testSocket(){
        webSocketServer.sendToAllClient("hahaha");
        return Result.success();
    }

    @Operation(description = "测试Redis")
    @PostMapping("/redis")
    public Result testRedis(){
        Random random = new Random();
        ClientNodeDTO clientNodeDTO = ClientNodeDTO.builder()
                .id(random.nextLong(1,10000))
                .address("192.168.0." + random.nextInt(255) + ":" + random.nextInt(65535))
                .name("节点" + random.nextInt(10000))
                .status(random.nextInt(4))
                .version(random.nextInt(50) + ".01")
                .capacityUsage(random.nextInt(100))
                .capacity(random.nextInt(100) + "G")
                .contactTime(LocalDateTime.now())
                .build();
        Map<String, Object> nodeMap = BeanUtil.beanToMap(clientNodeDTO);
        redisTemplate.opsForHash().putAll("node::" + clientNodeDTO.getId(), nodeMap);
        return Result.success();
    }

    @Operation(description = "根据 id 获取节点信息")
    @GetMapping("/{node}")
    public Result<ClientNodeDetailVO> getNode(@PathVariable("node") Long nodeId){
        ClientNodeDetailVO nodeDetailVO = nodeService.getClientNodeById(nodeId);
        log.info("查询节点:{}",nodeDetailVO);
        return Result.success(nodeDetailVO);
    }

    @Operation(description = "根据 id 下线节点")
    @PutMapping("/{node}")
    public Result offlineNode(@PathVariable("node") Long nodeId){
        log.info("下线节点:{}",nodeId);
        return nodeService.offlineNodeById(nodeId);
    }

    @Operation(description = "根据 id 删除节点")
    @DeleteMapping("/{node}")
    public Result deleteNode(@PathVariable("node") Long nodeId){
        log.info("删除节点:{}",nodeId);
        nodeService.deleteClientNodeById(nodeId);
        return Result.success();
    }

}
