package com.ustc.socket.controller;
/**
 * Author:Yu-Bar
 * Date:2023/12/29-10:50
 */

import com.ustc.result.Result;
import com.ustc.socket.service.NodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName CommandController
 *@Description 接收命令接口
 *@Author Yu-Bar
 *@Date 2023/12/29 10:50
 *@Version 1.0
 **/
@RestController
@RequestMapping("/command")
@Tag(name = "命令",description = "命令相关接口")
@Slf4j
public class CommandController {
    @Autowired
    NodeService nodeService;

    @PutMapping("/{id}")
    @Operation(description = "根据 id 下线节点")
    public Result offlineNode(@PathVariable("id") Long nodeId){
        // 执行下线节点的相关操作
        log.info("下线节点:{}",nodeId);
        nodeService.sendMessage();
        // 下线节点成功返回Result.success()，失败则返回Result.error()
        return Result.success();
    }

}
