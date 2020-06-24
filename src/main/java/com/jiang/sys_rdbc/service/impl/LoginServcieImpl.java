package com.jiang.sys_rdbc.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.User;
import com.jiang.sys_rdbc.service.SysUserTokenService;
import com.jiang.sys_rdbc.service.UserService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@Service("loginServcieImpl")
public class LoginServcieImpl {

    @Autowired
    UserService userService;

    @Autowired
    SysUserTokenService sysUserTokenService;

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
   public R  login(String userName,String password){

       if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)){

           return R.error("用户名和密码不能为空！");
       }

       //查找用户
       User user = userService.getOne(new QueryWrapper<User>().eq("user_name", userName));
       if (user==null){
           return R.error("用户名不存在！");
       }

       if (!password.equals(user.getPassword())){
           return R.error("密码错误！");
       }

        //生成token，并保存到数据库
        return sysUserTokenService.createToken(user.getUserId());

   }

}
