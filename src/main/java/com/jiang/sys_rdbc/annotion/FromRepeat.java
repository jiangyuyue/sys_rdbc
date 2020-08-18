package com.jiang.sys_rdbc.annotion;

/**
 * 表单重复提交annontion
 *
 */
public @interface FromRepeat {
    String seconds() default "0";
}
