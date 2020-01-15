package top.simpleito.jwtspringsecuritydemo.auth.security;


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
        jwt = new Jwt(token, JdkMacCryptoSignatureAlgorithm.HMACSHA256);
    }
    public JwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.jwt = jwt;
    }

    @Override
    public String getCredentials() {
        return jwt.getToken();
    }

    @Override
    public String getPrincipal() {
        return jwt.getToken();
    }

    public Jwt getJwt() {
        return jwt;
    }
}
