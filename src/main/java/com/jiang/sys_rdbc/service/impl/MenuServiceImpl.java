package com.jiang.sys_rdbc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.sys_rdbc.entity.SysMenuEntity;
import com.jiang.sys_rdbc.entity.vo.MenuVoEntity;
import com.jiang.sys_rdbc.mapper.SysMenuMapper;
import com.jiang.sys_rdbc.service.MenuService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@Service
public class MenuServiceImpl  extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements MenuService {


    @Autowired
    SysMenuMapper sysMenuMapper;

    /**
     * 父节点 查找子节点
     * @param parentId
     * @return
     * 第一次前端传0，显示一级菜单
     * 以后点击菜单传 menuId
     */
    @Override
    public List<MenuVoEntity> listByParentId(Long parentId) {


        return sysMenuMapper.listByParentId(parentId);
    }

    /**
     * 按照当前用户父节点 查找子节点
     * @param parentId  父节点Id
     * @param userId  用户Id
     * @return
     *
     * 第一次前端传0，当前用户看到一级
     * 以后点击菜单传 menuId
     */
    @Override
    public List<MenuVoEntity> listByParentId(Long userId, Long parentId) {

        return sysMenuMapper.listByParentId2(userId,parentId);
    }
}
