package com.zzrq.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtil {
    /**
     * 根据URL获取domain
     * @param url
     * @return
     */
    public static String getDomainForUrl(String url){

        String domainUrl = null;
        if (url == null) {
            return null;
        } else {
            Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            while(matcher.find()){
                domainUrl = matcher.group();
            }
            return domainUrl;
        }
    }
}
