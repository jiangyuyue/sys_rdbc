package com.jiang.sys_rdbc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.sys_rdbc.common.utils.PageResult;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.SysRoleEntity;
import com.jiang.sys_rdbc.entity.User;
import com.jiang.sys_rdbc.mapper.SysRoleMapper;
import com.jiang.sys_rdbc.service.RoleServcie;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@Service
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements RoleServcie {
    @Override
    public R roleList(Integer currentPage, Integer rows) {

        currentPage=(currentPage==null || currentPage==0)?1:currentPage;

        //必须先写
        PageHelper.startPage(currentPage, rows);
        List<SysRoleEntity> list = this.list();
        PageInfo<User>  pageInfo= new PageInfo(list);

        //封装分页组件
        PageResult<User> userPageResult = new PageResult<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());

        return R.ok().put("page",userPageResult);

    }
}
