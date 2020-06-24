package com.jiang.sys_rdbc.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jiang.sys_rdbc.config.shiroOauth.OAuth2Filter;
import com.jiang.sys_rdbc.config.shiroOauth.OAuth2Realm;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 * shiro配置
 */
@Configuration
public class ShrioConfig {


    /**
     * 这两个必须写到最上面，否则权限注解不生效
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        // new DefaultAdvisorAutoProxyCreator().setProxyTargetClass(true);
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }

    /**
     * 这两个必须写到最上面，否则权限注解不生效
     * @return
     */
    @Bean("defaultAdvisorAutoProxyCreator")
    public DefaultAdvisorAutoProxyCreator getAdvisorAutoProxyCreator(LifecycleBeanPostProcessor lifecycleBeanPostProcessor) {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }



    //1.创建ShiroFilterFactoryBean
    //2. 创建DefaultWebSecurityManager
    //3. 创建Realm（需要自定义）

    @Bean("sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }

    @Bean("securityManager") //安全管理器 关联 Realm
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm, SessionManager sessionManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // "anon": 无需认证 （登录）可以访问
        // authc   必须认证才能访问  oauth2
        // user : 如果施工remeberMe的功能可以直接访问
        // perms : 该资源必须得到资源权限才可以访问
        //  role :该资源必须得到角色全下才可以访问

        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();

        filters.put("oauth2", new OAuth2Filter());
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/sys/login", "anon");
        filterMap.put("/**", "oauth2");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        // shiroFilterFactoryBean.setLoginUrl("/toLogin");
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
