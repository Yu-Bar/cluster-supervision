package com.ustc.domain.constant;

/**
 * Author:Yu-Bar
 * Date:2023/12/27-16:30
 */

public class NodeStatus {
    /**
     * 断开连接
     */
    public static final int WRONG = 0;

    /**
     * 已连接
     */
    public static final int OK = 1;

    /**
     * 连接重试中
     */
    public static final int RETRY = 2;
}
