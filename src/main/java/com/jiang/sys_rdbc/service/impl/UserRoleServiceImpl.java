package com.jiang.sys_rdbc.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.sys_rdbc.entity.SysUserRoleEntity;
import com.jiang.sys_rdbc.mapper.SysUserRoleMapper;
import com.jiang.sys_rdbc.service.UserRoleService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@Service
public class UserRoleServiceImpl  extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements UserRoleService {
}
