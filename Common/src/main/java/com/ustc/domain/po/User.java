package com.ustc.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 
 * @TableName user
 */
@Data
public class User implements Serializable {

    private Long id;

    private String username;

    private String password;
}