package top.simpleito.jwtspringsecuritydemo.auth.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class EncryptCredentialDaoAuthProvider extends DaoAuthenticationProvider {

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, user.getPassword(), null);
        auth.setDetails(authentication.getDetails());
        return auth;
    }
}
