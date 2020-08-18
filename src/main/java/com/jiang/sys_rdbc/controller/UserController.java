package com.jiang.sys_rdbc.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.SysUserRoleEntity;
import com.jiang.sys_rdbc.entity.User;
import com.jiang.sys_rdbc.entity.vo.MenuVoEntity;
import com.jiang.sys_rdbc.service.MenuService;
import com.jiang.sys_rdbc.service.RoleMenuService;
import com.jiang.sys_rdbc.service.UserRoleService;
import com.jiang.sys_rdbc.service.UserService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRoleService     userRoleService;

    @Autowired
    RoleMenuService     roleMenuService;

    @Autowired
    MenuService         menuService;

    /**
     * 保存用户（或更新）
     * @param user
     * @return
     */
    @PostMapping("/save")
    public R SaveUser(@RequestBody User user) {

        //为空是新增
        if (user.getUserId() == null) {
            //当前用户
            user.setCreateUserId(getUser().getUserId());
        }

        boolean save = userService.saveOrUpdate(user);
        if (save == false) {
            return R.error("保存失败！");
        }
        return R.ok();
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping("/remove")
    @Transactional(rollbackFor = Exception.class)
    public R removeUser(Long userId) {

        //未作标记删除(删除用户)
        userService.remove(new QueryWrapper<User>().eq("user_id", userId));

        //查询用户-角色列表，删除角色-菜单数据
        //        List<SysUserRoleEntity> userRoleEntityList = userRoleService
        //                .list(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId));
        //        if (!CollectionUtils.isEmpty(userRoleEntityList)) {
        //            userRoleEntityList.forEach(userRole -> roleMenuService
        //                    .remove(new QueryWrapper<SysRoleMenuEntity>().eq("role_id", userRole.getRoleId())));
        //        }

        //删除 用户-角色
        userRoleService.remove(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId));

        return R.ok();
    }

    /**
     * 用户列表
     * @param currentPage  分页当前页
     * @param rows     每页记录数
     * @return
     */
    @GetMapping("/userList")
    public R userList(Integer currentPage, Integer rows) {

        return userService.userList(currentPage, rows);

    }

    /**
     *
     * @param userId
     * @return 用户详情
     */
    @GetMapping("/userInfo")
    public R userInfo(Long userId) {

        User user = userService.getById(userId);
        if (user == null) {
            return R.error("用户已经不存在！，请刷新页面");
        }

        return R.ok().put("user", user);

    }

    /**
     * 批量删除用户
     * @param ids
     */
    @PostMapping("/deleteByIds")
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(String ids) {

        if (StringUtils.isBlank(ids)) {
            return;
        }
        String[] idstr = ids.split(",");

        //循环删除
        for (String id : idstr) {
            //调用单个删除方法
            removeUser(Long.valueOf(id));
        }
    }

    /**
     *
     * @param userId
     * @param roleIds 角色ids
     * @return   给用户分配角色
     */
    @PostMapping("/saveUserRole")
    @Transactional(rollbackFor = Exception.class)
    public R distributionRole(Long userId, String roleIds) {

        //当前用户
        Long currentUserId = getUser().getUserId();

        //删除旧数据
        userRoleService.remove(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId));

        String[] split = roleIds.split(",");
        //新数据循环插入用户—角色表
        for (String id : split) {
            Long roleid = Long.valueOf(id);
            SysUserRoleEntity entity = new SysUserRoleEntity();
            entity.setRoleId(roleid);
            entity.setUserId(userId);
            entity.setCreateUserId(currentUserId);
            userRoleService.save(entity);
        }
        return R.ok();
    }

    /**
     * 按照当前用户遍历树
     * 首页加载 前端传入  parentId=0
     * 目录显示后 将当前menuId  parentId={menuId}
     * 用字段if(parented==true)   请求接口还是跳转页面
     * @param parentId
     * @return
     */
    @GetMapping("/menuList")
    public R menuList(Long parentId) {
        List<MenuVoEntity> menuVoEntities = menuService.listByParentId(getUser().getUserId(), parentId);
        return R.ok().put("menuList", menuVoEntities);
    }

}
