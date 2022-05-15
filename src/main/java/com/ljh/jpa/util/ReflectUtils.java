package com.ljh.jpa.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReflectUtils {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");


    /**
     * 驼峰转下划线
     * @date 2021/1/27 13:27
     * @since 0.1.1
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * getter方法名
     * @since 0.1.1
     * @date 2021/1/27 13:28
     */
    public static String getter(String str) {
        return "get" + str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * setter方法名
     * @since 0.1.1
     * @date 2021/1/27 13:28
     */
    public static String setter(String str) {
        return "set" + str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
