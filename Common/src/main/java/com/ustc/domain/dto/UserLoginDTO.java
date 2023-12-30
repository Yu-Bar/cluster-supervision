package com.ustc.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "用户登录时传递的数据模型")
public class UserLoginDTO implements Serializable {

    @Schema(description = "账户")
    private String username;

    @Schema(description = "密码")
    private String password;

}
