/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jiang.sys_rdbc.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.sys_rdbc.entity.SysUserTokenEntity;
import com.jiang.sys_rdbc.entity.User;
import com.jiang.sys_rdbc.mapper.SysMenuMapper;
import com.jiang.sys_rdbc.mapper.SysUserTokenMapper;
import com.jiang.sys_rdbc.mapper.UserMapper;
import com.jiang.sys_rdbc.service.ShiroService;


@Service
public class ShiroServiceImpl implements ShiroService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SysUserTokenMapper sysUserTokenMapper;

    @Override
    public Set<String> getUserPermissions(long userId) {
        //用户权限
        List<String> permsList = userMapper.queryAllPerms(userId);

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perm : permsList){
            if(StringUtils.isEmpty(perm)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perm.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        SysUserTokenEntity tokenEntity = sysUserTokenMapper.selectOne(new QueryWrapper<SysUserTokenEntity>().eq("token", token));
        return tokenEntity;
       // return sysUserTokenMapper.queryByToken(token);
    }

    @Override
    public User queryUser(Long userId) {
        return userMapper.selectById(userId);
    }
}
