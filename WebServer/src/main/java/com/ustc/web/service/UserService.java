package com.ustc.web.service;

import com.ustc.domain.dto.UserLoginDTO;
import com.ustc.domain.dto.UserSignDTO;
import com.ustc.domain.po.User;

/**
 * Author:Yu-Bar
 * Date:2023/12/31-0:26
 */

public interface UserService {
    /**
     * 登陆
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 注册
     * @param userSignDTO
     * @return
     */
    User sign(UserSignDTO userSignDTO);
}
