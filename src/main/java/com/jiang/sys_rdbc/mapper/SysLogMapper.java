package com.jiang.sys_rdbc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.sys_rdbc.entity.SysLogEntity;

/**
 * @author 蒋雨岳
 * @Date 2020/6/28 0028
 */
@Mapper
@Component
public interface SysLogMapper extends BaseMapper<SysLogEntity> {
}
