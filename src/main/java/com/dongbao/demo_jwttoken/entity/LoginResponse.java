package com.dongbao.demo_jwttoken.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : wangjunyue
 * date: 2025/11/8 14:00
 * Description: com.dongbao.demo_jwttoken.entity
 * project: demo_JWTToken
 */
@Data
@AllArgsConstructor
public class LoginResponse {
//    private String token;
   private String accessToken;
//    private String refreshToken;
    private String username;
//    private String tokenType = "Bearer";
    private Long expiresIn;  // Access Token剩余秒数

}
