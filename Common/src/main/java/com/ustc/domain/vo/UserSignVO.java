package com.ustc.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户注册返回的数据格式")
public class UserSignVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "账户")
    private String username;

    @Schema(description = "jwt令牌")
    private String token;

}
