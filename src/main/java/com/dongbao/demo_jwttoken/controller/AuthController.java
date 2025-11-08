package com.dongbao.demo_jwttoken.controller;

import com.dongbao.demo_jwttoken.Exception.BusinessException;
import com.dongbao.demo_jwttoken.Tool.ApiResult;
import com.dongbao.demo_jwttoken.Tool.JwtUtils;
import com.dongbao.demo_jwttoken.entity.LoginRequest;
import com.dongbao.demo_jwttoken.entity.LoginResponse;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : wangjunyue
 * date: 2025/11/8 14:00
 * Description: com.dongbao.demo_jwttoken.controller
 * project: demo_JWTToken
 */
@RestController
@RequestMapping("/dhg/auth")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 用户登录
     */

      @PostMapping("/login")
      public ApiResult<LoginResponse> login(@RequestBody LoginRequest request){
          // 最简单的用户验证 - 实际项目中应该查询数据库
          if ("admin".equals(request.getUsername()) && "123456".equals(request.getPassword())) {
              String username = request.getUsername();

//              String token = jwtUtils.generateToken(request.getUsername());
              // 生成Token
              String accessToken = jwtUtils.generateAccessToken(username);

              // 计算token生效时间有多久
//              long expiresIn = (jwtUtils.getExpirationFromToken(accessToken).getTime() - System.currentTimeMillis()) / 1000;
              //实时计算token剩余时间
              long expiresIn = jwtUtils.getRemainingTimeInSeconds(accessToken);


              return ApiResult.success(new LoginResponse(accessToken,request.getUsername(),expiresIn),"登录成功");
          } else {
              throw new BusinessException("用户名或密码错误");
          }
      }


    /**
     * 验证Token接口
     */
    @GetMapping("/verify")
    public String verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("缺少Token");
        }

        String token = authHeader.substring(7);
        if (jwtUtils.validateToken(token)) {
            String username = jwtUtils.getUsernameFromToken(token);
            return "Token有效，当前用户: " + username;
        } else {
            throw new RuntimeException("Token无效");
        }
    }


}
