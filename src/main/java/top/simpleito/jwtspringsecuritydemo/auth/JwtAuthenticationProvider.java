package top.simpleito.jwtspringsecuritydemo.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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


            //校验token是否过期
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            long currentTime = calendar.getTimeInMillis();
            Number exp = (Number) jwt.getPayloadField("exp");
            if (exp.longValue() < currentTime)
                throw new CredentialsExpiredException("expired token");
        } catch (ClassCastException e){
            throw new BadCredentialsException("invalid token");
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtAuthenticationToken.class);
    }
}
