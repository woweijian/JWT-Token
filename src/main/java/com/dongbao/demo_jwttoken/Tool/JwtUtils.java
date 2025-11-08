package com.dongbao.demo_jwttoken.Tool;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * @author : wangjunyue
 * date: 2025/11/8 13:40
 * Description: com.dongbao.demo_jwttoken.Tool
 * project: demo_JWTToken
 */
@Component
public class JwtUtils {

    @Value("${jwt.access-token.expiration:7200000}")  // 30分钟
    private Long accessTokenExpiration;

//    @Value("${jwt.refresh-token.expiration:604800000}") // 7天
//    private Long refreshTokenExpiration;

    @Value("${jwt.secret}")
    private String SECRET_KEY;


    // 秘钥 - 生产环境请从配置文件读取
//    private final String SECRET_KEY = "mySuperSecretKeyForJWTTokenGeneration123";
//    private final long EXPIRATION_TIME = 86400000; // 24小时，单位毫秒

    private SecretKey getSigningKey() {  //生成JWT签名所需的密钥
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * 生成Access Token
     */
    public String generateAccessToken(String username) {
        return generateToken(username, accessTokenExpiration);
    }

    /**
     * 生成Refresh Token
     */
//    public String generateRefreshToken(String username) {
//        return generateToken(username, refreshTokenExpiration);
//    }


    /**
     * 生成Token
     */
    public String generateToken(String username,Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从Token中获取用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 验证Token是否有效
     */
    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从Token中获取过期时间
     */
    public Date getExpirationFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 计算Token剩余有效时间（秒）- 实时计算
     */
    public long getRemainingTimeInSeconds(String token) {
        try {
            Date expiration = getExpirationFromToken(token);
            long remainingMs = expiration.getTime() - System.currentTimeMillis();
            return Math.max(0, remainingMs / 1000); // 确保不为负数
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 通用的Claims获取方法
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    /**
     * 解析Token获取所有Claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查Token是否过期
     */
    public boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }

    /**
     * 解析Token获取Claims
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
