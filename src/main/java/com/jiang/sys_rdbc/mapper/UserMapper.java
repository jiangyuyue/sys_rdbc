package com.jiang.sys_rdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.sys_rdbc.entity.User;

/**
 * @author 蒋雨岳
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Select("select m.perms from sys_user_role ur \n" +
            "\t\t\tLEFT JOIN sys_role_menu rm on ur.role_id = rm.role_id \n" +
            "\t\t\tLEFT JOIN sys_menu m on rm.menu_id = m.menu_id \n" +
            "\t\twhere ur.user_id = #{userId}")
    List<String> queryAllPerms(long userId);

   // @ResultMap()
}
