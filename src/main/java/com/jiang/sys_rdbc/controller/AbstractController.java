package com.jiang.sys_rdbc.controller;

import org.apache.shiro.SecurityUtils;

import com.jiang.sys_rdbc.entity.User;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 *  Controller公共组件
 */
public abstract class AbstractController {

    /**
     * 获取用户信息
     * @return
     */
    protected User getUser() {

        User userEntity = (User) SecurityUtils.getSubject().getPrincipal();

        return userEntity;
    }
}
