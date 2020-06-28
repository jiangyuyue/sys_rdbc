package com.jiang.sys_rdbc.annotion;

import java.lang.annotation.*;

/**
 * @author 蒋雨岳
 * @Date 2020/6/28 0028
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
