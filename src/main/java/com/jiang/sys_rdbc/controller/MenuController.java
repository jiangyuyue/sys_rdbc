package com.jiang.sys_rdbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.sys_rdbc.annotion.SysLog;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.SysMenuEntity;
import com.jiang.sys_rdbc.entity.SysRoleMenuEntity;
import com.jiang.sys_rdbc.entity.vo.MenuVoEntity;
import com.jiang.sys_rdbc.service.MenuService;
import com.jiang.sys_rdbc.service.RoleMenuService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 * 菜单管理
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends AbstractController {

    @Autowired
    MenuService menuService;

    /**
     * 保存菜单
     * @param entity
     * @return
     */
    @PostMapping("/saveMenu")
    public R saveMenu(@RequestBody SysMenuEntity entity) {

        //新增
        if (entity.getMenuId() == null) {
            //父节点
            Long parentId = entity.getParentId();
            //非父节点
            entity.setParented(false);

            if (parentId == null) {

                //根节点
                entity.setParentId(0L);
                entity.setType(0);//   类型     0：目录(或一级菜单)   1：菜单(或二级菜单)    2：按钮(或三级菜单)
            } else if (parentId.longValue() == 0L) {
                //当前为二级菜单
                entity.setType(1);

            } else {
                //三级菜单
                entity.setType(2);
            }
        }
        menuService.saveOrUpdate(entity);

        return R.ok();

    }

    @Autowired
    RoleMenuService roleMenuService;

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/remove")
    public R remove(Long menuId) {

        //删除角色-菜单
        roleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("menu_id", menuId));

        //删除菜单
        menuService.remove(new QueryWrapper<SysMenuEntity>().eq("menu_id", menuId));

        return R.ok();

    }

    /**
     * 批量删除菜单
     * @param menuIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/removeList")
    public R removeList(String menuIds) {

        String[] ids = menuIds.split(",");
        for (String id : ids) {
            remove(Long.valueOf(id));
        }
        return R.ok();
    }

    /**
     * 菜单详情
     * @param menuId
     * @return
     */
    @GetMapping("/menuInfo")
    public R menuInfo(Long menuId) {

        SysMenuEntity menuEntity = menuService.getById(menuId);

        if (menuEntity == null) {
            return R.error("菜单已不存在，请刷新页面！");
        }
        return R.ok();
    }

    /**
     * 遍历树
     *  进入加载 前端传入  parentId=0
     * 目录显示后 将当前menuId  parentId={menuId}
     * 用字段if(parented==true)   请求接口或跳转页面
     * @param parentId  父节点id
     * @return
     * RequiresPermissions 权限注解
     */
    //@RequiresPermissions("menu:menuList")
    @SysLog("菜单列表")
    @GetMapping("/menuList")
    public R menuList(Long parentId, String logTestParam) {
        List<MenuVoEntity> menuVoEntities = menuService.listByParentId(parentId);

        return R.ok().put("menuList", menuVoEntities);
    }
}
