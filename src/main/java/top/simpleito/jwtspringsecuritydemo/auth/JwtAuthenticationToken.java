package top.simpleito.jwtspringsecuritydemo.auth;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import top.simpleito.jwtspringsecuritydemo.auth.jwtutils.JdkMacCryptoSignatureAlgorithm;
import top.simpleito.jwtspringsecuritydemo.auth.jwtutils.Jwt;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private Jwt jwt;

    public JwtAuthenticationToken(String token, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        try {
            jwt = new Jwt(token, JdkMacCryptoSignatureAlgorithm.HMACSHA256);
        } catch (Exception e){
            throw new BadCredentialsException("invalid token");
        }
    }

    @Override
    public String getCredentials() {
        return jwt.getToken();
    }

    @Override
    public String getPrincipal() {
        throw new UnsupportedOperationException();
    }

    public Jwt getJwt() {
        return jwt;
    }
}
