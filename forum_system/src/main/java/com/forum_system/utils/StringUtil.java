package com.forum_system.utils;

public class StringUtil {




    /**
     *判断字符串是否是空
     * true：为空
     * false：非空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;

    }
}
