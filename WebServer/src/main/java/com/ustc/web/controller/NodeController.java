package com.ustc.web.controller;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-15:22
 */

import cn.hutool.core.bean.BeanUtil;
import com.ustc.domain.dto.ClientNodeDTO;
import com.ustc.domain.dto.ClientNodeModifyDTO;
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

    @Operation(description = "根据 id 修改节点")
    @PutMapping
    public Result modifyNode(@RequestBody ClientNodeModifyDTO clientNodeModifyDTO){
        log.info("修改节点:{}",clientNodeModifyDTO);
        nodeService.modifyNodeById(clientNodeModifyDTO);
        return Result.success();
    }

    @Operation(description = "根据 id 删除节点")
    @DeleteMapping("/{node}")
    public Result deleteNode(@PathVariable("node") Long nodeId){
        log.info("删除节点:{}",nodeId);
        nodeService.deleteClientNodeById(nodeId);
        return Result.success();
    }

}
