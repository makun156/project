package com.mk.service;

import com.mk.bean.AjaxResult;
import com.mk.bean.RegisterBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 注册登录接口
 */
public interface RegisterService {
    /**
     * 手机号注册
     * @param registerBody
     * @return
     */
    String registerByPhone(RegisterBody registerBody);

    /**
     * 用户名注册
     * @param registerBody
     * @return
     */
    String registerByName(RegisterBody registerBody);

    AjaxResult register(RegisterBody registerBody);

    void captcha(String username, HttpServletResponse response);
}
