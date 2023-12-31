package com.ustc.web.service;

import com.ustc.domain.dto.ClientNodeModifyDTO;
import com.ustc.domain.vo.ClientNodeDetailVO;
import com.ustc.domain.vo.ClientNodeVO;
import com.ustc.result.Result;

import java.util.List;

/**
 * Author:Yu-Bar
 * Date:2023/12/27-22:49
 */

public interface NodeService {

    /**
     * 获取客户端节点信息
     * @return
     */
    List<ClientNodeVO> getClientNodeList();

    /**
     * 通过 id 获取指定节点信息
     * @param id
     * @return
     */
    ClientNodeDetailVO getClientNodeById(Long id);

    /**
     * 通过 id 删除节点
     * @param id
     * @return
     */
    Boolean deleteClientNodeById(Long id);

    /**
     * 根据 id 下线节点
     * @param nodeId
     */
    Result offlineNodeById(Long nodeId);

    /**
     * 根据 id 修改节点
     * @param clientNodeModifyDTO
     */
    void modifyNodeById(ClientNodeModifyDTO clientNodeModifyDTO);
}
