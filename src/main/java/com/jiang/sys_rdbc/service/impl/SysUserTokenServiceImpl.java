package com.jiang.sys_rdbc.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.SysUserTokenEntity;
import com.jiang.sys_rdbc.mapper.SysUserTokenMapper;
import com.jiang.sys_rdbc.service.SysUserTokenService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserTokenEntity> implements SysUserTokenService {

   //过期时间
    private static  final int EXPIRE_TIME=2;

    /**
     * 生成Token
     * @param usrid
     * @return
     */
    @Override
    public R createToken(Long usrid) {

       //创建Token
        String token= UUID.randomUUID().toString().replaceAll("-","");

        //当前时间
         Date now = new Date();;

        //过期时间(2个小时)
        Date expireTime = new Date(now.getTime()+ EXPIRE_TIME*60*60*1000);

        SysUserTokenEntity tokenEntity = this.getOne(new QueryWrapper<SysUserTokenEntity>().eq("user_id",usrid));

        //判断是否创建过
        if (tokenEntity==null){

            tokenEntity=new SysUserTokenEntity();

            tokenEntity.setUserId(usrid);
            tokenEntity.setToken(token);
            tokenEntity.setExpireTime(expireTime);
            tokenEntity.setUpdateTime(now);

            //保存
            this.save(tokenEntity);
        }else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新Token
            this.updateById(tokenEntity);
        }


        //返回token 给前端
        return R.ok().put("token", token).put("expire",EXPIRE_TIME);
    }


}
