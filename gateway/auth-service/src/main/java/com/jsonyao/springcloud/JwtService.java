package com.jsonyao.springcloud;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 测试网关JWT鉴权: JWT鉴权服务核心逻辑
 */
@Service
@Slf4j
public class JwtService {

    /**
     * 加密算法关键的Key: 生产环境不能这么用, Key是要传进去的
     */
    private static final String KEY = "changeIt";

    /**
     * 发行方: 用于校验
     */
    private static final String ISSUER = "jsonyao";

    /**
     * 校验内容: 用户名
     */
    private static final String USER_NAME = "username";

    /**
     * Token过期时间
     */
    private static final long TOKEN_EXP_TIME = 2 * 60 * 1000;

    /**
     * 加密算法: HMAC256
     */
    private static final Algorithm HMAC256_ALGORITHM = Algorithm.HMAC256(KEY);

    /**
     * 生成Token
     *
     * @param account
     * @return
     */
    public String token(Account account) {
        // 使用发行方、发行时间、UserName签发Token
        Date now = new Date();
        String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(now)
                    .withExpiresAt(new Date(now.getTime() + TOKEN_EXP_TIME))
                    .withClaim(USER_NAME, account.getUsername())
                    .sign(HMAC256_ALGORITHM);
        log.info("jwt generated user={}", account.getUsername());
        return token;
    }

    /**
     * 校验Token
     *
     * @param token
     * @param username
     * @return
     */
    public boolean verify(String token, String username){
        // 验证发行方、UserName、Token
        try {
            log.info("verifying jwt - username={}", username);
            JWTVerifier jwtVerifier = JWT.require(HMAC256_ALGORITHM)
                                    .withIssuer(ISSUER)
                                    .withClaim(USER_NAME, username)
                                    .build();
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("auth failed", e);
            return false;
        }
    }
}
