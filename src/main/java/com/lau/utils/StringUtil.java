package com.lau.utils;


import org.apache.commons.lang.StringUtils;

/**
 * Created by liutf on 2016/4/25.
 */
public class StringUtil {

    /**
     *  判断字符串不为""," ","null".
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        return StringUtils.isBlank(str) || "null".equals(str) ? true : false;
    }

    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }


}
