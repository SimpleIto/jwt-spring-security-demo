package top.simpleito.jwtspringsecuritydemo.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import top.simpleito.jwtspringsecuritydemo.auth.JwtTokenGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BusinessJwtTokenGenerator implements JwtTokenGenerator {
    @Override
    public String generate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        UsernamePasswordAuthenticationToken upToken = (UsernamePasswordAuthenticationToken) authentication;

    }
}
