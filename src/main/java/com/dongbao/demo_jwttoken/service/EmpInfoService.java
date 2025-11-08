package com.dongbao.demo_jwttoken.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dongbao.demo_jwttoken.entity.EmpInfo;

/**
 * @author : wangjunyue
 * date: 2025/11/8 11:32
 * Description: com.dongbao.demo_jwttoken.service.impl
 * project: demo_JWTToken
 */
public interface EmpInfoService extends IService<EmpInfo> {
    EmpInfo getEmpInfoByEmpNo(String empNo);
}
