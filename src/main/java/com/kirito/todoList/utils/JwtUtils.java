package com.kirito.todoList.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtUtils {

    // 有效期
    private static final Long JWT_TTL =  TimeUnit.HOURS.toMillis(1);; // 一个小时
    // 设置密钥明文, 注意长度必须大于等于 6 位
    private static final String JWT_KEY = "kirito";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;


    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式）
     */
    public static String createJWT(String subject){
        log.info("Creating JWT for subject: {}", subject);
        String jwt = getJwtBuilder(subject, null, getUUID()).compact();
        log.debug("Generated JWT: {}", jwt);
        return jwt;
    }

    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     */
    public static String createJWT(String subject, Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    public static String createJWT(String id, String subject, Long ttlMills){
        JwtBuilder builder = getJwtBuilder(subject, ttlMills, id);
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid){
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (ttlMillis == null){
            ttlMillis = JwtUtils.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)                                // 唯一的ID
                .setSubject(subject)                        // 主题 可以是Json数据
                .setIssuer(JWT_KEY)                         // 签发者
                .setIssuedAt(now)                           // 签发时间
                .signWith(SIGNATURE_ALGORITHM, secretKey)    // 使用 HS265 对称加密算法签名, 参数二为密钥
                .setExpiration(expDate);
    }

    /**
     * 生产加密后的密钥 secretKey
     */
    public static SecretKey generalKey(){
        // 固定密钥（推荐从配置文件中加载）
        byte[] encodedKey = JWT_KEY.getBytes(); // 使用固定的明文密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
    }

    /**
     * 解析
     */
    public static Claims parseJWT(String jwt){
        log.info("Parsing JWT: {}", jwt);
        try {
            SecretKey secretKey = generalKey();
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            log.error("Failed to parse JWT: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }


    public static void main(String[] args) {
        String jwt = createJWT("cdc64170957348b890eceeb9e95e801e");
        System.out.println(jwt);
        Claims claims = parseJWT(jwt);
        System.out.println(claims.getSubject());
    }
}
