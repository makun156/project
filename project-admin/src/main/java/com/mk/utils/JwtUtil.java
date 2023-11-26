package com.mk.utils;

import com.mk.bean.User;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Configuration
public class JwtUtil {
    /**
     * 携带JWT令牌的HTTP的Header的名称,在实际生产中可读性越差越安全
     */
    private static String header="Authorization";


    /**
     * 为JWT基础信息加密和解密的密钥
     * 在实际生产中通常不直接写在配置文件里面。而是通过应用的启动参数传递，并且需要定期修改。
     */
    private static String secret="mk-server";

    /**
     * JWT令牌的有效时间，单位秒
     * - 默认30分钟
     */
    private static Long expiration=1000*60*30L;

    public static String createToken(User user) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("phone", user.getPhone())
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, secret);
        return jwtBuilder.compact();
    }

    public static User parseToken(String token) {
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(secret)
                .parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        User user = new User();
        user.setPhone(body.get("phone").toString());
        return user;
    }
    ///**
    // * SecretKey 根据 SECRET 的编码方式解码后得到：
    // * Base64 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    // * Base64URL 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));
    // * 未编码：SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    // */
    //private static SecretKey getSecretKey(String secret) {
    //    byte[] encodeKey = Decoders.BASE64.decode(secret);
    //    return Keys.hmacShaKeyFor(encodeKey);
    //}
    //
    ///**
    // * 用claims生成token
    // *
    // * @param claims 数据声明，用来创建payload的私有声明
    // * @return token 令牌
    // */
    //private String generateToken(Map<String, Object> claims) {
    //    SecretKey key = getSecretKey(secret);
    //    //SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //两种方式等价
    //
    //    // 添加payload声明
    //    JwtBuilder jwtBuilder = Jwts.builder()
    //            // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
    //            .setClaims(claims)
    //            // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
    //            .setId(UUID.randomUUID().toString())
    //            // iat: jwt的签发时间
    //            .setIssuedAt(new Date())
    //
    //            // 你也可以改用你喜欢的算法，支持的算法详见：https://github.com/jwtk/jjwt#features
    //            // SignatureAlgorithm.HS256：指定签名的时候使用的签名算法，也就是header那部分
    //            .signWith(key, SignatureAlgorithm.HS256)
    //            .setExpiration(new Date(System.currentTimeMillis() + this.expiration * 1000));
    //
    //    String token = jwtBuilder.compact();
    //    return token;
    //}
    //
    ///**
    // * 生成Token令牌
    // *
    // * @param user 用户
    // * @param id          用户编号
    // * @return 令牌Token
    // */
    //public String generateToken(User user, String id) {
    //    Map<String, Object> claims = new HashMap<>();
    //    claims.put("userId", id);
    //    claims.put("sub", user.getName());
    //    claims.put("created", new Date());
    //    return generateToken(claims);
    //}
    //
    ///**
    // * 从token中获取数据声明claim
    // *
    // * @param token 令牌token
    // * @return 数据声明claim
    // */
    //public Claims getClaimsFromToken(String token) {
    //    try {
    //        SecretKey key = getSecretKey(secret);
    //        Claims claims = Jwts.parserBuilder()
    //                .setSigningKey(key)
    //                .parseClaimsJws(token)
    //                .getBody();
    //        return claims;
    //    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
    //        log.error("token解析错误", e);
    //        throw new IllegalArgumentException("Token invalided.");
    //    }
    //}
    //
    //public String getUserId(String token) {
    //    return (String) getClaimsFromToken(token).get("userId");
    //}
    //
    ///**
    // * 从token中获取登录用户名
    // *
    // * @param token 令牌
    // * @return 用户名
    // */
    //public String getSubjectFromToken(String token) {
    //    String subject;
    //    try {
    //        Claims claims = getClaimsFromToken(token);
    //        subject = claims.getSubject();
    //    } catch (Exception e) {
    //        subject = null;
    //    }
    //    return subject;
    //}
    //
    //
    ///**
    // * 获取token的过期时间
    // *
    // * @param token token
    // * @return 过期时间
    // */
    //public Date getExpirationFromToken(String token) {
    //    return getClaimsFromToken(token).getExpiration();
    //}
    //
    ///**
    // * 判断token是否过期
    // *
    // * @param token 令牌
    // * @return 是否过期：已过期返回true，未过期返回false
    // */
    //public Boolean isTokenExpired(String token) {
    //    Date expiration = getExpirationFromToken(token);
    //    return expiration.before(new Date());
    //}
    //
    ///**
    // * 验证令牌：判断token是否非法
    // *
    // * @param token       令牌
    // * @param user 用户
    // * @return 如果token未过期且合法，返回true，否则返回false
    // */
    //public Boolean validateToken(String token, User user) {
    //    //如果已经过期返回false
    //    if (isTokenExpired(token)) {
    //        return false;
    //    }
    //    String usernameFromToken = getSubjectFromToken(token);
    //    String username = user.getName();
    //    return username.equals(usernameFromToken);
    //}

}