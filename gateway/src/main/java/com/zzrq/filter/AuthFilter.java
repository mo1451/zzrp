package com.zzrq.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zzrq.base.dto.ResponseData;
import com.zzrq.base.utils.CookieUtil;
import com.zzrq.dto.FileFilterProperties;
import com.zzrq.dto.JWTProperties;
import com.zzrq.service.AuthFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public class AuthFilter extends ZuulFilter {

    @Autowired
    private JWTProperties properties;

    @Autowired
    private FileFilterProperties fileFilterProperties;

    @Autowired
    private AuthFeign authFeign;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest req = ctx.getRequest();
        // 获取路径
        String requestURI = req.getRequestURI();
        // 判断白名单
        return !isAllowPath(requestURI);
    }

    private boolean isAllowPath(String requestURI) {
        // 定义一个标记
        boolean flag = false;
        // 遍历允许访问的路径
        for (String path : this.fileFilterProperties.getAllowPaths()) {
            // 然后判断是否是符合
            if(requestURI.startsWith(path)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = context.getRequest();
        // 获取token
        String token = CookieUtil.getCookie(request, this.properties.getCookieName());
        // 校验
        try {
            // 校验通过什么都不做，即放行
            ResponseData responseData = authFeign.verify(token, request, context.getResponse());
            if(!responseData.isSuccess()) {
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                context.setResponseBody(responseData.getMessage());
            }
        } catch (Exception e) {
            // 校验出现异常，返回403
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
        return null;
    }
}
