package com.ustc.command;
/**
 * Author:Yu-Bar
 * Date:2023/12/30-15:21
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *@ClassName Command
 *@Description 命令
 *@Author Yu-Bar
 *@Date 2023/12/30 15:21
 *@Version 1.0
 **/
@Data
public class Command<T> implements Serializable {
    // 指令类型
    private CommandType commandType;
    // 参数列表
    private List<T> params;

}
