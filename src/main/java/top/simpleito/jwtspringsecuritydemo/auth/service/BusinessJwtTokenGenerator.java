package top.simpleito.jwtspringsecuritydemo.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import top.simpleito.jwtspringsecuritydemo.auth.jwtutils.JdkMacCryptoSignatureAlgorithm;
import top.simpleito.jwtspringsecuritydemo.auth.jwtutils.Jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BusinessJwtTokenGenerator{

    public String generate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        long iat = calendar.getTimeInMillis()/1000;
        calendar.add(Calendar.DAY_OF_MONTH,1);
        long exp = calendar.getTimeInMillis()/1000;

        UsernamePasswordAuthenticationToken upToken = (UsernamePasswordAuthenticationToken) authentication;
        JdkMacCryptoSignatureAlgorithm signatureAlgorithm = JdkMacCryptoSignatureAlgorithm.HMACSHA256;
        signatureAlgorithm.setSecret(((String)upToken.getCredentials()).getBytes());

        return Jwt.JwtBuilder.getInstance()
                .header()
                    .alg(JdkMacCryptoSignatureAlgorithm.HMACSHA256)
                .payload()
                    .sub((String) upToken.getPrincipal())
                    .iat(iat)
                    .exp(exp)
                    .end()
                .build().getToken();

    }
}
