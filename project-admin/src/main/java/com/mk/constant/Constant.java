package com.mk.constant;

public class Constant {
    /**
     * 登录token
     */
    public static final String LOGIN_TOKEN="login:token:";
    /**
     * token失效时间
     */
    public static final Long TOKEN_EXPIRE_TIME= 30L;

    public static final String REGISTER_CODE= "register:code:";

    /**
     * 注册验证码失效时间
     */
    public static final Long CODE_EXPIRE_TIME= 5L;

    /**
     * 登录昵称前缀
     */
    public static final String REGISTER_NICKNAME_PREFIX="user:";

    public static final String PROVINCE_CITY_AREA="province:city:area";

    /**
     * redis分布式锁
     */
    public static final String REDIS_LOCK="lock:";
    public static final Long REDIS_LOCK_EXPIRE_TIME= 1L;

}
