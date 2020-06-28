package com.jiang.sys_rdbc.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author 蒋雨岳
 * @Date 2020/6/28 0028
 */
@Data
@TableName("sys_log")
public class SysLogEntity {

    @TableId
    private Long   id;

    /**
     * 用户ID
     */
    private Long   userId;

    /**
     * 用户名
     */
    private String username;
    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时间
     */
    private Long   time;

    /**
     * 请求Ip
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date   createDate;

}
