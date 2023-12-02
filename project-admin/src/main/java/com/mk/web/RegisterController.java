package com.mk.web;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.jwt.JWTUtil;
import com.mk.bean.AjaxResult;
import com.mk.bean.RegisterBody;
import com.mk.constant.Constant;
import com.mk.service.ProvinceService;
import com.mk.service.RegisterService;
import com.mk.service.UserService;
import com.mk.utils.RegUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static com.mk.constant.Constant.*;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private ProvinceService provinceService;
    @GetMapping("province")
    private AjaxResult getProvince() {
        return AjaxResult.success(provinceService.queryAll());
    }
    /**
     * 手机号注册生成验证码
     *
     * @param registerBody
     * @return
     */
    @PostMapping("sendCode")
    private AjaxResult sendCode(@RequestBody RegisterBody registerBody) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        System.out.println(requestAttributes);
        if (!RegUtil.isValidPhoneNumber(registerBody.getPhone())) {
            throw new RuntimeException("手机号格式错误!");
        }
        boolean existUser = userService.getUserByPhone(registerBody.getPhone());
        //手机号已存在
        if (existUser) {
            throw new RuntimeException("当前手机号已注册!");
        }
        //生成6位验证码设置失效时间
        String code = RandomUtil.randomString(6);
        redis.opsForValue().set(REGISTER_CODE + registerBody.getPhone(), code);
        redis.expire(REGISTER_CODE + registerBody.getPhone(), CODE_EXPIRE_TIME, TimeUnit.MINUTES);
        return AjaxResult.success("验证码已发送");
    }

    /**
     * 用户名注册
     * @param username
     * @param response
     */
    @PostMapping("captcha/{username}")
    public void  captcha(@PathVariable("username") String username, HttpServletResponse response) {
        registerService.captcha(username,response);
    }

    /**
     * 用户注册
     *
     * @param registerBody
     * @return
     */
    @PostMapping("register")
    public AjaxResult register(@RequestBody RegisterBody registerBody) {
        return registerService.register(registerBody);
    }

}
