package com.ljh;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户对象
 * @author lvjinhui
 * @date 2022/03/04 15:26
 **/
public class UserContext {
    private static ThreadLocal<Map<String, Object>> threadLocal;
    /**
     * 用户idKey
     */
    public static final String CONTEXT_KEY_USER_ID = "userId";
    /**
     * 用户名
     */
    public static final String CONTEXT_KEY_USER_NAME = "userName";

    /**
     * 用户租户ID
     */
    public static final String CONTEXT_KEY_USER_TENANT_ID="tenantId";

    static {
        threadLocal = new ThreadLocal<>();
    }

    /**
     * 设置数据
     *
     * @param key 键
     * @param value 值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        return map.get(key);
    }

    /**
     * 清除数据
     *
     */
    public static void remove() {
        threadLocal.remove();
    }
    public static void setUserId(String userId) {
        set(CONTEXT_KEY_USER_ID, userId);
    }

    public static Long getUserId() {
        Object value = get(CONTEXT_KEY_USER_ID);
        return Long.valueOf(String.valueOf(value));
    }

    public static void setUserName(String userName) {
        set(CONTEXT_KEY_USER_NAME, userName);
    }

    public static String getUserName() {
        Object value = get(CONTEXT_KEY_USER_NAME);
        return String.valueOf(value);
    }

    public static void setTenantId(String tenantId) {
        set(CONTEXT_KEY_USER_TENANT_ID, tenantId);
    }

    public static String getTenantId() {
        Object value = get(CONTEXT_KEY_USER_TENANT_ID);
        return String.valueOf(value);
    }
    
}
