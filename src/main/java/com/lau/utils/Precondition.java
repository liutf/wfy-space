package com.lau.utils;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *  参数校验
 * Created by liutf on 2016/8/5.
 */
public class Precondition {

    private static final Logger logger = LoggerFactory.getLogger(Precondition.class);

    /**
     *  参数校验,expression为false时表示校验不通过,抛出非法参数异常.
     * @param key
     * @param value
     * @throws Exception
     */
    public static void checkArgument(String key,Object value) throws Exception{
        logger.debug("checkArgument: key:{} , value:{}" , key,value);
        boolean expression = false;
        if(value instanceof String){
            expression = StringUtil.isNotBlank((String) value);
        }
        Preconditions.checkArgument(expression, "Param ["+ key +"] isBlank.");
    }

    /**
     *  参数校验: 当字符串值为{ null || "" || " "  || "null" } 抛出非法参数异常.
     * @param key
     * @param value
     * @throws Exception
     */
    public static void checkStringArgument(String key,String value) throws Exception{
        logger.debug("checkArgument: key:{} , value:{}" , key,value);
        boolean expression = StringUtil.isNotBlank(value);
        Preconditions.checkArgument(expression, "Param ["+ key +"] isBlank.");
    }

    /**
     *  业务参数校验
     * @param paraMap
     * @throws Exception
     */
    public void busiParamCheck(Map<String, String> paraMap) throws Exception {
        for (Map.Entry<String, String> entry : paraMap.entrySet()) {
            checkArgument(entry.getKey(),entry.getValue());
        }
    }

}
