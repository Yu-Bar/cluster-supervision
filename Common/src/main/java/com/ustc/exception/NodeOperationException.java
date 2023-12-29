package com.ustc.exception;
/**
 * Author:Yu-Bar
 * Date:2023/12/29-14:44
 */

/**
 *@ClassName NodeOperationException
 *@Description 操作节点异常
 *@Author Yu-Bar
 *@Date 2023/12/29 14:44
 *@Version 1.0
 **/
public class NodeOperationException extends BaseException{
    public NodeOperationException() {
    }

    public NodeOperationException(String msg) {
        super(msg);
    }
}
