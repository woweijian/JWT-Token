package com.dongbao.demo_jwttoken.Tool;

import com.dongbao.demo_jwttoken.Exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : wangjunyue
 * date: 2025/11/8 15:02
 * Description: com.dongbao.demo_jwttoken.Tool
 * project: demo_JWTToken
 */

/**
 * Token认证拦截器
 */
@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行OPTIONS请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }

        // 检查handler是否是Controller方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 检查是否有@Anonymous注解（不需要认证）
//        if (handlerMethod.hasMethodAnnotation(Anonymous.class)) {
//            return true;
//        }

        // 获取Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(401, "缺少有效的Token");
        }

        String token = authHeader.substring(7);

        // 验证Token
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "Token无效或已过期");
        }

        // 将用户信息存入请求上下文
        String username = jwtUtils.getUsernameFromToken(token);
        request.setAttribute("currentUser", username);

        return true;
    }
}
