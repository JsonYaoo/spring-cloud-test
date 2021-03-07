package com.jsonyao.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 测试网关JWT鉴权: JWT鉴权服务
 */
@RestController
@Slf4j
public class AuthController implements AuthService{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public AuthResponse login(String username, String password) {
        Account account = Account.builder().username(username).build();

        // 此处省略username、password验证逻辑...

        String token = jwtService.token(account);
        String refreshToken = UUID.randomUUID().toString();
        account.setToken(token);
        account.setRefreshToken(refreshToken);

        // Redis存放refreshToken, 使用该UUID可以重新生成Token
        redisTemplate.opsForValue().set(refreshToken, account);
        return AuthResponse.builder()
                .account(account)
                .code(AuthResponseCode.SUCCESS)
                .build();
    }

    @Override
    public AuthResponse verify(String token, String username) {
        boolean result = jwtService.verify(token, username);
        return AuthResponse.builder()
                // 其实这里应该是返回Token验证失败的Code, 但这里为了方便就没改
                .code(result? AuthResponseCode.SUCCESS : AuthResponseCode.USER_NOT_FOUND)
                .build();
    }

    @Override
    public AuthResponse refresh(String refresh) {
        // 根据RefreshToken获取Redis中的账户信息, 用于生成Token
        Account account = (Account) redisTemplate.opsForValue().get(refresh);
        if(account == null){
            return AuthResponse.builder()
                    .code(AuthResponseCode.USER_NOT_FOUND)
                    .build();
        }

        String token = jwtService.token(account);
        String refreshToken = UUID.randomUUID().toString();
        account.setToken(token);
        account.setRefreshToken(refreshToken);

        // Redis存放refreshToken, 使用该UUID可以重新生成Token
        redisTemplate.opsForValue().set(refreshToken, account);

        // 同时还要删除旧的refreshToken
        redisTemplate.delete(refresh);

        return AuthResponse.builder()
                .account(account)
                .code(AuthResponseCode.SUCCESS)
                .build();
    }
}
