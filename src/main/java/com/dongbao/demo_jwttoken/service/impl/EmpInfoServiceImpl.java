package com.dongbao.demo_jwttoken.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dongbao.demo_jwttoken.entity.EmpInfo;
import com.dongbao.demo_jwttoken.mapper.EmpInfoMapper;
import com.dongbao.demo_jwttoken.service.EmpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author : wangjunyue
 * date: 2025/11/8 11:33
 * Description: com.dongbao.demo_jwttoken.service.impl
 * project: demo_JWTToken
 */

@Service
public class EmpInfoServiceImpl extends ServiceImpl<EmpInfoMapper, EmpInfo> implements EmpInfoService {

    @Autowired
    private EmpInfoMapper empInfoMapper;

    @Override
    public EmpInfo getEmpInfoByEmpNo(String empNo) {
        return empInfoMapper.getEmpInfoByEmpNo(empNo);
    }
}
