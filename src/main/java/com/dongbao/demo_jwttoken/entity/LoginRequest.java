package com.dongbao.demo_jwttoken.entity;

import lombok.Data;

/**
 * @author : wangjunyue
 * date: 2025/11/8 13:59
 * Description: com.dongbao.demo_jwttoken.entity
 * project: demo_JWTToken
 */
@Data
public class LoginRequest {

    private String username;
    private String password;
}
