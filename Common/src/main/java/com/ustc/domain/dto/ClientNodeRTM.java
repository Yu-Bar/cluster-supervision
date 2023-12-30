package com.ustc.domain.dto;
/**
 * Author:Yu-Bar
 * Date:2023/12/29-11:52
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *@ClassName ClientNodeRTM
 *@Description 需要实时监控的节点信息
 *@Author Yu-Bar
 *@Date 2023/12/29 11:52
 *@Version 1.0
 **/
@Data
@Builder
public class ClientNodeRTM implements Serializable {

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
    private Integer cpuLoad;

    @Serial
    private static final long serialVersionUID = 1L;
}
