package com.dongbao.demo_jwttoken.Tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : wangjunyue
 * date: 2025/11/8 15:08
 * Description: com.dongbao.demo_jwttoken.Tool
 * project: demo_JWTToken
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns("/dhg/api/**") // 拦截所有/dhg/下的请求
                .excludePathPatterns("/dhg/auth/login", "/dhg/auth/verify"); // 排除登录和验证Token接口
    }

}
