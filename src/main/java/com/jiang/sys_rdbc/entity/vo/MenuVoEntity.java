package com.jiang.sys_rdbc.entity.vo;

import lombok.Data;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23
 */
@Data
public class MenuVoEntity {

    private Long menuId;

    private String name;

    private Integer type;

    private Boolean parented;

    /**
     * 是否被选中0未选中 1选中
     */
    private Integer selected=0;

    /**
     * 前端跳转页面路径
     */
    private String url;

}
