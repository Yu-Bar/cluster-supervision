package com.ustc.domain.dto;
/**
 * Author:Yu-Bar
 * Date:2023/12/29-11:52
 */

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 *@ClassName ClientNodeRTM
 *@Description 需要实时监控的节点信息
 *@Author Yu-Bar
 *@Date 2023/12/29 11:52
 *@Version 1.0
 **/
public class ClientNodeRTM {
    @Schema(description = "节点 id")
    private Long id;

    @Schema(description = "上次活跃时间")
    private LocalDateTime contactTime;

    @Schema(description = "磁盘已使用容量百分比")
    private Integer capacityUsage;

    @Schema(description = "内存已使用容量百分比")
    private Integer memUsage;

    @Schema(description = "CPU 载荷百分比")
    private Integer CPULoad;

    @Schema(description = "状态")
    private int status;
}
