package com.jiang.sys_rdbc.common.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 蒋雨岳
 * @Date 2020/3/20 0020
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

    private int     pageNum;
    private int     pageSize;
    private Long    total;
    private List<T> list;
}
