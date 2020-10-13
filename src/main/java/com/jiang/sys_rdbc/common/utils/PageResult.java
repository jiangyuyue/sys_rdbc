package com.jiang.sys_rdbc.common.utils;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jiang.sys_rdbc.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 蒋雨岳
 * @Date 2020/3/20 0020
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

    /**
     * 当前页
     */
    private int     pageNum;

    /**
     * 每页的数量
     */
    private int     pageSize;

    /**
     *  总页数
     */
    private Long    total;

    /**
     * 当前页结果集
     */
    private List<T> list;

    /**
     * 总页数
     * 
     */

    private int     pages;

    public PageResult(PageInfo pageInfo) {
        this.list = pageInfo.getList();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
        pages = pageInfo.getPages();
    }

    public PageResult(int pageNum, int pageSize, long total, List<User> list) {
    }
}
