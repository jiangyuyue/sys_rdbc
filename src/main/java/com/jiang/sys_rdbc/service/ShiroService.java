/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jiang.sys_rdbc.service;

import java.util.Set;

import com.jiang.sys_rdbc.entity.SysUserTokenEntity;
import com.jiang.sys_rdbc.entity.User;

/**
 * shiro相关接口
 *
 *
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    User queryUser(Long userId);
}
