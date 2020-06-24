package com.jiang.sys_rdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.sys_rdbc.entity.SysMenuEntity;
import com.jiang.sys_rdbc.entity.vo.MenuVoEntity;

/**
 * @author 蒋雨岳
 *菜单mapper
 */
@Mapper
@Component
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {


    @Select("select menu_id,name,type,parented  from sys_menu where parent_id=#{parentId}  order by order_num ASC")
    List<MenuVoEntity> listByParentId(@Param("parentId") Long parentId);


    @Select("SELECT  menu_id,name,type,parented,url  from sys_menu  where menu_id in\n" +
            "       (  SELECT  menu_id from sys_role_menu where  role_id in \n" +
            "        (SELECT role_id  from sys_user_role where user_id=#{userId})\n" +
            "        ) and parent_id=#{parentId} ORDER BY order_num ASC")
    List<MenuVoEntity> listByParentId2(@Param("userId") Long userId, @Param("parentId") Long parentId);
}


