package com.ustc.web.service.impl;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-22:49
 */

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.ustc.constant.MessageConstant;
import com.ustc.constant.NodeStatus;
import com.ustc.domain.dto.ClientNodeDTO;
import com.ustc.domain.dto.ClientNodeModifyDTO;
import com.ustc.domain.vo.ClientNodeDetailVO;
import com.ustc.domain.vo.ClientNodeVO;
import com.ustc.exception.NodeOperationException;
import com.ustc.result.Result;
import com.ustc.web.service.CommandService;
import com.ustc.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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
    @Autowired
    HttpServiceProxyFactory httpServiceProxyFactory;

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

    /**
     * 通过 id 删除节点
     * @param id
     * @return
     */
    @Override
    public Boolean deleteClientNodeById(Long id) {
        ClientNodeDetailVO nodeDetailVO = getClientNodeById(id);
        if(nodeDetailVO == null)
            throw new NodeOperationException(MessageConstant.NODE_NOT_EXIST);
        if(nodeDetailVO.getStatus() == NodeStatus.OK || nodeDetailVO.getStatus() == NodeStatus.RETRY)
            throw new NodeOperationException(MessageConstant.DELETE_ALIVE_NODE);
        return redisTemplate.delete("node::" + id);
    }

    /**
     * 根据 id 下线节点
     * @param nodeId
     */
    @Override
    public Result offlineNodeById(Long nodeId) {
        ClientNodeDetailVO nodeDetailVO = getClientNodeById(nodeId);
        if(nodeDetailVO == null)
            throw new NodeOperationException(MessageConstant.NODE_NOT_EXIST);
        if(nodeDetailVO.getStatus() == NodeStatus.WRONG || nodeDetailVO.getStatus() == NodeStatus.OFFLINE)
            throw new NodeOperationException(MessageConstant.NODE_NOT_ALIVE);
        CommandService commandService = httpServiceProxyFactory.createClient(CommandService.class);
        Result result = commandService.offlineNode(nodeId);
        if(result.getCode() == 1){
            redisTemplate.opsForHash().put("node::" + nodeId, "status", NodeStatus.OFFLINE);
        }
        return result;
    }

    /**
     * 根据 id 修改节点
     * @param clientNodeModifyDTO
     */
    @Override
    public void modifyNodeById(ClientNodeModifyDTO clientNodeModifyDTO) {
        Map<String, Object> map = BeanUtil.beanToMap(clientNodeModifyDTO);
        if(!redisTemplate.hasKey("node::" + clientNodeModifyDTO.getId()))
            throw new NodeOperationException(MessageConstant.NODE_NOT_EXIST);
        redisTemplate.opsForHash().putAll("node::" + clientNodeModifyDTO.getId(), map);
    }
}
