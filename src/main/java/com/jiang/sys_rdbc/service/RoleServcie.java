package com.jiang.sys_rdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.SysRoleEntity;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
public interface RoleServcie extends IService<SysRoleEntity> {
    R roleList(Integer currentPage, Integer rows);
}
