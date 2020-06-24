package com.jiang.sys_rdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.service.impl.LoginServcieImpl;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 * 登录请求
 */
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    LoginServcieImpl loginServcieImpl;

    /**
     * 登录
      * @param userName
     * @param password
     * @return
     *
     */
    @PostMapping("/sys/login")
    public R login(String userName, String password) {

        return loginServcieImpl.login(userName, password);

    }

}
