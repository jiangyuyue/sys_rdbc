package com.jiang.sys_rdbc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.sys_rdbc.common.utils.PageResult;
import com.jiang.sys_rdbc.common.utils.R;
import com.jiang.sys_rdbc.entity.User;
import com.jiang.sys_rdbc.mapper.UserMapper;
import com.jiang.sys_rdbc.service.UserService;

/**
 * @author 蒋雨岳
 * @Date 2020/3/20 0020
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public R userList(Integer currentPage, Integer rows) {

        currentPage=(currentPage==null || currentPage==0)?1:currentPage;

        //分页插件
        PageHelper.startPage(currentPage,rows);
        List<User> userList = this.list();
        PageInfo<User> pageInfo = new PageInfo(userList);

        //分页组件
        //        List<User> infoList = pageInfo.getList(); //当前页结果集
        //
        //        int pageNum = pageInfo.getPageNum(); //当前页
        //
        //        long total=pageInfo.getTotal();  //总记录数
        //
        //        int pageSize = pageInfo.getPageSize();//每页的数量

        return R.ok().put("page", new PageResult<User>(pageInfo));
    }
}
