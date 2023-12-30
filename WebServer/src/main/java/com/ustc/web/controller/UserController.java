package com.ustc.web.controller;
/**
 * Author:Yu-Bar
 * Date:2023/12/31-0:25
 */

import com.ustc.constant.JwtClaimsConstant;
import com.ustc.domain.dto.UserLoginDTO;
import com.ustc.domain.dto.UserSignDTO;
import com.ustc.domain.po.User;
import com.ustc.domain.vo.UserLoginVO;
import com.ustc.domain.vo.UserSignVO;
import com.ustc.properties.JwtProperties;
import com.ustc.result.Result;
import com.ustc.web.service.UserService;
import com.ustc.web.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName UserController
 *@Description 用户模块
 *@Author Yu-Bar
 *@Date 2023/12/31 0:25
 *@Version 1.0
 **/
@CrossOrigin(origins="*",maxAge=3600)
@Tag(name = "用户",description = "用户相关接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @Operation(description = "用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    @Operation(description = "用户注册")
    @PostMapping("/sign")
    public Result<UserSignVO> sign(@RequestBody UserSignDTO userSignDTO) {
        log.info("用户注册：{}", userSignDTO);

        User user = userService.sign(userSignDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserSignVO userSignVO = UserSignVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();

        return Result.success(userSignVO);
    }
}
