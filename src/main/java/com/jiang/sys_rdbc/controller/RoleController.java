package com.jiang.sys_rdbc.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.SysRoleEntity;
import com.jiang.sys_rdbc.entity.SysRoleMenuEntity;
import com.jiang.sys_rdbc.entity.SysUserRoleEntity;
import com.jiang.sys_rdbc.entity.vo.MenuVoEntity;
import com.jiang.sys_rdbc.service.MenuService;
import com.jiang.sys_rdbc.service.RoleMenuService;
import com.jiang.sys_rdbc.service.RoleServcie;
import com.jiang.sys_rdbc.service.UserRoleService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@RestController()
@RequestMapping("/role")
public class RoleController extends AbstractController {

    @Autowired
    RoleServcie             roleServcie;

    @Autowired
    UserRoleService         userRoleService;

    @Autowired
    RoleMenuService         roleMenuService;

    /**
     * 保存角色 （新增和更新）
     * @param role
     * @return
     */
    @PostMapping("/saveRole")
    public R saveRole(@RequestBody SysRoleEntity role) {

        //id==null, 为新增
        if (role.getRoleId() == null) {
            role.setCreateUserId(getUser().getUserId());
            role.setCreateTime(new Date());
        }
        roleServcie.saveOrUpdate(role);

        return R.ok();
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @PostMapping("/remove")
    @Transactional(rollbackFor = Exception.class)
    public R remove(Long roleId) {

        //删除角色
        roleServcie.remove(new QueryWrapper<SysRoleEntity>().eq("role_id", roleId));
        //删除用户-角色
        userRoleService.remove(new QueryWrapper<SysUserRoleEntity>().eq("role_id", roleId));
        //删除 角色-菜单
        roleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("role_id", roleId));

        return R.ok();
    }

    /**
     * 批量删除角色
     * @param ids 用逗号隔开
     * @return
     */
    @PostMapping("/removeList")
    @Transactional(rollbackFor = Exception.class)
    public R removeList(String ids) {

        //分割 逗号
        String[] roles = ids.split(",");

        //循环删除
        for (String id : roles) {

            //调用单个删除方法
            remove(Long.valueOf(id));
        }
        return R.ok();

    }

    /**
     * 角色列表  当前页 和记录数
     * @return
     */
    @GetMapping("/roleList")
    public R roleList(Integer currentPage, Integer rows) {

        return roleServcie.roleList(currentPage, rows);

    }

    /**
     * 角色详情
     *
     */
    @GetMapping("/roleInfo")
    public R getRole(Long roleId) {
        if (roleId == null) {
            return R.error("参数不能为空！");
        }

        SysRoleEntity roleEntity = roleServcie.getOne(new QueryWrapper<SysRoleEntity>().eq("role_id", roleId));
        return R.ok().put("role", roleEntity);
    }

    /**
     * 给角色分配菜单（权限）
     * @param roleId  角色id
     * @param menuIds  逗号隔开 菜单ids（包含一级 二级 三级所有选中的id）
     * @return
     */
    @PostMapping("/distributionMenu")
    @Transactional(rollbackFor = Exception.class)
    public R distributionMenu(Long roleId, String menuIds) {

        //删除旧记录
        roleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("role_id",roleId));

        String[] ids = menuIds.split(",");
        //新数据批量插入角色-菜单表
        for (String id : ids) {
            SysRoleMenuEntity entity = new SysRoleMenuEntity();
            entity.setMenuId(Long.valueOf(id));
            entity.setRoleId(roleId);
            entity.setCreateUserId(getUser().getUserId());
            roleMenuService.save(entity);
        }

        return R.ok();

    }


    @Autowired
    MenuService  menuService;
    /**
     * 按角色显示菜单树
     *第一次加载传入 parentId=0
     */
    public R menuList(Long roleId,Long parentId) {

        //查询角色关联的菜单
        List<SysRoleMenuEntity> list = roleMenuService.list(new QueryWrapper<SysRoleMenuEntity>().eq("role_id", roleId));

        //查询所有parentId子菜单 并标记属性(selected)
        List<MenuVoEntity> voEntityList = menuService.listByParentId(parentId);

        //遍历标记
        if (!CollectionUtils.isEmpty(list)){
            for (SysRoleMenuEntity roleMenuEntity : list) {

                for (MenuVoEntity menuVoEntity : voEntityList) {

                    //标记
                    if (roleMenuEntity.getMenuId().longValue()==menuVoEntity.getMenuId().longValue()){
                        menuVoEntity.setSelected(1);
                    }
                }

            }
        }

        return R.ok().put("list",voEntityList);
    }
}
