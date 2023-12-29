package com.ustc.web.service.impl;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-22:49
 */

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.ustc.domain.dto.ClientNodeDTO;
import com.ustc.domain.vo.ClientNodeDetailVO;
import com.ustc.domain.vo.ClientNodeVO;
import com.ustc.result.Result;
import com.ustc.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *@ClassName NodeServiceImpl
 *@Author Yu-Bar
 *@Date 2023/12/27 22:49
 *@Version 1.0
 **/
@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ClientNodeVO> getClientNodeList() {
        Set keys = redisTemplate.keys("node::*");
        List<ClientNodeVO> clientNodeVOList = new ArrayList<>();
        for (Object key : keys) {
            Map entries = redisTemplate.opsForHash().entries(key);
            ClientNodeVO clientNodeVO = BeanUtil.mapToBean(entries, ClientNodeVO.class, true, CopyOptions.create());
            clientNodeVOList.add(clientNodeVO);
        }
        return clientNodeVOList;
    }

    /**
     * 通过 id 获取指定节点信息
     * @param id
     * @return
     */
    @Override
    public ClientNodeDetailVO getClientNodeById(Long id) {
        Map entries = redisTemplate.opsForHash().entries("node::" + id);
        ClientNodeDetailVO clientNodeDetailVO = BeanUtil.mapToBean(entries, ClientNodeDetailVO.class, true, CopyOptions.create());
        return clientNodeDetailVO;
    }
}
