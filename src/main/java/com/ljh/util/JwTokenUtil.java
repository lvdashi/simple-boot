package com.ljh.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 
 * @author lvjinhui
 * @date 2022/03/03 17:08
 **/
@Slf4j
@Component
public class JwTokenUtil {

    private JwTokenUtil() {
    }

    public static JwTokenUtil getInstance() {
        return new JwTokenUtil();
    }


    private static String jwtTokenSecret;
    private static Integer jwtTokenOffset;
    private static Integer refreshTokenOffset;


    @Value("${auth.jwtTokenSecret}")
    public void setJwtTokenSecret(String jwtTokenSecret) {
        JwTokenUtil.jwtTokenSecret = jwtTokenSecret;
    }

    @Value("${auth.jwtTokenOffset}")
    public void setJwtTokenOffset(Integer jwtTokenOffset) {
        JwTokenUtil.jwtTokenOffset = jwtTokenOffset;
    }

    @Value("${auth.refreshTokenOffset}")
    public void setRefreshTokenOffset(Integer refreshTokenOffset) {
        JwTokenUtil.refreshTokenOffset = refreshTokenOffset;
    }
    /**
     * 解析jwt toke 获取数据
     * @param token
     * @return
     */
    public Claims parseToken(String token) {
        try {
            //构建
            DES des = SecureUtil.des(jwtTokenSecret.getBytes());
            //解密为原字符串
            String decryptStr = des.decryptStr(token);
            //token校验
            return Jwts.parser()
                    .setSigningKey(jwtTokenSecret)
                    .parseClaimsJws(decryptStr).getBody();
        } catch (ExpiredJwtException exception) {
            log.error("token解析失败 token过期：" + exception.getMessage(), exception);
        } catch (SignatureException exception) {
            log.error("token解析失败 token签名/密钥异常：" + exception.getMessage(), exception);
        } catch (Exception exception) {
            log.error("token解析失败：" + exception.getMessage(), exception);
        }
        return null;
    }


    /**
     * 生成 access token
     * @param claims
     * @return
     */
    public String generateAccessToken(Map<String, Object> claims) {
        try {
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder()
                    //签发者
                    .setIssuer(Claims.ISSUER)
                    //Jwt头
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                    //自定义有效载荷部分
                    .setClaims(claims)
                    //使用HS256算法签名，PRIVATE_KEY为签名密钥 //生成签名密钥
                    .signWith(SignatureAlgorithm.HS256, jwtTokenSecret)
                    //过期时间
                    .setExpiration(DateUtil.offsetSecond(new Date(), jwtTokenOffset).toJdkDate())
                    //当前时间
                    .setNotBefore(new Date());
            //生成JWT
            String compact = builder.compact();
            //System.out.println("access token->"+compact);
            //构建
            DES des = SecureUtil.des(jwtTokenSecret.getBytes());
            //加密
            return des.encryptHex(compact);
        } catch (Exception exception) {
            log.error("token创建失败：" + exception.getMessage(), exception);
        }
        return null;

    }

    /**
     * 生成 refresh token
     * @param claims
     * @return
     */
    public String generateRefreshToken(Map<String, Object> claims) {
        try {
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder()
                    //签发者
                    .setIssuer(Claims.ISSUER)
                    //Jwt头
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                    //自定义有效载荷部分
                    .setClaims(claims)
                    //使用HS256算法签名，PRIVATE_KEY为签名密钥 //生成签名密钥
                    .signWith(SignatureAlgorithm.HS256, jwtTokenSecret)
                    //过期时间
                    .setExpiration(DateUtil.offsetSecond(new Date(), jwtTokenOffset+refreshTokenOffset).toJdkDate())
                    //当前时间
                    .setNotBefore(new Date());
            //生成JWT
            String compact = builder.compact();
            //去除token对称加密
            //System.out.println("refresh token->"+compact);
            //构建
            DES des = SecureUtil.des(jwtTokenSecret.getBytes());
            //加密
            return des.encryptHex(compact);
        } catch (Exception exception) {
            log.error("token创建失败：" + exception.getMessage(), exception);
        }
        return null;

    }
}
