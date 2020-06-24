package com.jiang.sys_rdbc.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.sys_rdbc.entity.SysRoleMenuEntity;
import com.jiang.sys_rdbc.mapper.SysRoleMenuMapper;
import com.jiang.sys_rdbc.service.RoleMenuService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper,SysRoleMenuEntity> implements RoleMenuService {
}
