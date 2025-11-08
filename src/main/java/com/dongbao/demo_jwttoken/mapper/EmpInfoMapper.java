package com.dongbao.demo_jwttoken.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongbao.demo_jwttoken.entity.EmpInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author : wangjunyue
 * date: 2025/11/8 11:32
 * Description: com.dongbao.demo_jwttoken.mapper
 * project: demo_JWTToken
 */
@Mapper
public interface EmpInfoMapper extends BaseMapper<EmpInfo> {


    @Select("select * from emp_info where emp_no = #{empNo} and is_delete=0 and enable_date<=now() and unable_date>=now()")
    EmpInfo getEmpInfoByEmpNo(String empNo);
}
