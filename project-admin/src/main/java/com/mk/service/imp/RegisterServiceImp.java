package com.mk.service.imp;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.IOUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.mk.bean.AjaxResult;
import com.mk.bean.RegisterBody;
import com.mk.bean.User;
import com.mk.mapper.UserMapper;
import com.mk.service.RegisterService;
import com.mk.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.mk.constant.Constant.*;

@Service
public class RegisterServiceImp implements RegisterService {
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Producer producer;

    /**
     * 手机号注册
     *
     * @param registerBody
     * @return
     */
    @Override
    public String registerByPhone(RegisterBody registerBody) {
        return null;
    }

    /**
     * 用户名注册
     *
     * @param registerBody
     * @return
     */
    @Override
    public String registerByName(RegisterBody registerBody) {
        return null;
    }

    /**
     * 注册
     * @param registerBody
     * @return
     */
    @Override
    public AjaxResult register(RegisterBody registerBody) {
        User insertUser = new User();
        insertUser.setType(2);
        insertUser.setLoginTime(new Date());
        insertUser.setNickName(REGISTER_NICKNAME_PREFIX + RandomUtil.randomNumbers(8));
        if (registerBody.getType() == 1) {
            if (StrUtil.isEmpty(registerBody.getUsername())) throw new RuntimeException("注册用户名不能为空!");
            String captchaCode = redis.opsForValue().get(CAPTCHA_CODE + registerBody.getUsername());
            if (StrUtil.isEmpty(captchaCode)) throw new RuntimeException("验证码已过期,请重试!");
            //用户名注册
            User user = userMapper.selectByUserName(registerBody.getUsername());
            if (user != null) {
                throw new RuntimeException("用户已存在");
            }
            insertUser.setUsername(registerBody.getUsername());
            insertUser.setPassword(registerBody.getPassword());
        } else {
            //手机号注册
            User user = userMapper.getUserByPhone(registerBody.getPhone());
            if (user != null) {
                throw new RuntimeException("当前手机号已被注册，请更换手机号！");
            }
            String code = registerBody.getCode();
            String cacheCode = redis.opsForValue().get(REGISTER_CODE + registerBody.getPhone());
            if (code == null || !code.equals(cacheCode)) {
                throw new RuntimeException("验证码错误");
            }
            insertUser.setPhone(registerBody.getPhone());
            insertUser.setPassword("123456");
        }
        userMapper.insertUser(insertUser);

        String token = JwtUtil.createToken(insertUser);
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", registerBody.getPhone());
        redis.opsForHash().putAll(LOGIN_TOKEN + token, map);
        redis.expire(LOGIN_TOKEN + token, TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        HashMap<String, Object> resData = new HashMap<>();
        resData.put("token", token);
        return AjaxResult.success("注册成功", resData);
    }

    /**
     *
     * @param username
     * @param response
     */
    @Override
    public void captcha(String username, HttpServletResponse response) {
        if (StrUtil.isEmpty(username)) throw new RuntimeException("username不能为空");
        //浏览器不缓存返回的数据
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        String code = producer.createText();
        BufferedImage imageBuffer = producer.createImage(code);
        try {
            ServletOutputStream ops = response.getOutputStream();
            ImageIO.write(imageBuffer, "jpg", ops);
            IoUtil.close(ops);
            redis.opsForValue().set(CAPTCHA_CODE + username, code, CAPTCHA_CODE_EXPIRE, TimeUnit.MINUTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
