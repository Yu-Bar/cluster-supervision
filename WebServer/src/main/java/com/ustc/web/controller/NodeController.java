package com.ustc.web.controller;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-15:22
 */

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.ustc.domain.constant.NodeStatus;
import com.ustc.domain.dto.ClientNodeDTO;
import com.ustc.result.Result;
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
@Tag(name = "节点",description = "节点相关接口")
@RestController
@RequestMapping("/node")
@Slf4j
public class NodeController {

    @Autowired
    WebSocketServer webSocketServer;
    @Autowired
    RedisTemplate redisTemplate;

    @Operation(description = "测试websocket")
    @PostMapping("/websocket")
    public Result testSocket(){
        webSocketServer.sendToAllClient("hahaha");
        return Result.success();
    }

    @Operation(description = "测试Redis")
    @PostMapping("/redis")
    public Result testRedis(){
        ClientNodeDTO clientNodeDTO = ClientNodeDTO.builder()
                .id(new Random().nextLong())
                .address("192.168.0.1:8888")
                .name("节点1")
                .status(NodeStatus.OK)
                .version("1.01")
                .capacityUsage(20)
                .capacity("10G")
                .contactTime(LocalDateTime.now())
                .build();
        Map<String, Object> nodeMap = BeanUtil.beanToMap(clientNodeDTO);
        redisTemplate.opsForHash().putAll("node::" + clientNodeDTO.getId(), nodeMap);
        return Result.success();
    }

    @Operation(description = "测试Redis")
    @GetMapping("/redis/{node}")
    public Result<ClientNodeDTO> testGetRedis(@PathVariable("node") Long nodeId){
        Map entries = redisTemplate.opsForHash().entries("node::" + nodeId);
        ClientNodeDTO clientNodeDTO = BeanUtil.mapToBean(entries, ClientNodeDTO.class, true, CopyOptions.create());
        return Result.success(clientNodeDTO);
    }

}
