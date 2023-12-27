package com.ustc.domain.vo;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-22:50
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *@ClassName NodeVo
 *@Description 返回给用户的客户端节点数据
 *@Author Yu-Bar
 *@Date 2023/12/27 22:50
 *@Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户查看评论返回的数据格式")
public class ClientNodeVO {
    @Schema(description = "节点 id")
    private Long id;

    @Schema(description = "节点名称")
    private String name;

    @Schema(description = "节点地址(IP:端口)")
    private String address;

    @Schema(description = "上次活跃时间")
    private LocalDateTime contactTime;

    @Schema(description = "已使用容量百分比")
    private int capacityUsage;

    @Schema(description = "总容量")
    private String capacity;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "状态")
    private int status;
}
