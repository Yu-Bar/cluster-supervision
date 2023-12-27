package com.ustc.web;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-16:41
 */

import cn.hutool.core.bean.BeanUtil;
import com.ustc.domain.dto.ClientNodeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @ClassName TestRedis
 * @Description TODO
 * @Author Yu-Bar
 * @Date 2023/12/27 16:41
 * @Version 1.0
 **/
@SpringBootTest
public class TestRedis {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void test01() {
        ClientNodeDTO clientNodeDTO = ClientNodeDTO.builder()
                .address("192.168.0.1:8888")
                .name("节点1")
                .status(0)
                .version("1.01")
                .capacityUsage(20)
                .capacity("10G")
                .contactTime(LocalDateTime.now())
                .build();
        Map<String, Object> nodeMap = BeanUtil.beanToMap(clientNodeDTO);
        redisTemplate.opsForHash().putAll(clientNodeDTO.getName(), nodeMap);
    }
}
