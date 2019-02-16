package com.zzrq.base.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {
    public static void setCookie(String name,String value,Integer maxAge,HttpServletRequest request,HttpServletResponse response) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name,URLEncoder.encode(value, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cookie.setMaxAge(maxAge);
        String domain = RequestUtil.getDomainForUrl(request.getRequestURI());
        if(!StringUtils.isEmpty(domain)) {
            cookie.setDomain(domain);
        }

        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        String value = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(),"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return value;
    }
}
