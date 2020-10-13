/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jiang.sys_rdbc.config.shiroOauth;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jiang.sys_rdbc.common.utils.R;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 *
 *
 */
public class OAuth2Filter extends AuthenticatingFilter {

    //AuthenticationToken

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if (StringUtils.isEmpty(token)) {
            return null;
        }

        return new OAuth2Token(token);
    }

    /**
     * 所有请求全部拒绝访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
        //            return true;
        //        }

        return false;
    }

    /**
     * 拒绝访问的请求，会调用onAccessDenied方法，onAccessDenied方法先获取token，再调用 executeLogin方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>..");

        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);

        System.out.println("token>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + token);

        if (StringUtils.isEmpty(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));

            String json = JSON.toJSONString(R.error(401, "invalid token"));

            httpResponse.getWriter().print(json);

            return false;
        }

        return executeLogin(request, response);
    }

    /**
     * 登录失败后，则调用onLoginFailure，进行失败处理，整个流程结束
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            R r = R.error(401, throwable.getMessage());

            String json = JSON.toJSONString(r);
            httpResponse.getWriter().print(json);


        } catch (IOException e1) {

        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpRequest.getParameter("token");
        }

        return token;
    }

}
