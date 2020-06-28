package com.jiang.sys_rdbc.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.jiang.sys_rdbc.annotion.SysLog;
import com.jiang.sys_rdbc.common.utils.IPUtils;
import com.jiang.sys_rdbc.entity.SysLogEntity;
import com.jiang.sys_rdbc.entity.User;
import com.jiang.sys_rdbc.mapper.SysLogMapper;

/**
 * @author 蒋雨岳
 * @Date 2020/6/28 0028
 * SysLog切面类
 */
@Aspect
@Component
public class LogAspect {


    @Autowired
    SysLogMapper sysLogMapper;

    /**
     *  切面
     */
    @Pointcut("@annotation(com.jiang.sys_rdbc.annotion.SysLog)")
    public  void logPoint(){

    }

    @Around("logPoint()")
    public  Object  around(ProceedingJoinPoint point)  throws  Throwable {

        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        /**
         * 保存日志
         */
        //构建日志实体
        SysLogEntity logEntity=new SysLogEntity();
        logEntity.setTime(time);

        //请求方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //获日志注解
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog!=null){
            //注解的描述
            logEntity.setOperation(sysLog.value());
        }

        //方法名
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        logEntity.setMethod(className+"."+methodName);

        //请求参数
        Object[] args = point.getArgs();
         List<Object> objects = Arrays.asList(args);
        logEntity.setParams( JSON.toJSONString(objects));

        //请求端Ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddr = IPUtils.getIpAddr(request);
        logEntity.setIp(ipAddr);

        //用户名 用户Id
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logEntity.setUserId(user.getUserId());
        logEntity.setUsername(user.getUserName());

        //创建日期
        logEntity.setCreateDate(new Date());

        //保存日志
        sysLogMapper.insert(logEntity);

        return result;
    }


}
