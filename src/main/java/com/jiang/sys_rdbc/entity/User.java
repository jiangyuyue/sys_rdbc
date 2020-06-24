package com.jiang.sys_rdbc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@Data
@TableName("sys_user")
public class User {

    @TableId
    private Long  userId;

    /**
     * 用户名
     */
    private  String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建者ID
     */
    private Long createUserId;


    /**
     * 用户状态
     */
    private Integer status;


    /**
     * 手机号
     */
    private String mobile;


    /**
     * 邮箱
     */
    private String email;




}
