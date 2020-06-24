package com.jiang.sys_rdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.User;

/**
 * @author 蒋雨岳
 *
 */
public interface UserService extends IService<User> {


    R userList(Integer currentPage, Integer rows);
}
