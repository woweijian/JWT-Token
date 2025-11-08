package com.dongbao.demo_jwttoken.controller;

import com.dongbao.demo_jwttoken.Tool.ApiResult;
import com.dongbao.demo_jwttoken.Tool.JwtUtils;
import com.dongbao.demo_jwttoken.entity.EmpInfo;
import com.dongbao.demo_jwttoken.entity.EmpInfoUto;
import com.dongbao.demo_jwttoken.service.EmpInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author : wangjunyue
 * date: 2025/11/8 11:30
 * Description: com.dongbao.demo_jwttoken.controller
 * project: demo_JWTToken
 */
@Slf4j
@RestController
@RequestMapping("/dhg/api")
public class ApiController {
   @Autowired
    private EmpInfoService empInfoService;
   @Autowired
   private JwtUtils jwtUtils;


   @PostMapping("/getEmpInfoByEmpNo")
   public ApiResult<EmpInfo> getEmpInfoByEmpNo(@RequestBody EmpInfoUto uto, @RequestHeader("Authorization") String authHeader){
       // 手动验证Token
      log.error("authorization{}",authHeader);
       if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           log.error("缺少Token");
           throw new RuntimeException("请先登录");
       }

       String token = authHeader.substring(7);
       if (!jwtUtils.validateToken(token)) {
           log.error("Token无效");
           throw new RuntimeException("Token无效，请重新登录");
       }



       log.info("接收到的参数为：{}",uto.getEmp_no());
       try {
           EmpInfo emp = empInfoService.getEmpInfoByEmpNo(uto.getEmp_no());
           log.info("12{}",emp);
           return ApiResult.success(emp,"查询成功");
       } catch (Exception e) {
           return ApiResult.error(500,e.getMessage());
       }


   }


}
