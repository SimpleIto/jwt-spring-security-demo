package top.simpleito.jwtspringsecuritydemo.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import top.simpleito.jwtspringsecuritydemo.InMemoryPermissionDao;
import top.simpleito.jwtspringsecuritydemo.InMemoryUserDao;
import top.simpleito.jwtspringsecuritydemo.auth.jwtutils.JdkMacCryptoSignatureAlgorithm;
import top.simpleito.jwtspringsecuritydemo.auth.jwtutils.Jwt;

import java.util.Calendar;
import java.util.Locale;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken auth = (JwtAuthenticationToken) authentication;
        //解析token，验证格式是否正确
        Jwt jwt = auth.getJwt();
        try {
            jwt.parseToken();
        } catch (Exception e) {
            throw new BadCredentialsException("invalid token");
        }

        try {

            //校验token是否被修改
            ((JdkMacCryptoSignatureAlgorithm)jwt.getSignatureAlgorithm()).setSecret(InMemoryUserDao.getUser((String) jwt.getPayloadField("sub")).getPassword().getBytes());//使用密码来作为HMAC密钥
            if (jwt.checkIsModified())
                throw new BadCredentialsException("invalid token");
            //校验token是否过期
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            long currentTime = calendar.getTimeInMillis()/1000;
            Number exp = (Number) jwt.getPayloadField("exp");
            if (exp.longValue() < currentTime)
                throw new CredentialsExpiredException("expired token");
            //就做这check，毕竟有密钥 不会伪造。
            //经过这俩校验后就能保证token是颁发的，且合法的，也不用做字段校验了。当然也可以访问呢数据库来进一步避免，但这里没走那种模式 也省了一次IO
        } catch (ClassCastException | NullPointerException e) {
            throwExceptionInvalidToken();
        }

        JwtAuthenticationToken populatedAuth = new JwtAuthenticationToken(jwt, InMemoryPermissionDao.getAuthorities((String) jwt.getPayloadField("sub")));
        populatedAuth.setAuthenticated(true);
        return populatedAuth;
    }

    protected void throwExceptionInvalidToken() throws BadCredentialsException{
        throw new BadCredentialsException("invalid token");
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtAuthenticationToken.class);
    }
}
