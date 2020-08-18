package com.jiang.sys_rdbc.aspect;

import com.jiang.sys_rdbc.annotion.FromRepeat;
import com.jiang.sys_rdbc.common.exception.RRException;
import com.jiang.sys_rdbc.common.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


/**
 * 幂等性处理类 史上最优雅处理表单重复提交写法
 */
@Aspect
@Component
public class FromRepeatAspect {


    @Autowired
    RedisTemplate<String,String>  template;

    /**
     *  切面
     */
    @Pointcut("@annotation(com.jiang.sys_rdbc.annotion.FromRepeat)")
    public  void logPoint(){

    }

    @Around("logPoint()")
    public  Object  around(ProceedingJoinPoint point)  throws  Throwable {

        Long startTime=System.currentTimeMillis();
        System.out.println("开始执行");

        //执行方法
        Object result = point.proceed();

        Long endTime=System.currentTimeMillis();
        System.out.println("执行"+(endTime-startTime));

        //请求方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //方法名
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        //获表单注解
        FromRepeat fromRepeat = method.getAnnotation(FromRepeat.class);

        //获取设定时间
        int seconds=0;
        if (fromRepeat!=null){
            seconds=Integer.parseInt(fromRepeat.seconds());
        }

        //请求端Ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddr = IPUtils.getIpAddr(request);

        //生成Token
        String token= DigestUtils.md5DigestAsHex((ipAddr+className+methodName).getBytes()).replaceAll("-","");

        //
        if (!template.opsForValue().setIfAbsent(token, "", seconds, TimeUnit.SECONDS)){
            throw  new RRException(seconds+"秒内不可重复提交！");
        }
        return  result;
    }
}
