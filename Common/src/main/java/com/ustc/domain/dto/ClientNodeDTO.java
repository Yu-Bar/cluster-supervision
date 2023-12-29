package com.ustc.domain.dto;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-16:25
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *@ClassName ClientNode
 *@Description 用于传输的节点数据（初次连接需要记录的信息）
 *@Author Yu-Bar
 *@Date 2023/12/27 16:25
 *@Version 1.0
 **/
@Data
@Builder
@Schema(description = "客户机器节点传输模型")
public class ClientNodeDTO {
    @Schema(description = "节点 id")
    private Long id;

    @Schema(description = "节点名称")
    private String name;

    @Schema(description = "节点地址(IP:端口)")
    private String address;

    @Schema(description = "上次活跃时间")
    private LocalDateTime contactTime;

    @Schema(description = "磁盘已使用容量百分比")
    private Integer capacityUsage;

    @Schema(description = "磁盘总容量")
    private String capacity;

    @Schema(description = "内存已使用容量百分比")
    private Integer memUsage;

    @Schema(description = "内存总容量")
    private String mem;

    @Schema(description = "CPU 核数")
    private Integer procNum;

    @Schema(description = "CPU 载荷百分比")
    private Integer CPULoad;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "状态")
    private int status;
}
