package com.ustc.web.service;

import com.ustc.result.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.PutExchange;

/**
 * Author:Yu-Bar
 * Date:2023/12/29-10:54
 */

public interface CommandService {
    @PutExchange(url = "/{nodeId}")
    Result offlineNode(@PathVariable("nodeId") Long nodeId);
}
