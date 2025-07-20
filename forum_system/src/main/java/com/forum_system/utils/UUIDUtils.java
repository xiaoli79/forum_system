package com.forum_system.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 生成32位UUID
     * @return
     */

    public static String UUID_32() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 生成36位UUID
     * @return
     */
    public static String UUID_36() {
        return UUID.randomUUID().toString();
    }

}
