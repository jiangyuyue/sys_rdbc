/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jiang.sys_rdbc.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


/**
 * 系统用户Token
 *
 * @author 蒋雨岳
 */
@Data
@TableName("sys_user_token")
public class SysUserTokenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId(type = IdType.INPUT)
	private Long userId;
	/**
	 * Token
	 */
	private String token;
	/**
	 * 过期试间
	 */
	private Date expireTime;
	/**
	 * 更新试间
	 */
	private Date updateTime;


}
