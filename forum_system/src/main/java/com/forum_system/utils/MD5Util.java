package com.forum_system.utils;

import org.springframework.util.DigestUtils;

import java.util.Arrays;

public class MD5Util {
    public static String md5(String str) {
//      对这个进行加密
        return DigestUtils.md5DigestAsHex(str.getBytes());

    }


    /**
     * 原始字符串与Key组合进行一次MD5加密
     * @param str
     * @param key
     * @return
     */
    public static String md5(String str,String key) {
//      对这个进行加密
        return DigestUtils.md5DigestAsHex((str+key).getBytes());

    }

    /**
     * 原始字符串加密后+上盐值再进行一次加密
     * @param str
     * @param salt
     * @return
     */

    public static String md5Salt(String str,String salt) {
        return DigestUtils.md5DigestAsHex((DigestUtils.md5DigestAsHex(str.getBytes())+salt).getBytes());
    }


}
