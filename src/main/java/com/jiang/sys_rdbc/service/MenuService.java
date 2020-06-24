package com.jiang.sys_rdbc.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.sys_rdbc.entity.SysMenuEntity;
import com.jiang.sys_rdbc.entity.vo.MenuVoEntity;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
public interface MenuService extends IService<SysMenuEntity> {

    //父节点 查找子节点
    List<MenuVoEntity>  listByParentId(Long parentId);

    //父节点 查找子节点（按照当前用户）
    List<MenuVoEntity>  listByParentId(Long userId,Long parentId);
}
