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
 *@Description 用于传输的节点数据（修改节点时的信息）
 *@Author Yu-Bar
 *@Date 2023/12/27 16:25
 *@Version 1.0
 **/
@Data
@Builder
@Schema(description = "修改客户机器节点信息时的传输模型")
public class ClientNodeModifyDTO {
    @Schema(description = "节点 id")
    private Long id;

    @Schema(description = "节点名称")
    private String name;
}
